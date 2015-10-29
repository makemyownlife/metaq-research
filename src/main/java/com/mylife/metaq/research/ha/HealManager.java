package com.mylife.metaq.research.ha;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.DelayQueue;

/**
 * 简单的修复管理器 ，暂时处理清理 订单提交费用前的内存提交
 * User: zhangyong
 * Date: 2015/10/28
 * Time: 13:42
 * To change this template use File | Settings | File Templates.
 */
public class HealManager {

    private final static Logger logger = LoggerFactory.getLogger(HealManager.class);

    private static HealManager INSTANCE = null;

    private static Object mutex = new Object();

    private DelayQueue<HealRequest> delayQueue = null;

    private Thread healThread = null;

    public HealManager() {
        init();
    }

    private void init() {
        //初始化delay 线程
        this.delayQueue = new DelayQueue<HealRequest>();
        healThread = new Thread(new HealRunner());
        healThread.start();
    }

    public static HealManager getInstance() {
        if (INSTANCE == null) {
            synchronized (mutex) {
                INSTANCE = new HealManager();
            }
        }
        return INSTANCE;
    }

    public void addHealRequest(HealRequest healRequest) {
        this.delayQueue.offer(healRequest);
    }

    private class HealRunner implements Runnable {

        private volatile boolean stopped = false;

        void shutdown() {
            this.stopped = true;
        }

        @Override
        public void run() {
            while (!this.stopped) {
                try {
                    final HealRequest request = delayQueue.take();
                    processRequest(request);
                } catch (final InterruptedException e) {
                    // take响应中断，忽略
                }
            }
        }

    }

    private void processRequest(HealRequest healRequest) {
        try {
             Long orderId = healRequest.getOrderId();
             System.out.println(DateFormatUtils.format(new Date() ,"yyyy-MM-dd HH:mm:ss") +" orderId:" + orderId);
        } catch (Throwable e) {
            logger.error("processRequest error:", e);
        }
    }

    public static void main(String[] args) {
        System.out.println(DateFormatUtils.format(new Date() ,"yyyy-MM-dd HH:mm:ss"));
        HealManager.getInstance().addHealRequest(new HealRequest(12L , 15000L));
        HealManager.getInstance().addHealRequest(new HealRequest(1111L , 5000L));
        HealManager.getInstance().addHealRequest(new HealRequest(2500l , 25000L));
    }

}
