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
                3,
                3,
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
            t.setName("线程--"+count);
            return t;
        }
    }
    private  class CustomRejectedExecutionHandler implements RejectedExecutionHandler{

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //todo 业务逻辑？
            logger.info("开始记录日志。。。。。。。。。");
        }
    }
}
