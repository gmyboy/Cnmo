package com.gmy.cnmo.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class PushActivity extends Activity {
	private CheckBox remind;
	private TextView txt_time, txt_sound, txt_vibration;
	private TextView txt_time_hint;
	private RelativeLayout lay_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_push);
		init();
		remind.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!isChecked) {
					txt_time.setTextColor(getResources().getColor(R.color.grey));
					txt_sound.setTextColor(getResources().getColor(R.color.grey));
					txt_vibration.setTextColor(getResources().getColor(R.color.grey));
					lay_time.setClickable(false);
				} else {
					txt_time.setTextColor(getResources().getColor(R.color.degrey));
					txt_sound.setTextColor(getResources().getColor(R.color.degrey));
					txt_vibration.setTextColor(getResources().getColor(R.color.degrey));
					lay_time.setClickable(true);
				}

			}
		});

		lay_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PushActivity.this, TimeSettingActivity.class);
				startActivityForResult(intent, 100);
			}
		});
	}

	private void init() {
		remind = (CheckBox) this.findViewById(R.id.push_checkbox_push_remind);
		txt_time = (TextView) this.findViewById(R.id.push_text_time_setting);
		txt_sound = (TextView) this.findViewById(R.id.push_text_sound_setting);
		txt_time_hint = (TextView) this.findViewById(R.id.push_text_time_setting_index);
		txt_vibration = (TextView) this.findViewById(R.id.push_text_vibration_setting);
		lay_time = (RelativeLayout) this.findViewById(R.id.push_layout_time_setting);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 101) {
			String result_pre = data.getStringExtra("pretime").substring(10);
			String result_next = data.getStringExtra("nexttime").substring(10);
			txt_time_hint.setText("每日 " + result_pre + "-次日 " + result_next);
		}
	}
}
