package com.gmy.cnmo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

public class ImgDownloader {
	/*
	 * �������ϻ�ȡͼƬ�����ͼƬ�ڱ��ش��ڵĻ���ֱ���ã������������ȥ������������ͼƬ �����path��ͼƬ�ĵ�ַ
	 */
	public static Uri getImageURI(String path, File cache) throws Exception {
		String name = path + path.substring(path.lastIndexOf("."));
		File file = new File(cache, name);
		// ���ͼƬ���ڱ��ػ���Ŀ¼����ȥ����������
		if (file.exists()) {
			return Uri.fromFile(file);// Uri.fromFile(path)��������ܵõ��ļ���URI
		} else {
			// �������ϻ�ȡͼƬ
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if (conn.getResponseCode() == 200) {

				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				// ����һ��URI����
				return Uri.fromFile(file);
			}
		}
		return null;
	}

	public  class AsyncImageTask extends AsyncTask<String, Integer, Uri> {

		private ImageView iv_header;
		private File cache;

		public AsyncImageTask(ImageView img, File cache) {
			this.iv_header = img;
			this.cache = cache;
		}

		// ��̨���е����߳����߳�
		@Override
		protected Uri doInBackground(String... params) {
			try {
				return getImageURI(params[0], cache);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		// ���������ui�߳���ִ��
		@Override
		protected void onPostExecute(Uri result) {
			super.onPostExecute(result);
			// ���ͼƬ�İ�
			if (iv_header != null && result != null) {
				iv_header.setImageURI(result);
			}
		}
	}

}