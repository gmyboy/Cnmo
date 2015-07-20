package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.StackOfActivity;

public class FeedBackActivity extends Activity {
	private EditText content, email;
	private Button submit;
	private SharedPreferences spf;
	private String username, uid, imei;
	private TelephonyManager telephonyManager;
	private String back_msg;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				CommonUtil.showToast(getApplicationContext(), back_msg);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity�����̨�Զ���stack��,�����Ժ��˳�
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_feedback);
		telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
		spf = FeedBackActivity.this.getSharedPreferences("Setting", 1);
		content = (EditText) this.findViewById(R.id.fed_editFeedContent);
		email = (EditText) this.findViewById(R.id.fed_editUserContact);
		submit = (Button) this.findViewById(R.id.fed_btnSubmit);
		username = spf.getString("username", null);
		uid = spf.getString("uid", null);
		imei = telephonyManager.getDeviceId();
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (username != null) {
					if (content.getText().toString() == null) {
						CommonUtil.showToast(getApplicationContext(), "�������ݲ���Ϊ��");
					} else if (email.getText().toString() == null) {
						CommonUtil.showToast(getApplicationContext(), "���������ҵ���,��������ϵ��ʽ");
					} else {
						new Thread() {
							public void run() {
								back_msg = ConnectUtil.feedBack(username, uid, imei, email
										.getText().toString(), content.getText().toString());
								handler.sendEmptyMessage(1);
							};
						}.start();
					}
				} else {
					CommonUtil.showToast(FeedBackActivity.this, "��û�е�¼...");
					Intent intent = new Intent();
					intent.setClass(FeedBackActivity.this, LoginActivity.class);
					startActivity(intent);
				}

			}
		});
	}
}
