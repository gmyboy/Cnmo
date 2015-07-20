package com.gmy.cnmo.util;

import android.content.Context;
import android.os.Environment;

public class SDCardUtil {

	private static SDCardUtil util;
	public static int flag = 0;

	private SDCardUtil() {

	}

	public static SDCardUtil getInstance() {
		if (util == null) {
			util = new SDCardUtil();
		}
		return util;
	}

	/**
	 * �ж��Ƿ���sdcard
	 * 
	 * @return
	 */
	public boolean hasSDCard() {
		boolean b = false;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			b = true;
		}
		return b;
	}

	/**
	 * �õ�sdcard·��
	 * 
	 * @return
	 */
	public String getExtPath() {
		String path = "";
		if (hasSDCard()) {
			path = Environment.getExternalStorageDirectory().getPath();
		}
		return path;
	}

	/**
	 * �õ�/data/data/yanbin.imagedownloadĿ¼
	 * 
	 * @param mActivity
	 * @return
	 */
	public String getPackagePath(Context context) {
		return context.getFilesDir().toString();
	}

	/**
	 * ����url�õ�ͼƬ��
	 * 
	 * @param url
	 * @return
	 */
	public String getImageName(String url) {
		String imageName = "";
		if (url != null) {
			imageName = url.substring(url.lastIndexOf("/") + 1);
		}
		return imageName;
	}
}
