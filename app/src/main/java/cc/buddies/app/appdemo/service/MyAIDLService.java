package cc.buddies.app.appdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.app.appdemo.MyAIDL;
import cc.buddies.app.appdemo.entity.Person;
import cc.buddies.app.treasury.utils.LogUtils;

public class MyAIDLService extends Service {

    private List<Person> listPerson;

    private IBinder iBinder = new MyAIDL.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {
            LogUtils.e("anInt: " + anInt);
        }

        @Override
        public void addPerson(Person person) throws RemoteException {
            listPerson.add(person);
            LogUtils.e("MyAIDLService----addPerson: " + person.toString());
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            LogUtils.e("MyAIDLService----getPersonList.size(): " + listPerson.size());
            return listPerson;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        listPerson = new ArrayList<>();
        LogUtils.e("MyAIDLService onBind!");
        return iBinder;
    }
}
