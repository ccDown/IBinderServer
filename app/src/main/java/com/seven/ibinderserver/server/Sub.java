package com.seven.ibinderserver.server;

import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.ISub;

/**
 * @author kuan
 * Created on 2019/1/9.
 * @description
 */
public class Sub extends ISub.Stub {
    public Sub(){
        Log.i("Sub","初始化");
    }
    @Override
    public void sub() throws RemoteException {
        Log.i("SUB","Server SUB");
    }
}
