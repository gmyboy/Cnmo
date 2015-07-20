package com.gmy.cnmo.fragment.dbg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.gmy.cnmo.R;

public class DieBaoGuanFragment extends Fragment {
	private FragmentTabHost mTabHost;
	private String[] menutext = { "头条", "新品", "价格", "体验", "应用" };
	@SuppressWarnings("rawtypes")
	private Class[] fragmentarray = { DBGTouTiaoFragment.class, DBGXinPinFragment.class,
			DBGJiaGeFragment.class, DBGTiYanFragment.class, DBGYingYongFragment.class };
	private TextView button;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_diebaoguan, null);
		initView(view);
		return view;
	}

	/**
	 * 初始化view
	 * 
	 * @param view
	 */
	private void initView(View view) {
		mTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
		mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
		for (int i = 0; i < 5; i++) {
			TabHost.TabSpec localTabSpec = mTabHost.newTabSpec(menutext[i]).setIndicator(
					getTabHost(i));
			mTabHost.addTab(localTabSpec, fragmentarray[i], null);
		}
	}

	private View getTabHost(int i) {
		View localView = LayoutInflater.from(getActivity()).inflate(R.layout.list_second, null);
		button = ((TextView) localView.findViewById(R.id.tab_second_btn));
		button.setText(menutext[i]);
		return localView;
	}
}
