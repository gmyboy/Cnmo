package com.gmy.cnmo.util;

import org.apache.http.HttpHost;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获得手机联网状态
 * 
 * @author GMY
 * 
 */
public class NetWorkUtil {
	@SuppressWarnings("unused")
	private static final String LOG = "NetworkUtils";
	public static int TYPE_MOBILE_CMNET;
	public static int TYPE_MOBILE_CMWAP;
	public static int TYPE_MOBILE_CTWAP;
	public static int TYPE_NO = 0;
	public static int TYPE_WIFI;
	private static NetworkInfo ni;

	static {
		TYPE_MOBILE_CMNET = 1;
		TYPE_MOBILE_CMWAP = 2;
		TYPE_WIFI = 3;
		TYPE_MOBILE_CTWAP = 4;
		ni = null;
	}

	public static HttpHost getHttpHostProxy() {
		if (getNetworkState(StackOfActivity.getInstance().currentActivity()) == TYPE_MOBILE_CTWAP)
			return new HttpHost("10.0.0.200", 80);
		return new HttpHost("10.0.0.172", 80);
	}

	public static int getNetworkState(Context paramContext) {
		ni = ((ConnectivityManager) paramContext.getSystemService("connectivity"))
				.getActiveNetworkInfo();
		if ((ni != null) && (ni.isAvailable())) {
			int i = ni.getType();
			if (i == 0) {
				if ((ni.getExtraInfo() != null) && (ni.getExtraInfo().equals("cmwap")))
					return TYPE_MOBILE_CMWAP;
				if ((ni.getExtraInfo() != null) && (ni.getExtraInfo().equals("uniwap")))
					return TYPE_MOBILE_CMWAP;
				if ((ni.getExtraInfo() != null) && (ni.getExtraInfo().equals("3gwap")))
					return TYPE_MOBILE_CMWAP;
				if ((ni.getExtraInfo() != null) && (ni.getExtraInfo().contains("ctwap")))
					return TYPE_MOBILE_CTWAP;
				return TYPE_MOBILE_CMNET;
			}
			if (i == 1)
				return TYPE_WIFI;
		}
		return TYPE_NO;
	}
}
