package com.yunboot.common.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue2 {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new LinkedBlockingQueue<>(40);
        CountDownLatch downLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            Thread.sleep(100);
            new Thread(new ConsumerTest(downLatch, queue, "data_" + i)).start();
        }
        downLatch.await();
    }
}

class ConsumerTest implements Runnable {
    BlockingQueue<String> queue;
    String data;
    CountDownLatch downLatch;

    public ConsumerTest(CountDownLatch downLatch, BlockingQueue queue, String data) {
        this.queue = queue;
        this.data = data;
        this.downLatch = downLatch;
    }

    @Override
    public void run() {
        System.out.println("当前queue大小:" + queue.size() + ",data=" + data);
        try {
            if (queue.offer(data)) {
                Thread.sleep(1000);
                String d = queue.poll();
                System.out.println("消费数据:" + d + ",当前大小：" + queue.size());
            } else {
                System.out.println("当前人数较多，请稍后再试！" + queue.size());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            downLatch.countDown();
        }
    }
}