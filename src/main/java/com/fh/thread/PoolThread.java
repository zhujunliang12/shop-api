package com.fh.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolThread {

    private static final ExecutorService pool = new ThreadPoolExecutor(5, 10,
            0, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));


    public static void testFilter() {

    }


    public static void main(String[] args) {
        pool.execute(new SingleThread( ));
    }


}
