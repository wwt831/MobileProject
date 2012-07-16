package com.oop.smining;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

public class ReplaceBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG="ApkDelete";

	public void onReceive(Context arg0, Intent arg1) {
		File updateApk = new File(Environment.getExternalStorageDirectory(),
				arg0.getResources().getString(R.string.app_filename));
		if(updateApk.exists()){
			updateApk.delete();
		}
		Log.i(TAG, "pudateApkFile was deleted!");
	}

}
