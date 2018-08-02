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
    EditText etName;
    EditText etBack;
    boolean isBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCommit = findViewById(R.id.button);
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

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                } else {
                    isBind = bindService(intent,mServiceConnection,BIND_AUTO_CREATE);
                }
            }
        });
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("连接  ComponentName=====",name.getClassName());
            mIButtonControlAIDL = IButtonControlAIDL.Stub.asInterface(service);
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
