package com.one.idea.netty.netty001;/*
InitThreadFactory
Zqq
三月
*/

import org.quartz.spi.ThreadExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 初始化线程池
 * @author Zqq
 */

public class CustomerThreadPoolExecutor {
    private final Logger logger = LoggerFactory.getLogger(CustomerThreadPoolExecutor.class);
    private  ThreadPoolExecutor pool=null;

    public void init() throws Exception {
        logger.info("初始化线程池");
        pool =  new ThreadPoolExecutor(
                10,
                12,
                3000,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new CustomerThreadFactory()   ,
                new CustomRejectedExecutionHandler()
        );
    }
    public void destory(){
        if(pool!=null){
            pool.shutdown();
        }
    }
    public ExecutorService getCustomerThreadPoolExecutor(){
        return this.pool;
    }
    private class CustomerThreadFactory implements ThreadFactory{

        @Override
        public Thread newThread(Runnable r) {
            AtomicInteger count = new AtomicInteger(0);
            logger.info("创建了一个线程。。。。");
            Thread t = new Thread(r);
//            t.setName("线程--"+count.addAndGet(1));
            return t;
        }
    }
    private  class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                // 核心改造点，由blockingqueue的offer改成put阻塞方法   进入拒绝机制保证
                logger.info("拒绝机制。。。。。。。。重新将线程重新放入队列。防止线程OOM。");
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //todo 业务逻辑？

        }
    }
}
