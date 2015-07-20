package com.gmy.cnmo.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class CommonUtil {
	/**
	 * 显示toast
	 * 
	 * @param context
	 * @param massage
	 */
	public static void showToast(Context context, String massage) {
		Toast.makeText(context, massage, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 检查是否有网络链接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean doHaveInternet(Context context) {
		try {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manager.getActiveNetworkInfo();
			return info != null && info.isConnected();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取软件版本号，对应AndroidManifest.xml下android:versionCode
	 * 
	 * @param context
	 * @return
	 */
	public static int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			versionCode = context.getPackageManager().getPackageInfo("com.iteye.androidtoast", 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

	/**
	 * 链接软件更新url
	 * 
	 * @return
	 */
	public static JSONObject getUpdateJson() {
		JSONObject jsonObject = null;
		try {
			String path = Constant.URL_APPUPDATE;
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5 * 1000);
			conn.setRequestMethod("GET");
			InputStream inStream = conn.getInputStream();
			jsonObject = new JSONObject(inStream.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}

	/**
	 * 检查是否有更新
	 * 
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static boolean isUpdate(Context context) throws Exception {
		// 获取当前软件版本
		int versionCode = getVersionCode(context);
		Log.i("out", "^^^^^^^^^^^^^^^^^^^" + versionCode);
		JSONObject jsonObject = getUpdateJson();
		JSONObject jsondata = jsonObject.getJSONObject("data");
		int newestversion = Integer.parseInt(jsondata.getString("versionCode"));
		Log.i("out", "^^^^^^^^^^^^^^^^^^^" + newestversion);
		if (versionCode >= newestversion) {
			return false;
		} else {
			return true;
		}
	}
}
