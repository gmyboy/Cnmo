package com.gmy.cnmo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.StackOfActivity;

public class AboutActivity extends Activity implements OnClickListener {
	private TextView abt_Version, abt_WebSite, abt_ServiceMassage, abt_ServiceStatement;
	private Button abt_backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_about);
		initView();
		abt_backbtn.setOnClickListener(this);
		abt_Version.setOnClickListener(this);
		abt_WebSite.setOnClickListener(this);
		abt_ServiceMassage.setOnClickListener(this);
		abt_ServiceStatement.setOnClickListener(this);
	}

	private void initView() {
		abt_backbtn = (Button) this.findViewById(R.id.btn_back);
		abt_Version = (TextView) this.findViewById(R.id.about_version);
		abt_WebSite = (TextView) this.findViewById(R.id.about_website);
		abt_ServiceMassage = (TextView) this.findViewById(R.id.about_servicemessage);
		abt_ServiceStatement = (TextView) this.findViewById(R.id.about_servicestatement);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		case R.id.about_version:

			break;
		case R.id.about_website:
			ConnectUtil.goToWeb(AboutActivity.this, "http://www.cnmo.com");
			break;
		case R.id.about_servicemessage:
			intent.setClass(AboutActivity.this, AboutDetailActivity.class);
			intent.putExtra("TYPE", 0);
			startActivity(intent);
			break;
		case R.id.about_servicestatement:
			intent.setClass(AboutActivity.this, AboutDetailActivity.class);
			intent.putExtra("TYPE", 1);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}
