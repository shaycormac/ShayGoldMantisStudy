package com.goldmantis.tinkerstudy;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * 静态变量的含义
     */
    public static final String TAG = "类加载学习";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         *android中的类加载器中主要包括三类BootClassLoader，PathClassLoader和DexClassLoader。
         BootClassLoader主要用于加载系统的类，包括java和android系统的类库。
         PathClassLoader主要用于加载应用内中的类。路径是固定的，只能加载
         /data/app中的apk，无法指定解压释放dex的路径。所以PathClassLoader是无法实现动态加载的。
         DexClassLoader可以用于加载任意路径的zip,jar或者apk文件。可以实现动态加载。下面来具体看看应用程序中的类加载器。

         作者：jjlanbupt
         链接：https://www.jianshu.com/p/57fc356b9093
         來源：简书
         著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
        Log.i(TAG,"Context的类加载器："+  Context.class.getClassLoader());
        Log.i(TAG,"TextView的类加载器： "+ TextView.class.getClassLoader());
        //打印结果(模拟器25打印的结果)
       // 01-03 01:30:31.296 3819-3819/com.goldmantis.tinkerstudy I/类加载学习: Context的类加载器：java.lang.BootClassLoader@beef8f4
       // 01-03 01:30:31.296 3819-3819/com.goldmantis.tinkerstudy I/类加载学习: TextView的类加载器： java.lang.BootClassLoader@beef8f4
        
        //当前这个类的classLoader
        Log.i(TAG, "当前这个Activity类的加载器：" + getClassLoader());
        //打印结果
        //01-03 01:30:31.296 3819-3819/com.goldmantis.tinkerstudy I/类加载学习: 当前这个Activity类的加载器：
        // dalvik.system.PathClassLoader[DexPathList[[zip file "/data/app/com.goldmantis.tinkerstudy-1/base.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_dependencies_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_0_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_1_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_2_apk.apk",
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_3_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_4_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_5_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_6_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_7_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_8_apk.apk", 
        // zip file "/data/app/com.goldmantis.tinkerstudy-1/split_lib_slice_9_apk.apk"],
        // nativeLibraryDirectories=[/data/app/com.goldmantis.tinkerstudy-1/lib/x86, /system/lib, /vendor/lib]]]
        //可见直接调用getClassLoader调用的是应用的PathClassLoader,DexPathList是一个数组，包含这么多
        
        
        
        //除了BootClassLoader和应用的PathClassLoader外，还有一个classLoader，比较难以理解，我们可以打印出来看看。
        Log.i(TAG, "ClassLoader的加载器：" + ClassLoader.getSystemClassLoader());
        //打印结果：
        // 01-03 01:38:34.045 4508-4508/com.goldmantis.tinkerstudy I/类加载学习: ClassLoader的加载器：
        // dalvik.system.PathClassLoader[DexPathList[[directory "."],nativeLibraryDirectories=[/system/lib, /vendor/lib, /system/lib, /vendor/lib]]]
        //可见调用ClassLoader.getSystemClassLoader()得到的也是一个PathClassLoader，但是DexPathList为“.”
        
        //todo  PathClassLoader我们只要知道它的路径是指定的，必须是已经安装的apk，应用的classLoader默认为PathClassLoader
        //重点关注DexClassLoader
        /**
         * DexClassLoader构造函数的四个参数。
         dexPath：是加载apk/dex/jar的路径
         optimizedDirectory：是dex的输出路径(因为加载apk/jar的时候会解压除dex文件，这个路径就是保存dex文件的)
         librarySearchPath：是加载的时候需要用到的lib库，这个一般不用，可以传入Null
         parent：给DexClassLoader指定父加载器
         * 
         *public class DexClassLoader extends BaseDexClassLoader
         *  {
              public DexClassLoader(String dexPath, String optimizedDirectory,String librarySearchPath, ClassLoader parent) 
         {
         super(dexPath, new File(optimizedDirectory), librarySearchPath, parent);
         }
         }
         */

        TextView tvBanSheet = findViewById(R.id.tvBanSheet);
        tvBanSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "My Name is Kai Proctor", Toast.LENGTH_SHORT).show();
            }
        });
        Log.i("路径", Environment.getExternalStorageDirectory().getAbsolutePath());
    }
}
