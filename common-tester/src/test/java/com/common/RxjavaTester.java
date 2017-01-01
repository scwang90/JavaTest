package com.common;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

import java.io.File;

/**
 * Created by SCWANG on 2016/9/21.
 */
public class RxjavaTester {

    @Test
    public void 打印字符串数组() {
        String[] names = {"1", "2"};
        Observable.from(names)
                .subscribe(System.out::println).unsubscribe();
    }

    @Test
    public void 创建Observable() {
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Hello");
                subscriber.onNext("Hi");
                subscriber.onNext("Aloha");
                subscriber.onCompleted();
            }
        });
        Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable observable2 = Observable.from(words);
    }

    @Test
    public void 创建Observer() {
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onNext(String s) {
                System.out.println("Item: " + s);
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error!");
            }
        };
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onNext(String s) {
                System.out.println("Item: " + s);
            }

            @Override
            public void onCompleted() {
                System.out.println("Completed!");
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("Error!");
            }
        };
    }

    @Test
    public void 递归测试() {

        Observable.just(new File(".."))
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        Observable<File> just = Observable.just(file);
                        File[] files = file.listFiles();
                        if (files != null && files.length > 0) {
                            just = just.concatWith(Observable.from(files));
                            for (File child : files) {
                                just = just.concatWith(call(child));
                            }
                        }
                        return just;
                    }
                }).subscribe(file -> System.out.println(file.getAbsolutePath()));
    }
}
