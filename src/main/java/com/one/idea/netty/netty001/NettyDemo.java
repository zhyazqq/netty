package com.one.idea.netty.netty001;/*
NettyDemo
Zqq
三月
*/

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NettyDemo {
    private final static Logger logger = LoggerFactory.getLogger(NettyDemo.class);
    public static void main(String[] args) {
        logger.info("main...............");
        server();
    }

    public static void server() {
        //创建一个Socket服务
        try {

            //

            CustomerThreadPoolExecutor pool = new CustomerThreadPoolExecutor();
            //初始化线程池
            pool.init();
            ExecutorService initThreadFactory = pool.getCustomerThreadPoolExecutor();
            ServerSocket serverSocket = new ServerSocket(6666);
            logger.info("创建一个服务。。。。。。。。。");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                logger.info("链接到一个客户端。。。。。。start。。。。。");
                initThreadFactory.execute(new Runnable() {
                    @Override
                    public void run() {
                        handler(socket);
                        logger.info("接收数据。。。。。。end。。。。。");
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Server socket error。。。。。。。。。");
        }

    }

    public static void handler(Socket socket) {
        byte[] bytes = new byte[1024];
        try (InputStream inputStream = socket.getInputStream()) {
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    //todo 业务逻辑
                    logger.info("读取数据。。。。。。start。。。。");
                    String s = new String(bytes, 0, read);
                    System.out.println(s);
                    logger.info("读取数据。。。。。。end。。。。");
                } else {
                    logger.info("读取数据。。。。。。end。。。循环结束。");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("读取数据。。。。。error。。。。。{}", e.fillInStackTrace().toString());
        }


    }
}
