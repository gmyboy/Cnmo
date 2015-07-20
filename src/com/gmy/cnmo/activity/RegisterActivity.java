package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.StackOfActivity;

public class RegisterActivity extends Activity implements OnClickListener {
	private EditText edt_username, edt_password, edt_email;
	private Button btn_register, btn_cancel, btn_show;
	private CheckBox ch_xiexi;// 是否注册协议
	private TextView txt_xieyi;// 注册协议内容
	private String message;// 返回值类型
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				CommonUtil.showToast(RegisterActivity.this, message);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_register);
		// 初始化
		init();
		btn_register.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		btn_show.setOnClickListener(this);
		txt_xieyi.setOnClickListener(this);
	}

	/*
	 * 初始化控件
	 */
	private void init() {
		edt_username = (EditText) this.findViewById(R.id.reg_edt_username);// 用户名
		edt_password = (EditText) this.findViewById(R.id.reg_edit_password);// 密码
		edt_email = (EditText) this.findViewById(R.id.reg_edit_mail);// 邮件
		btn_register = (Button) this.findViewById(R.id.reg_btn_finish);// 完成按钮
		btn_show = (Button) this.findViewById(R.id.reg_btn_showcode);// 显示密码
		btn_cancel = (Button) this.findViewById(R.id.reg_btn_cancel);// 取消按钮
		ch_xiexi = (CheckBox) this.findViewById(R.id.reg_cbox_agree);// 注册协议
		txt_xieyi = (TextView) this.findViewById(R.id.reg_text_reg_content);// 注册协议内容
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.reg_btn_cancel:
			finish();
			break;
		case R.id.reg_btn_finish:
			if (ch_xiexi.isChecked()) {
				new Thread() {
					public void run() {
						message = ConnectUtil.register(edt_username.getText().toString(),
								edt_password.getText().toString(), edt_email.getText().toString());
						handler.sendEmptyMessage(1);
					};
				}.start();
			}
			break;
		case R.id.reg_btn_showcode:
			if (btn_show.getText().toString().equals("显示")) {
				edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
				btn_show.setText("隐藏");
			} else if (btn_show.getText().toString().equals("隐藏")) {
				edt_password.setInputType(InputType.TYPE_CLASS_TEXT
						| InputType.TYPE_TEXT_VARIATION_PASSWORD);
				btn_show.setText("显示");
			}
			break;
		case R.id.reg_text_reg_content:
			// 弹出注册协议具体内容
			AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
			localBuilder.setTitle("注册协议");
			localBuilder.setIcon(android.R.drawable.ic_dialog_alert);
			localBuilder.setMessage(R.string.service_message_detail);
			localBuilder.setNegativeButton("确定", null);
			AlertDialog localAlertDialog = localBuilder.create();
			localAlertDialog.getWindow().setFlags(1024, 1024);
			localAlertDialog.show();
			break;
		default:
			break;
		}
	}
}
