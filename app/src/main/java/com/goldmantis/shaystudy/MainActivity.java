package com.goldmantis.shaystudy;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;

import com.goldmantis.shaystudy.javaBase.MuitThread.Resource;
import com.goldmantis.shaystudy.javaBase.MuitThread.SemaphoreThreadPool;

import hugo.weaving.DebugLog;

//JakeWharton的方法时间消耗库
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

        printArgs("The", "Quick", "Brown", "Fox");
        Log.i("Fibonacci", "fibonacci's 4th number is " + fibonacci(4));

        Greeter greeter = new Greeter("Jake");
        Log.d("Greeting", greeter.sayHello());

        Charmer charmer = new Charmer("Jake");
        Log.d("Charming", charmer.askHowAreYou());

        startSleepyThread();
    }
    
    @DebugLog
    private void printArgs(String... args)
    {
        for (String arg:args)
        {
            Log.i("参数", arg);
            
        }
        
    }

    @DebugLog
    private int fibonacci(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Number must be greater than zero.");
        }
        if (number == 1 || number == 2) {
            return 1;
        }
        // NOTE: Don't ever do this. Use the iterative approach!
        return fibonacci(number - 1) + fibonacci(number - 2);
    }

    private void startSleepyThread() {
        new Thread(new Runnable() {
            private static final long SOME_POINTLESS_AMOUNT_OF_TIME = 50;

            @Override public void run() {
                sleepyMethod(SOME_POINTLESS_AMOUNT_OF_TIME);
            }

            @DebugLog
            private void sleepyMethod(long milliseconds) {
                SystemClock.sleep(milliseconds);
            }
        }, "I'm a lazy thr.. bah! whatever!").start();
    }


    @DebugLog
    static class Greeter {
        private final String name;

        Greeter(String name) {
            this.name = name;
        }

        private String sayHello() {
            return "Hello, " + name;
        }
    }

    @DebugLog
    static class Charmer {
        private final String name;

        private Charmer(String name) {
            this.name = name;
        }

        public String askHowAreYou() {
            return "How are you " + name + "?";
        }
    }
}
