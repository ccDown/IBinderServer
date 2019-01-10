package com.seven.ibinderserver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.seven.ibinderserver.bean.ButtonInfoEntry;

public class MainActivity extends AppCompatActivity {


    IButtonControlAIDL mIButtonControlAIDL;
    Button btnCommit;
    Button btnStop;
    EditText etName;
    EditText etBack;
    boolean isBind;

    //死亡代理 当连接断开之后打印日志
    private IBinder.DeathRecipient  deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("deathRecipient","连接断开");
            mIButtonControlAIDL.asBinder().unlinkToDeath(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCommit = findViewById(R.id.btn_start);
        btnStop = findViewById(R.id.btn_stop);
        etName = findViewById(R.id.et_name);
        etBack = findViewById(R.id.et_back);

        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ButtonService.class);
                intent.setAction("AIDL.buttonserver");
                if (isBind){
                    ButtonInfoEntry buttonInfoEntry = new ButtonInfoEntry();
                    buttonInfoEntry.setButtonName(etName.getText().toString());
                    buttonInfoEntry.setButtonBackground(Integer.parseInt(etBack.getText().toString()));
                    try {
                        Log.e("buttonInfoEntry===",buttonInfoEntry.toString());
                        mIButtonControlAIDL.addButtonInfoList(buttonInfoEntry);

//                            //判断是否存活
//                            mIButtonControlAIDL.asBinder().isBinderAlive();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                } else {
                    isBind = bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isBind){
                    unbindService(mServiceConnection);
                    isBind = false;
                }
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("连接  ComponentName=====",name.getClassName());
            mIButtonControlAIDL = IButtonControlAIDL.Stub.asInterface(service);
            try {
                mIButtonControlAIDL.asBinder().linkToDeath(deathRecipient,0);
            } catch (RemoteException e) {

            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("断开  ComponentName=====",name.getClassName());
            mIButtonControlAIDL = null;
            isBind = false;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
