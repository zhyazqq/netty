package com.one.idea.netty.netty001;/*
NettyDemo002
Zqq
三月
*/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyDemo002 {
    private final static Logger logger = LoggerFactory.getLogger(NettyDemo002.class);
    public static void main(String[] args) {
        CustomerThreadPoolExecutor pool = new CustomerThreadPoolExecutor();
        try{
            pool.init();
            ExecutorService executorService = pool.getCustomerThreadPoolExecutor();
            for (int i = 0; i < 100; i++) {
                logger.info("提交第" + i + "个任务!当前线程--：{}",Thread.currentThread().getName()+Thread.currentThread().getId());
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            logger.info(">>>task is running=====当前线程--：{}",Thread.currentThread().getName()+Thread.currentThread().getId());
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }catch (Exception e){
          e.printStackTrace();
        }

    }
}
