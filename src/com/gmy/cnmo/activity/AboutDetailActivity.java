package com.gmy.cnmo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class AboutDetailActivity extends Activity {
	Intent intent;
	private TextView detail_servicetitle, detail_service, detail_toptxt;
	private String title;
	private String titlestate;
	private Button btn_detail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_about_detail);
		btn_detail = (Button) this.findViewById(R.id.detail_btn_back);
		detail_servicetitle = (TextView) this.findViewById(R.id.detail_servicetitle);
		detail_service = (TextView) this.findViewById(R.id.detail_service);
		detail_toptxt = (TextView) this.findViewById(R.id.detail_toptitle);
		title = getResources().getString(R.string.service_message_detail);
		titlestate = getResources().getString(R.string.service_statement_detail);
		intent = getIntent();
		if (intent.getIntExtra("TYPE", 0) == 0) {
			detail_toptxt.setText("服务条款");
			detail_servicetitle.setText("服务条款");
			detail_service.setText(title);
		} else {
			detail_toptxt.setText("免责说明");
			detail_servicetitle.setText("免责说明");
			detail_service.setText(titlestate);
		}
		btn_detail.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
