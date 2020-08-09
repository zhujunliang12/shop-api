package com.fh.thread;

import org.springframework.retry.backoff.Sleeper;

import java.io.File;

public class SingleThread implements Runnable {

    private Integer count = 100;


    public SingleThread(Integer count) {
        this.count = count;
    }

    ;

    public SingleThread() {
    }

    ;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public void run() {
        createFilter( );
    }

    public void createFilter() {
        for (int i = 0; i <= count; i++) {

            File file = new File("D:\\lianxiyongde\\tempFilter");

        }


    }


    public static void main(String[] args) {

        Thread thread = new Thread(new SingleThread( ));
        thread.start( );
        System.out.println(thread.getName( ) + "=========" + Thread.currentThread( ).getName( ));


    }


}
