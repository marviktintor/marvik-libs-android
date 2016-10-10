package com.marvik.libs.android.syncengine.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.marvik.libs.android.syncengine.authenticator.SyncAuthenticator;


public class AuthService extends Service {

    private SyncAuthenticator syncAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        syncAuthenticator = new SyncAuthenticator(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAuthenticator.getIBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
