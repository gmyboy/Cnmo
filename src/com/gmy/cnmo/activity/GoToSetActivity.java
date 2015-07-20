package com.gmy.cnmo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class GoToSetActivity extends Activity {
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.gotoset);
		textView = (TextView) this.findViewById(R.id.gotoset_txt);
		textView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
				finish();
				startActivity(intent);
			}
		});

	}
}
