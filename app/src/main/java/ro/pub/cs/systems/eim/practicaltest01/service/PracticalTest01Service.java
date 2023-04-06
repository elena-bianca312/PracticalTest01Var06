package ro.pub.cs.systems.eim.practicaltest01.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class PracticalTest01Service extends Service {

    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String value = intent.getStringExtra(Constants.TOAST_VALUE);
        processingThread = new ProcessingThread(this, value);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}