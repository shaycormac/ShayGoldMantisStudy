package com.goldmantis.shaystudy.javaBase.MuitThread;

import java.util.concurrent.Semaphore;

/**
 * @author: Shay-Patrick-Cormac
 * @email: fang47881@126.com
 * @ltd: 金螳螂企业（集团）有限公司
 * @date: 2017/11/30 09:29
 * @version: 1.0 线程池
 * @description: 练习使用信号量，轻量级的实现线程控制
 */

public class SemaphoreThreadPool 
{
    /**
     * 默认可以获取的最大许可数
     */
    public static final int MAX_AVAILABLE = 10;
/**
 * 显示指定可以获取的个数
 */
    private final int availableSemaphoreCount;
    private Semaphore semaphore;


    //假设的资源数组
    protected Object[] items;
    //数组上是否被占用了
    protected boolean[] used;


    public SemaphoreThreadPool() 
    {
        this(MAX_AVAILABLE);
    }

    public SemaphoreThreadPool(int available) {
        this(available, false);
    }

    /**
     * 
     * @param available  许可数量
     * @param fairMode  是否公平模式
     */
    public SemaphoreThreadPool(int available,boolean fairMode)
    {
        this.availableSemaphoreCount = available;
        this.semaphore = new Semaphore(availableSemaphoreCount, fairMode);

        //假设就这么多
        items = new Resource[availableSemaphoreCount];
        //给与每个值进行赋值
        for (int i = 0; i < availableSemaphoreCount; i++) 
        {
            items[i] = new Resource();
        }
        used = new boolean[availableSemaphoreCount];
        
    }
    //一些方法
    public int availableSemaphoreCount() {
        return this.availableSemaphoreCount;
    }
    /**
     * 获取这个资源
     */
    public Object getItem() throws InterruptedException {
        //获取锁
        semaphore.acquire();
        return getNextAvailableItem();
    }

    //加上锁
    private synchronized Object getNextAvailableItem() 
    {
        for (int i = 0; i < MAX_AVAILABLE; i++)
        {
            //说明这个资源已经被取出使用了。
            if (!used[i])
            {
                used[i] = true;
                return items[i];
            }
        }
        return null;//返回null，说明没有找到
    }

    /**
     * 归还这个资源
     * @param x
     */
    public void givenBack(Object x)
    {
        if (markAsUnused(x))
            semaphore.release();
    }

    //必须同步
    private synchronized boolean markAsUnused(Object x) 
    {
        //循环找到这个资源，把他标记成可以使用了。
        for (int i = 0; i < MAX_AVAILABLE; i++) {
            if (items[i]==x)
            {
                if (used[i])
                {
                    used[i] = false;
                    return true;
                }else
                    return false;
            }
        }
        
        return false;
    }

}

