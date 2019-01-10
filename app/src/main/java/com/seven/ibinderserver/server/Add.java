package com.seven.ibinderserver.server;

import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.IAdd;

/**
 * @author kuan
 * Created on 2019/1/9.
 * @description
 */
public class Add extends IAdd.Stub {

    public Add(){
        Log.i("ADD","初始化");
    }

    @Override
    public void add() throws RemoteException {
        Log.i("ADD","Server 加");

    }
}
