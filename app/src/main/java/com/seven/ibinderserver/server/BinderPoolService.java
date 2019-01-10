package com.seven.ibinderserver.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

public class BinderPoolService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    private Binder mBinderPool = new BinderPool();
    @Override
    public IBinder onBind(Intent intent) {
        int process = Process.myPid();
        Log.e("Server","连接成功   process=="+process);
        return mBinderPool;
    }
}
