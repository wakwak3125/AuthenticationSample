package com.manturf.authenticationsample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by RyoSakaguchi on 15/02/22.
 */
public class AuthenticationService extends Service {

    private MyAuthenticator myAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        myAuthenticator = new MyAuthenticator(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myAuthenticator.getIBinder();
    }
}
