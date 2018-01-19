package com.goldmantis.shaystudy.pattern;

import android.content.Context;

import java.io.Serializable;
import java.lang.ref.WeakReference;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: 金螳螂企业（集团）有限公司
 * @Date: 2018/1/17 14:16
 * @Version: 1.0
 * @Description: 第二种方案，将context变成弱引用
 */

public class SingletonDoubleLock2 implements Serializable
{
    //这个必须加上
    private static volatile SingletonDoubleLock2 instance;

    //这样，从而持有的对象便是全局的对象了
    //private Context context;
    private WeakReference<Context> contextWeakReference;

    private SingletonDoubleLock2(Context context)
    {
        if (contextWeakReference==null || contextWeakReference.get()==null)
        {
            contextWeakReference = new WeakReference<Context>(context);
        }
    }

    public static SingletonDoubleLock2 getInstance(Context context)
    {
        if (instance == null) {
            synchronized (SingletonDoubleLock2.class)
            {
                if (instance == null)
                {
                    //解决context内存泄露方法之二是把这个context的变成弱引用
                    instance = new SingletonDoubleLock2(context);
                }

            }
        }
        return instance;
    }

    //做一些事情，假如这里面需要context了
    public void doSomething()
    {
        //得到这个弱引用的对象
        Context context = contextWeakReference.get();
        //然后使用它。要不要判断呢
        if (context != null) {
            
        }
    }

    /**
     * 如果序列化，需要加入此方法，否则单例模式无效
     * @see java.io.ObjectStreamClass
     * @return
     */

    private Object readResolve() {
        return instance;
    }
}
