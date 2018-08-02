package com.seven.ibinderserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.seven.ibinderserver.bean.ButtonInfoEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuan
 * Created on 2018/7/31.
 * @description
 */
public class ButtonService extends Service {
    private static final String TAG = "ButtonService";
    List<ButtonInfoEntry> mButtonInfoEntryList;
    @Override
    public void onCreate() {
        super.onCreate();
        mButtonInfoEntryList = new ArrayList<>();
    }


    IBinder mIBinder = new IButtonControlAIDL.Stub(){

        public static final String TAG = "ButtonService";

        @Override
        public List<ButtonInfoEntry> getButtonInfoList() throws RemoteException {
            Log.e(TAG, "getButtonInfoList: buttonInfo.toString()==="+mButtonInfoEntryList.get(mButtonInfoEntryList.size()-1).toString());

            return mButtonInfoEntryList;
        }

        @Override
        public void addButtonInfoList(ButtonInfoEntry buttonInfo) throws RemoteException {
            Log.e(TAG, "addButtonInfoList: buttonInfo.toString()==="+buttonInfo.toString());
            mButtonInfoEntryList.add(buttonInfo);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: 连接方"+intent.getPackage() );
        return mIBinder;
    }
}
