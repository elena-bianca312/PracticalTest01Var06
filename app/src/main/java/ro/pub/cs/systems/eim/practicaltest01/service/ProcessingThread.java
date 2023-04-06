package ro.pub.cs.systems.eim.practicaltest01.service;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;

    private final String value;

    public ProcessingThread(Context context, String value) {
        this.context = context;

        this.value = value;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionTypes[0]);
        if (Integer.parseInt(value) >= 300) {
            intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                    new Date(System.currentTimeMillis()) + " Victory " + value);
        } else {
            intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                    new Date(System.currentTimeMillis()) + " " + value);
        }

        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}