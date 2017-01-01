package com.common;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by SCWANG on 2016/11/24.
 */
public class AppReplace$Tester {

    private static final String source = "E:\\Workspaces\\android-studio\\ProjectDetection\\app\\src\\main\\java\\com\\yx\\test\\app";

    private static final Pattern[] patterns = {
            Pattern.compile("(@\\w+)\\(\\{?R\\.\\w+\\.(\\w+)\\}?\\)", Pattern.CASE_INSENSITIVE),
            Pattern.compile("(@\\w+)\\(\\{((R\\.\\w+\\.(\\w+))(\\s*,\\s*R\\.\\w+\\.(\\w+))+)\\}\\)", Pattern.CASE_INSENSITIVE),
    };


    private class Module {
        public File file;
        public String content;
        public Module(File file) {
            this.file = file;
            this.content = fileToUtf8(file);
        }

        public boolean filter() {
            return filterContent(content);
        }

        public boolean replace() {
            boolean ret = false;
            ret = ret || replace$single();
            ret = ret || replace$multi();
            return ret;
        }

        public boolean replace$single() {
            Matcher matcher = patterns[0].matcher(content);
            if (matcher.find()) {
                String source = matcher.group();
                String target = matcher.group(1) + "$(\"" + matcher.group(2) + "\")";
                System.out.println(source);
                System.out.println("\t" + target);
                content = content.replace(source, target);
                replace();
                return true;
            }
            return false;
        }

        public boolean replace$multi() {
            Matcher matcher = patterns[1].matcher(content);
            if (matcher.find()) {
                System.out.println(matcher.group());
//                for (int i = 1; i <= matcher.groupCount(); i++) {
//                    System.out.println("\t" + matcher.group(i));
//                }
                String source = matcher.group();
                StringBuilder target = new StringBuilder(matcher.group(1));
                target.append("$({");
                Observable.just(matcher.group(2))
                        .flatMap(s -> Observable.from(s.split(",")))
                        .map(s -> s.substring(1+s.lastIndexOf('.')))
                        .subscribe(s -> target.append('"').append(s).append('"').append(","));
                target.deleteCharAt(target.length() - 1);
                target.append("})");
                System.out.println("\t" + target);
                content = content.replace(source, target);
                replace();
                return true;
            }
            return false;
        }

        public void save() {
            try (FileOutputStream stream = new FileOutputStream(file)){
                stream.write(content.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void find() {
        Observable<Module> map = Observable.just(source).map(File::new)
                .flatMap(AppReplace$Tester::listFiles)
                .map(Module::new);
        map.count().subscribe(count -> System.out.println("map.count = " + count));
        map = map.filter(Module::filter);
        map.count().subscribe(count -> System.out.println("map.filter = " + count));
        map = map.filter(Module::replace);
        map.count().subscribe(count -> System.out.println("map.filter = " + count));
        map.subscribe(Module::save);
    }

    private Boolean filterContent(String s) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(s).find()) {
                return true;
            }
        }
        return false;
    }

    private String fileToUtf8(File file) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"))) {
            String temp;
            while ((temp = reader.readLine()) != null) {
                builder.append(temp);
                builder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    /**
     * rxjava递归查询
     * @param f
     * @return
     */
    public static Observable<File> listFiles(final File f){
        if(f.isDirectory()){
            return Observable.from(f.listFiles()).flatMap(AppReplace$Tester::listFiles);
        } else {
            /**filter操作符过滤视频文件,是视频文件就通知观察者**/
            return Observable.just(f).filter(file -> file.getName().endsWith(".java"));
        }
    }

}
