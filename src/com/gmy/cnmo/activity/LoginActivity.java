package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.User;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.StackOfActivity;

public class LoginActivity extends Activity {
	private EditText username, password;
	private TextView register;
	private Button submit;
	private Intent intent;
	private Animation shake;
	private Context context;
	private User user;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (user != null) {
					CommonUtil.showToast(LoginActivity.this, "��¼�ɹ�");
					SharedPreferences.Editor localEditor = context.getSharedPreferences("Setting", 0)
							.edit();
					localEditor.putString("username", username.getText().toString());
					localEditor.putString("uid", user.getUid());
					localEditor.putString("key", user.getKey());
					localEditor.commit();
				} else {
					CommonUtil.showToast(LoginActivity.this, "��¼ʧ��");
				}
				intent.putExtra("resultname", username.getText().toString());
				setResult(1001, intent);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity�����̨�Զ���stack��,�����Ժ��˳�
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_login);
		init();
		// �ύ
		submit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				check();
			}
		});
		// ע��
		register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * ����˺�
	 */
	private void check() {
		context = LoginActivity.this;
		if (username.getText().toString().equals("")) {
			CommonUtil.showToast(context, "�û�������Ϊ��");
			username.startAnimation(shake);
		} else if (password.getText().toString().equals("")) {
			CommonUtil.showToast(context, "���벻��Ϊ��");
			password.startAnimation(shake);
		} else if (password.getText().toString().trim().length() < 4
				|| password.getText().toString().trim().length() > 20) {
			CommonUtil.showToast(context, "�������Ϊ4-20λ֮��");
			password.startAnimation(shake);
		} else {

			new Thread() {
				public void run() {
					// �жϵ�¼�Ƿ�ɹ������ɹ���ѷ����û��������ý���
					user = ConnectUtil.checkLogin(username.getText().toString(), password.getText()
							.toString());
					handler.sendEmptyMessage(1);
				};
			}.start();
		}
	}

	private void init() {
		user = new User();
		username = (EditText) this.findViewById(R.id.login_edit_username);// �����û���
		password = (EditText) this.findViewById(R.id.login_edit_password);// ��������
		register = (TextView) this.findViewById(R.id.login_text_register);// �������ע�����
		submit = (Button) this.findViewById(R.id.login_btn_submit);// �ύ��ť
		shake = AnimationUtils.loadAnimation(this, R.anim.shake);// ��ʼ���ڶ�����
		intent = new Intent();
	}
}
