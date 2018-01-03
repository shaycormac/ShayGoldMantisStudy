package com.goldmantis.shaystudy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.goldmantis.shaystudy.javaBase.MuitThread.Resource;
import com.goldmantis.shaystudy.javaBase.MuitThread.SemaphoreThreadPool;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testSemaphore();
    }
/**
 * 测试信号量
 */ 
    private void testSemaphore() 
    {
        //初始化线程池。假设采用默认的构造方法
        SemaphoreThreadPool semaphoreThreadPool = new SemaphoreThreadPool();
        //想要获取的资源
        Object resource = null;

        try {
            Resource r = (Resource) semaphoreThreadPool.getItem();
            resource=r.getResource();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //把资源归还给原来的位置。
            semaphoreThreadPool.givenBack(resource);
        }

    }
}
