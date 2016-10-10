package com.marvik.libs.android.syncengine.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.marvik.libs.android.syncengine.adapter.SyncAdapter;


public class SyncService extends Service {

    private SyncAdapter syncAdapter;

    @Override
    public void onCreate() {
        super.onCreate();
        syncAdapter = new SyncAdapter(getApplicationContext(), true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
