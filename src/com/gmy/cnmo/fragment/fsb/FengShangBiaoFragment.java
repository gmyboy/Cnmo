package com.gmy.cnmo.fragment.fsb;

import com.gmy.cnmo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

public class FengShangBiaoFragment extends Fragment {
	private FragmentTabHost mTabHost;
	private String[] menutext = { "综合", "爱美志", "雯琰文", "爱美访", "爱美妆" };
	@SuppressWarnings("rawtypes")
	private Class[] fragmentarray = { FSBZongHe.class, FSBAiMeiZhi.class, FSBWenYanWen.class,
			FSBAiMeiFang.class, FSBAiMeiZhuang.class };
	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_diebaoguan, null);
		initView(view);
		return view;
	}

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
		textView = ((TextView) localView.findViewById(R.id.tab_second_btn));
		textView.setText(menutext[i]);
		// 获取屏幕宽度
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		textView.setWidth(metrics.widthPixels / 5);
		return localView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getActivity().setTitle("风尚标");
	}
}
