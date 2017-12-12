package com.milo.learnaidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by milo on 17-12-12.
 */

public class IRemoteService extends Service {
    private static final String TAG = "IRemoteService";
    private List<Person> persons;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        persons = new ArrayList<>();
        return iBinder;
    }

    private IBinder iBinder = new IAddInterface.Stub() {
        @Override
        public void setPerson(Person person) throws RemoteException {
            persons.add(person);
        }

        @Override
        public List<Person> getPerson() throws RemoteException {
            return persons;
        }
    };
}
