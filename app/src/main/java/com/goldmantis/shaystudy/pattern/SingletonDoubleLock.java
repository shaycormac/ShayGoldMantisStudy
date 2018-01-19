package com.goldmantis.shaystudy.pattern;

import android.content.Context;

import java.io.Serializable;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: 金螳螂企业（集团）有限公司
 * @Date: 2018/1/17 13:55
 * @Version: 1.0
 * @Description: 普通双重锁校验 为了防止序列化生成不同对象，需要实现ser
 */

class SingletonDoubleLock implements Serializable
{
    //这个必须加上
    private static volatile SingletonDoubleLock instance;

    //这样，从而持有的对象便是全局的对象了
    private Context context;

    private SingletonDoubleLock(Context context) 
    {
        this.context = context;
    }
    
    public static SingletonDoubleLock getInstance(Context context)
    {
        if (instance == null) {
            synchronized (SingletonDoubleLock.class)
            {
                if (instance == null) 
                {
                    //解决context内存泄露方法之一是把这个context的对象变成全局的application对象即可
                   // context = context.getApplicationContext();
                    instance = new SingletonDoubleLock(context.getApplicationContext());
                }
                
            }
        }
        return instance;
    }

    public void doSomething()
    {
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
