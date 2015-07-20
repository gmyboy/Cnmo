package com.gmy.cnmo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ab.activity.AbActivity;
import com.ab.global.AbConstant;
import com.ab.view.wheel.AbWheelUtil;
import com.ab.view.wheel.AbWheelView;
import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class TimeSettingActivity extends AbActivity {
	private TextView pre, next;
	private View mTimeView1 = null;
	private View mTimeView2 = null;
	private Intent intent;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity�����̨�Զ���stack��,�����Ժ��˳�
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_settime);
		// �ɹ�ʱ��ѡ����ѡ��Ľ���
		mTimeView1 = mInflater.inflate(R.layout.choose_three, null);
		// �ɹ�ʱ��ѡ����ѡ��Ľ���
		mTimeView2 = mInflater.inflate(R.layout.choose_three, null);
		pre = (TextView) this.findViewById(R.id.time_text_pre);
		next = (TextView) this.findViewById(R.id.time_text_next);
		back = (Button) this.findViewById(R.id.time_btn_back);
		intent = new Intent();
		pre.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(AbConstant.DIALOGBOTTOM, mTimeView1, 200);
			}
		});
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(AbConstant.DIALOGBOTTOM, mTimeView2, 200);
			}
		});

		initWheelTime(mTimeView1, pre);
		initWheelTime(mTimeView2, next);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent.putExtra("pretime", pre.getText().toString());
				intent.putExtra("nexttime", pre.getText().toString());
				setResult(101, intent);
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

	}

	// ʱ����ʾ����
	public void initWheelTime(View mTimeView, TextView mText) {
		// �� �� ʮ������
		final AbWheelView mWheelViewMD = (AbWheelView) mTimeView.findViewById(R.id.wheelView1);
		final AbWheelView mWheelViewMM = (AbWheelView) mTimeView.findViewById(R.id.wheelView2);
		final AbWheelView mWheelViewHH = (AbWheelView) mTimeView.findViewById(R.id.wheelView3);

		Button okBtn = (Button) mTimeView.findViewById(R.id.wheel_okBtn);
		Button cancelBtn = (Button) mTimeView.findViewById(R.id.wheel_cancelBtn);

		mWheelViewMD.setCenterSelectDrawable(this.getResources().getDrawable(
				R.drawable.wheel_select));
		mWheelViewMM.setCenterSelectDrawable(this.getResources().getDrawable(
				R.drawable.wheel_select));
		mWheelViewHH.setCenterSelectDrawable(this.getResources().getDrawable(
				R.drawable.wheel_select));
		// �൱�ڲ��������� �Զ���䲢������
		AbWheelUtil.initWheelTimePicker(this, mText, mWheelViewMD, mWheelViewMM, mWheelViewHH,
				okBtn, cancelBtn, 2013, 1, 1, 10, 0, true);
	}
}
