package com.gmy.cnmo.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.fragment.dbg.DieBaoGuanFragment;
import com.gmy.cnmo.fragment.fsb.FSBAiMeiZhuang;
import com.gmy.cnmo.fragment.fsb.FengShangBiaoFragment;
import com.gmy.cnmo.fragment.gyj.GuangYingJiFragment;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.StackOfActivity;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private String[] menuText = { "谍报馆", "风尚标", "光影集", "爱美志", };
	// "爱美访", "雯琰文", "爱美志", "应用谍报", "体验谍报",
	// "价格谍报", "新品谍报" };
	@SuppressWarnings("rawtypes")
	private Class[] fragmentarray = { DieBaoGuanFragment.class, FengShangBiaoFragment.class,
			GuangYingJiFragment.class, FSBAiMeiZhuang.class };
	private FragmentTabHost mTabHost;
	private PopupWindow popup;
	private View popview, localView;
	private TextView textView;
	private ImageView imageView;
	private SharedPreferences spf;
	JSONObject jsonObject, jsondata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_main);
		setTitle("谍报馆");
		initView();
		initPopupWindow();
		mTabHost.getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!popup.isShowing()) {
					showPopupWindow();
					imageView.setImageResource(R.drawable.diy_rightb);
				} else if (popup.isShowing()) {
					popup.dismiss();
					imageView.setImageResource(R.drawable.diy_righta);
				}
				mTabHost.setCurrentTab(3);
			}
		});
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				switch (tabId) {
				case "爱美装":
					showPopupWindow();
					imageView.setImageResource(R.drawable.diy_rightb);
					break;
				case "谍报馆":
				case "风尚标":
				case "光影集":
					popup.dismiss();
					imageView.setImageResource(R.drawable.diy_righta);
				default:
					break;
				}
			}
		});
	}

	private void initPopupWindow() {
		// 获取屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		popview = LayoutInflater.from(MainActivity.this).inflate(R.layout.popup, null);
		popup = new PopupWindow(popview, metrics.widthPixels / 4, LayoutParams.WRAP_CONTENT);
		Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8;
		btn1 = (Button) popview.findViewById(R.id.btn_pop1);
		btn2 = (Button) popview.findViewById(R.id.btn_pop2);
		btn3 = (Button) popview.findViewById(R.id.btn_pop3);
		btn4 = (Button) popview.findViewById(R.id.btn_pop4);
		btn5 = (Button) popview.findViewById(R.id.btn_pop5);
		btn6 = (Button) popview.findViewById(R.id.btn_pop6);
		btn7 = (Button) popview.findViewById(R.id.btn_pop7);
		btn8 = (Button) popview.findViewById(R.id.btn_pop8);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		// 使用系统动画
		popup.setAnimationStyle(R.style.anim_pop);
		// 以下三行作用是点击空白处popup会消失
		popup.setOutsideTouchable(true);
		// popup.setBackgroundDrawable(new BitmapDrawable(getResources(),
		// (Bitmap) null));
		popup.setFocusable(true);
		popup.getContentView().setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				popup.setFocusable(false);
				popup.dismiss();
				imageView.setImageResource(R.drawable.diy_righta);
				return true;
			}
		});
	}

	private void showPopupWindow() {
		if (!popup.isShowing()) {
			popup.showAtLocation(popview, Gravity.RIGHT | Gravity.BOTTOM, 0 - 10, mTabHost
					.getTabWidget().getHeight());
		}
	}

	private void initView() {
		spf = MainActivity.this.getSharedPreferences("Setting", 1);
		// 获取tabhost
		mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		for (int i = 0; i < 4; i++) {
			TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(menuText[i]).setIndicator(
					getTabHost(i));
			mTabHost.addTab(localTabSpec, fragmentarray[i], null);
		}
	}

	/*
	 * 获得tabhost内容
	 */
	private View getTabHost(int i) {
		if (i < 3) {
			localView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_main, null);
			textView = (TextView) localView.findViewById(R.id.tab_main_text);
			textView.setText(menuText[i]);
		} else if (i == 3) {
			localView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_last, null);
			textView = (TextView) localView.findViewById(R.id.tab_last_text);
			imageView = (ImageView) localView.findViewById(R.id.tab_last_img);
			textView.setText(menuText[i]);
		}
		return localView;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent();
		switch (item.getItemId()) {
		case R.id.menu_favorites:
			if (spf.getString("username", null) != null) {
				intent.setClass(MainActivity.this, CollectionActivity.class);
				startActivity(intent);
			} else {
				CommonUtil.showToast(MainActivity.this, "还没有登录...");
				Intent intent2 = new Intent();
				intent2.setClass(MainActivity.this, LoginActivity.class);
				startActivity(intent2);
			}
			break;
		case R.id.menu_apps_recommended:
			intent.setClass(MainActivity.this, RecommendActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_update:
			CommonUtil.showToast(this, "已经是最新版本");
			break;
		case R.id.menu_settings:
			intent.setClass(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_about:
			intent.setClass(MainActivity.this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.menu_exit:
			StackOfActivity.getInstance().exit();
			break;
		default:
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case value:
		// mTabHost.re
		// break;

		default:
			break;
		}
	}

}
