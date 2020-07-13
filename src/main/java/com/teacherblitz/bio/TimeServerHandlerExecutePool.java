package com.teacherblitz.bio;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 伪IO线程池
 *
 * @author: teacherblitz
 * @since: 2020/7/13
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize){
        // 系统可用线程数 * 2
        int corePoolSize = Runtime.getRuntime().availableProcessors() * 2;
        executor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, 120L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(queueSize));
    }

    public void execute(Runnable task){
        executor.execute(task);
    }
}
