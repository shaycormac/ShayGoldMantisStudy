package com.goldmantis.payhost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.goldmantis.payservice.IPay;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    /**
     * 静态变量的含义
     */
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建apk加载路径
        String src = getFilesDir().getAbsolutePath() + File.separator + "plugin.apk";
        //作为odex的释放路径
        String desc = getFilesDir().getAbsolutePath() + File.separator + "plugin" + File.separator;
        try {
            copyPlugin(src);//将assets下的插件apk拷贝到src路径下
            //创建DexClassLoader,parent指定为应用默认的PathClassLoader(getClassLoader默认就是加载当前的应用的使用PathClassLoader)
            DexClassLoader dexClassLoader = new DexClassLoader(src, desc, null, getClassLoader());
            Class payServiceImplClass = dexClassLoader.loadClass("com.goldmantis.payplugin.PayServiceImpl");
            Object o = payServiceImplClass.newInstance();
            IPay payService = (IPay) o;
            String userName = payService.getUserName();
            String order=payService.getOrder("ss");
            payService.pay(10);
            Log.i("TAG", "userName: "+userName);
            Log.i("TAG", "order: "+order);
            
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void copyPlugin(String path) throws Exception{
        InputStream in=getAssets().open("payplugin.apk");
        OutputStream os=new FileOutputStream(path);
        byte[] temp=new byte[1024];
        int len=-1;
        while((len=in.read(temp))!=-1){
            os.write(temp,0,len);
        }
        in.close();
        os.flush();
        os.close();
    }
    
}
