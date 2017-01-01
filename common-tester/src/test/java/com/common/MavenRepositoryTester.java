package com.common;

import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.*;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Maven库转移
 * Created by SCWANG on 2016/9/22.
 */
public class MavenRepositoryTester {

    private static final String source = "C:\\Users\\SCWANG\\.gradle\\caches\\modules-2\\files-2.1";
    private static final String target = "E:\\Android\\android-studio-2.2.0\\gradle\\m2repository";

    private class Module {
        public String group;
        public String module;
        public String version;
        public boolean intact;
        public List<File> files = new ArrayList<>();
        public List<File> fileTargets = new ArrayList<>();
        public List<String> fileNames = new ArrayList<>();
        public List<SimpleEntry<File,File>> fileSimpleEntrys = new ArrayList<>();
        public File path;
        public File pathTarget;

        public Module(File path) {
            this.path = path;
            this.version = path.getName();
            this.module = path.getParentFile().getName();
            this.group = path.getParentFile().getParentFile().getName();
            this.pathTarget = new File(new File(new File(target, group.replace(".", "\\")), module), version);

            Observable.just(path)
                    .flatMap(file -> Observable.from(file.listFiles()))
                    .flatMap(file -> Observable.from(file.listFiles()))
                    .subscribe(file -> {
                        files.add(file);
                        fileNames.add(file.getName());
                        fileTargets.add(new File(pathTarget, file.getName()));
                        fileSimpleEntrys.add(new SimpleEntry<>(file,new File(pathTarget, file.getName())));
                    });

            intact = files.size() >= 2;
        }

        @Override
        public String toString() {
            return "Module{" +
                    "intact='" + intact + '\'' +
                    ", group='" + group + '\'' +
                    ", module='" + module + '\'' +
                    ", version='" + version + '\'' +
                    ", files=" + Arrays.toString(fileNames.toArray()) +
                    '}';
        }
    }

    @Test
    public void loadModules() {
        Observable<Module> from = Observable.just(source)
                .map(File::new)
                .flatMap(file1 -> Observable.from(file1.listFiles()))
                .flatMap(file1 -> Observable.from(file1.listFiles()))
                .flatMap(file1 -> Observable.from(file1.listFiles()))
                .map(Module::new);

        Observable<Module> intact = from.filter(module -> module.intact);
        Observable<Module> unintact = from.filter(module -> !module.intact);

        intact.count().subscribe(integer -> {
            System.out.println("intact.size = " + integer);
        });
        unintact.count().subscribe(integer -> {
            System.out.println("unintact.size = " + integer);
        });
//        unintact.subscribe(System.out::println);
//        intact.subscribe(System.out::println);

        Observable<File> file = intact.flatMap(module -> Observable.from(module.fileTargets));
//        Observable<File> module = intact.map(module -> module.pathTarget);

        try (PrintStream print = new PrintStream(new FileOutputStream(new File(target, "fileList.txt")))) {
            file.subscribe(file1 -> {
                print.println(file1.getAbsolutePath().substring(target.length() + 1));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try (PrintStream print = new PrintStream(new FileOutputStream(new File(target, "moduleList.txt")))) {
            intact.subscribe(module -> {
                print.println(module.pathTarget.getAbsolutePath().substring(target.length() + 1));
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        intact
                .flatMap(module -> Observable.from(module.fileSimpleEntrys))
                .subscribe(entry -> {
//                    deleteFile(entry.getValue());
                    copyFile(entry.getValue(),entry.getKey());
                });
//        modulelist.subscribe(System.out::println);
//        filelist.subscribe(System.out::println);
    }

    @Test
    public void listSource() {
        File file = new File(source);
        for (File tmp : file.listFiles()) {
            System.out.println(tmp);
        }
    }

    public static void deleteFile(File file) {
        try {
            if (file.delete()) {
                System.out.println("删除文件：" + file + " 成功");
            } else {
                System.out.println("删除文件：" + file + " 失败");
            }
        } catch (Throwable e) {
            System.err.println("删除文件：" + file + e.toString());
        }
    }

    public static void copyFile(File toFile, File fromFile) {// 复制文件
        if (toFile.exists()) {// 判断目标目录中文件是否存在
            System.out.println("文件" + toFile.getAbsolutePath() + "已经存在，跳过该文件！");
            return;
        } else {
            createFile(toFile, true);// 创建文件
        }
        System.out.println("复制文件" + fromFile.getAbsolutePath() + "到"
                + toFile.getAbsolutePath());
        try {
            InputStream is = new FileInputStream(fromFile);// 创建文件输入流
            FileOutputStream fos = new FileOutputStream(toFile);// 文件输出流
            byte[] buffer = new byte[1024];// 字节数组
            while (is.read(buffer) != -1) {// 将文件内容写到文件中
                fos.write(buffer);
            }
            is.close();// 输入流关闭
            fos.close();// 输出流关闭
        } catch (Exception e) {// 捕获文件不存在异常
            e.printStackTrace();
        }
    }

    public static void createFile(File file, boolean isFile) {// 创建文件
        if (!file.exists()) {// 如果文件不存在
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();// 创建目录
            }
            try {
                file.createNewFile();// 创建新文件
            } catch (IOException e) {
                e.printStackTrace();
            }
//            if (!file.getParentFile().exists()) {// 如果文件父目录不存在
//                createFile(file.getParentFile(), false);
//            } else {// 存在文件父目录
//                if (isFile) {// 创建文件
//                    try {
//                        file.createNewFile();// 创建新文件
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    file.mkdirs();// 创建目录
//                }
//            }
        }
    }
}
