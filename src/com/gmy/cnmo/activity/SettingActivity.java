package com.gmy.cnmo.activity;

import java.io.File;
import java.text.DecimalFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class SettingActivity extends Activity implements OnCheckedChangeListener, OnClickListener {
	private CheckBox cb_wifi, cb_3g, cb_offline;
	private RelativeLayout ly_size, ly_clean, ly_push, ly_objection, ly_new;
	private Button btn_back, btn_login;
	private TextView name, count_cache;
	public SharedPreferences spf;
	private double count;// ͳ�ƻ����С
	private DecimalFormat dcmFmt;// ���û����С�ľ���

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity�����̨�Զ���stack��,�����Ժ��˳�
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_setting);
		// ��ʼ��view
		initView();
		// ��Ӽ���
		setListener();
	}

	/*
	 * �ؼ���Ӽ���
	 */
	private void setListener() {
		cb_wifi.setOnCheckedChangeListener(this);
		cb_3g.setOnCheckedChangeListener(this);
		cb_offline.setOnCheckedChangeListener(this);
		ly_size.setOnClickListener(this);
		ly_clean.setOnClickListener(this);
		ly_push.setOnClickListener(this);
		ly_objection.setOnClickListener(this);
		ly_new.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_login.setOnClickListener(this);
	}

	/*
	 * ��ʼ���ؼ�
	 */
	private void initView() {
		cb_wifi = (CheckBox) this.findViewById(R.id.set_checkbox_wifi_setting);// wifi����
		cb_3g = (CheckBox) this.findViewById(R.id.set_checkbox_3g_setting);// 3g��������
		cb_offline = (CheckBox) this.findViewById(R.id.set_checkbox_offline_setting);// ��������
		ly_size = (RelativeLayout) this.findViewById(R.id.set_layout_size_setting);// �����С����
		ly_clean = (RelativeLayout) this.findViewById(R.id.set_layout_clean_setting);// �������
		ly_push = (RelativeLayout) this.findViewById(R.id.set_layout_push_setting);// ������ʧ
		ly_objection = (RelativeLayout) this.findViewById(R.id.set_layout_objection_setting);// �������
		ly_new = (RelativeLayout) this.findViewById(R.id.set_layout_new_setting);// ����ָ��
		btn_back = (Button) this.findViewById(R.id.set_btn_back);// ���ذ�ť
		btn_login = (Button) this.findViewById(R.id.set_btn_login);// ��¼��ť
		name = (TextView) this.findViewById(R.id.set_text_username);// �û���
		count_cache = (TextView) this.findViewById(R.id.set_text_clean_index);// �����С
		File[] files = SettingActivity.this.getCacheDir().listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				count += files[i].length();
			}
		}
		dcmFmt = new DecimalFormat("0.00");
		if (count <= 1000) {
			count_cache.setText("����������ʱ�ļ�����ǰ�����СΪ" + dcmFmt.format(count / 1024.00) + "KB");
		} else {
			count_cache.setText("����������ʱ�ļ�����ǰ�����СΪ" + dcmFmt.format(count / (1024.00 * 1024.00))
					+ "MB");
		}

		spf = SettingActivity.this.getSharedPreferences("Setting", 0);
		if (spf.getString("username", null) != null) {
			name.setText(spf.getString("username", null));
			btn_login.setText("ע��");
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.set_btn_back:
			finish();
			break;
		case R.id.set_btn_login:
			if (btn_login.getText().toString().equals("��¼")) {
				intent = new Intent();
				intent.setClass(SettingActivity.this, LoginActivity.class);
				startActivityForResult(intent, 1000);// ������¼activity
			} else if (btn_login.getText().toString().equals("ע��")) {
				new AlertDialog.Builder(SettingActivity.this).setTitle("��ܰ��ʾ:")
						.setMessage("ȷ��Ҫע��?").setNegativeButton("ȡ��", null)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SharedPreferences.Editor localEditor = SettingActivity.this
										.getSharedPreferences("Setting", 0).edit();
								localEditor.remove("username");
								localEditor.remove("uid");
								localEditor.remove("key");
								localEditor.commit();
								name.setText("δ��¼");
								btn_login.setText("��¼");
							}
						}).create().show();
			}

			break;
		case R.id.set_layout_size_setting:

			break;
		case R.id.set_layout_clean_setting:
			new AlertDialog.Builder(SettingActivity.this).setTitle("��ܰ��ʾ:").setMessage("ȷ��Ҫ�������?")
					.setNegativeButton("ȡ��", null)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ��ȡcache�»����ļ� ��ɾ��
							File[] files = SettingActivity.this.getCacheDir().listFiles();
							count = 0;
							for (int i = 0; i < files.length; i++) {
								if (files[i].isFile()) {
									files[i].delete();
									count += files[i].length();
								}
							}
							dcmFmt = new DecimalFormat("0.00");
							count_cache.setText("����������ʱ�ļ�����ǰ�����СΪ"
									+ dcmFmt.format(count / (1024.0 * 1024.0)) + "MB");
						}
					}).create().show();

			break;
		case R.id.set_layout_push_setting:
			intent = new Intent();
			intent.setClass(SettingActivity.this, PushActivity.class);
			startActivity(intent);
			break;
		case R.id.set_layout_objection_setting:
			intent = new Intent();
			intent.setClass(SettingActivity.this, FeedBackActivity.class);
			startActivity(intent);
			break;
		case R.id.set_layout_new_setting:
			intent = new Intent();
			intent.setClass(SettingActivity.this, GuideActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 1000 && resultCode == 1001) {
			String result_value = data.getStringExtra("resultname");
			name.setText(result_value);
			btn_login.setText("ע��");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	}
}
