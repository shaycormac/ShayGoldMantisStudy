package com.goldmantis.payplugin;

import android.util.Log;

import com.goldmantis.payservice.IPay;

/**
 * @Author: Shay-Patrick-Cormac
 * @Email: fang47881@126.com
 * @Ltd: 金螳螂企业（集团）有限公司
 * @Date: 2018/1/3 10:05
 * @Version: 1.0
 * @Description: 简单实现
 */

public class PayServiceImpl implements IPay 
{
    /**
     * 静态变量的含义
     */
    public static final String TAG = "PayServiceImpl实现类";
    @Override
    public void pay(int money) {
        Log.i(TAG, "pay: "+money+" 元"); 
    }

    @Override
    public String getOrder(String s) {
        return "001";
    }

    @Override
    public String getUserName() {
        return "Kai Proctor";
    }
}
