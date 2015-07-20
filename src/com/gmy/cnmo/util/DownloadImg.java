package com.gmy.cnmo.util;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.gmy.cnmo.R;

public class DownloadImg {
	private static final DownloadImg imgload = new DownloadImg();

	ExecutorService pool;
	private HashMap<String, SoftReference<Drawable>> imageCache;

	public static synchronized DownloadImg getImgloadInstance() {
		return imgload;
	}

	private DownloadImg() {
		pool = Executors.newFixedThreadPool(4);
		imageCache = new HashMap<String, SoftReference<Drawable>>();
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param imageUrl
	 *            ͼƬ��URL��ַ
	 * @param imageCallback
	 *            �ص��ӿ�
	 * @return ����ͼƬ�� drawable
	 */
	@SuppressLint("HandlerLeak")
	Drawable loadDrawable(final String imageUrl, final ImageCallback imageCallback) {

		// ������ ���д�url,��ֱ�ӵ���
		if (imageCache.containsKey(imageUrl)) {

			SoftReference<Drawable> softReference = imageCache.get(imageUrl);
			Drawable drawable = softReference.get();
			if (drawable != null) {

				return drawable;
			}
		}

		// ��������ͼƬ
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {

				imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
			}
		};
		// ����������ڻ����У������߳�����ͼƬ
		pool.execute(new Thread() {

			@Override
			public void run() {
				// ����ͼƬ����drawable����
				Drawable drawable = loadImageFromUrl(imageUrl);

				// �����ص�ͼƬ���浽������
				if (drawable != null) {
					imageCache.put(imageUrl, new SoftReference<Drawable>(drawable));

					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				} else {
					Message message = handler.obtainMessage(0, null);
					handler.sendMessage(message);
				}

			}

		});

		return null;
	}

	/**
	 * ����URL����ͼƬ����drawable
	 */
	Drawable loadImageFromUrl(String imageURL) {
		URL urll;
		Drawable drawable = null;
		try {
			urll = new URL(imageURL);
			HttpURLConnection conn = (HttpURLConnection) urll.openConnection();
			conn.setDoInput(true);
			conn.connect();

			InputStream in = conn.getInputStream();
			drawable = Drawable.createFromStream(in, imageURL);
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		return drawable;
	}

	// �ص��ӿ�
	public interface ImageCallback {
		public void imageLoaded(Drawable imageDrawable, String imageUrl);
	}

	@SuppressWarnings("deprecation")
	public void getImgDrawable(String imageUrl, final View view) {

		Drawable drawable = loadDrawable(imageUrl, new DownloadImg.ImageCallback() {
			@SuppressWarnings("null")
			@Override
			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (view != null) {
					if (imageDrawable != null)
						view.setBackgroundDrawable(imageDrawable);
					else
						view.setBackgroundResource(R.drawable.default_article_list);
				} else
					view.setBackgroundResource(R.drawable.default_article_list);
			}
		});

		if (drawable == null)
			view.setBackgroundResource(R.drawable.default_article_list);
		else
			view.setBackgroundDrawable(drawable);

	}
}
