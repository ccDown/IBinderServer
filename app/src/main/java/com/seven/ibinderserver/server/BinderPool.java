package com.seven.ibinderserver.server;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.IBinderPool;


public class BinderPool extends IBinderPool.Stub {
    public static final int ADD = 0;
    public static final int SUB = 1;

    public BinderPool() {
        Log.e("BinderPool", "创建");
    }

    @Override
    public IBinder queryBinder(int binderCode) throws RemoteException {
        IBinder binder = null;
        switch (binderCode) {
            case ADD: {
                binder = new Add();
                Log.e("Server queryBinder","创建 Add");
                break;
            }
            case SUB: {
                binder = new Sub();
                Log.e("Server queryBinder","创建 Sub");
                break;
            }
            default:
                break;
        }

        return binder;
    }
}
