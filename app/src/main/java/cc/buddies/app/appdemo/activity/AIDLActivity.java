package cc.buddies.app.appdemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

import java.util.List;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.MyAIDL;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.entity.Person;
import cc.buddies.app.appdemo.service.MyAIDLService;

public class AIDLActivity extends BaseActivity {

    private boolean icServiceConnected;
    private MyAIDL mAIDL;

    private ServiceConnection serviceConn= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAIDL = MyAIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAIDL = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        bindServiceConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindServiceConnection();
    }

    private void bindServiceConnection() {
        if (!icServiceConnected) {
            Intent intent = new Intent(this, MyAIDLService.class);
            bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
        }
    }

    private void unbindServiceConnection() {
        if (icServiceConnected) {
            unbindService(serviceConn);
            icServiceConnected = false;
        }
    }

    public void onClickButton(View view) {
        Person person = new Person("zhao");
        try {
            mAIDL.addPerson(person);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            List<Person> personList = mAIDL.getPersonList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
