package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.DownloadImg;
import com.gmy.cnmo.util.JsonImgParser;
import com.gmy.cnmo.util.StackOfActivity;

public class SplashActivity extends Activity {
	private String url = Constant.URL_LOGO;
	private String picurl;
	private View view;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				DownloadImg.getImgloadInstance().getImgDrawable(picurl, view);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_splash);
		view = LayoutInflater.from(SplashActivity.this).inflate(R.layout.activity_splash, null);

		new Thread() {
			public void run() {
				picurl = JsonImgParser.getImgforLoGo(url);
				handler.sendEmptyMessage(1);
			};
		}.start();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, MainActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();

			}
		}, 2000);
	}
}
