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
	private double count;// 统计缓存大小
	private DecimalFormat dcmFmt;// 设置缓存大小的精度

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_setting);
		// 初始化view
		initView();
		// 添加监听
		setListener();
	}

	/*
	 * 控件添加监听
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
	 * 初始化控件
	 */
	private void initView() {
		cb_wifi = (CheckBox) this.findViewById(R.id.set_checkbox_wifi_setting);// wifi设置
		cb_3g = (CheckBox) this.findViewById(R.id.set_checkbox_3g_setting);// 3g网络设置
		cb_offline = (CheckBox) this.findViewById(R.id.set_checkbox_offline_setting);// 离线下载
		ly_size = (RelativeLayout) this.findViewById(R.id.set_layout_size_setting);// 字体大小设置
		ly_clean = (RelativeLayout) this.findViewById(R.id.set_layout_clean_setting);// 清除缓存
		ly_push = (RelativeLayout) this.findViewById(R.id.set_layout_push_setting);// 推送消失
		ly_objection = (RelativeLayout) this.findViewById(R.id.set_layout_objection_setting);// 意见反馈
		ly_new = (RelativeLayout) this.findViewById(R.id.set_layout_new_setting);// 新手指南
		btn_back = (Button) this.findViewById(R.id.set_btn_back);// 返回按钮
		btn_login = (Button) this.findViewById(R.id.set_btn_login);// 登录按钮
		name = (TextView) this.findViewById(R.id.set_text_username);// 用户名
		count_cache = (TextView) this.findViewById(R.id.set_text_clean_index);// 缓存大小
		File[] files = SettingActivity.this.getCacheDir().listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				count += files[i].length();
			}
		}
		dcmFmt = new DecimalFormat("0.00");
		if (count <= 1000) {
			count_cache.setText("清除缓存的临时文件，当前缓存大小为" + dcmFmt.format(count / 1024.00) + "KB");
		} else {
			count_cache.setText("清除缓存的临时文件，当前缓存大小为" + dcmFmt.format(count / (1024.00 * 1024.00))
					+ "MB");
		}

		spf = SettingActivity.this.getSharedPreferences("Setting", 0);
		if (spf.getString("username", null) != null) {
			name.setText(spf.getString("username", null));
			btn_login.setText("注销");
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
			if (btn_login.getText().toString().equals("登录")) {
				intent = new Intent();
				intent.setClass(SettingActivity.this, LoginActivity.class);
				startActivityForResult(intent, 1000);// 启动登录activity
			} else if (btn_login.getText().toString().equals("注销")) {
				new AlertDialog.Builder(SettingActivity.this).setTitle("温馨提示:")
						.setMessage("确定要注销?").setNegativeButton("取消", null)
						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								SharedPreferences.Editor localEditor = SettingActivity.this
										.getSharedPreferences("Setting", 0).edit();
								localEditor.remove("username");
								localEditor.remove("uid");
								localEditor.remove("key");
								localEditor.commit();
								name.setText("未登录");
								btn_login.setText("登录");
							}
						}).create().show();
			}

			break;
		case R.id.set_layout_size_setting:

			break;
		case R.id.set_layout_clean_setting:
			new AlertDialog.Builder(SettingActivity.this).setTitle("温馨提示:").setMessage("确定要清除缓存?")
					.setNegativeButton("取消", null)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 获取cache下缓存文件 并删除
							File[] files = SettingActivity.this.getCacheDir().listFiles();
							count = 0;
							for (int i = 0; i < files.length; i++) {
								if (files[i].isFile()) {
									files[i].delete();
									count += files[i].length();
								}
							}
							dcmFmt = new DecimalFormat("0.00");
							count_cache.setText("清除缓存的临时文件，当前缓存大小为"
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
			btn_login.setText("注销");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

	}
}
