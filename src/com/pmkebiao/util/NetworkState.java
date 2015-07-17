package com.pmkebiao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkState {
	public static boolean isCon(Context context) {
		
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				 NetworkInfo networkInfo=connectivity.getActiveNetworkInfo();
				 if(networkInfo.isConnected() && networkInfo!=null){
					 if(networkInfo.getState()==NetworkInfo.State.CONNECTED){
						 return true;
					 }
				 }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
