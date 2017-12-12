package com.milo.learnaidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.milo.learnaidlserver.IAddInterface;
import com.milo.learnaidlserver.Person;

import java.sql.Array;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "aidlclient-MainActivity";
    private EditText etNum1;
    private EditText etNum2;
    private EditText etResult;
    private Button btnAdd;
    private IAddInterface iAddInterface;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected");
            iAddInterface = IAddInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceConnected");
            iAddInterface = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.milo.learnaidlserver",
                "com.milo.learnaidlserver.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    private int age;

    private void initView() {
        etNum1 = (EditText) findViewById(R.id.num1);
        etNum2 = (EditText) findViewById(R.id.num2);
        etResult = (EditText) findViewById(R.id.result);
        btnAdd = (Button) findViewById(R.id.add);
        age = 1;
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                int num1 = Integer.parseInt(etNum1.getText().toString());
//                int num2 = Integer.parseInt(etNum2.getText().toString());
                if (iAddInterface != null) {
                    try {
                        iAddInterface.setPerson(new Person("小" + age, age));
                        age++;
                        etResult.setText(iAddInterface.getPerson().toString());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "服务连接失败！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "服务连接失败！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
