package com.gmy.cnmo.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.fragment.collection.CollectionDB;
import com.gmy.cnmo.util.StackOfActivity;

public class CollectionActivity extends FragmentActivity {
	private String[] menuText = { "谍报", "生活", "图片" };
	@SuppressWarnings("rawtypes")
	private Class[] fragmentarray = { CollectionDB.class, CollectionDB.class, CollectionDB.class };
	private FragmentTabHost mTabHost;
	private View localView;
	private TextView textView;
	@SuppressWarnings("unused")
	private SharedPreferences spf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_collection);
		initView();
	}

	private void initView() {
		spf = CollectionActivity.this.getSharedPreferences("Setting", 1);
		// 获取tabhost
		mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		for (int i = 0; i < 3; i++) {
			TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(menuText[i]).setIndicator(
					getTabHost(i));
			mTabHost.addTab(localTabSpec, fragmentarray[i], null);
		}
	}

	/*
	 * 获得tabhost内容
	 */
	private View getTabHost(int i) {
		localView = LayoutInflater.from(CollectionActivity.this).inflate(R.layout.list_main, null);
		textView = (TextView) localView.findViewById(R.id.tab_main_text);
		textView.setText(menuText[i]);
		return localView;
	}
}
