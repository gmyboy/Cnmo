package com.gmy.cnmo.fragment.gyj;

import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.activity.ImageDetailActivity;
import com.gmy.cnmo.adapter.GYJZongHeAdapter;
import com.gmy.cnmo.entity.GuangYingJi;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.view.CustomProgressDialog;
import com.gmy.cnmo.view.PullToRefreshView;
import com.gmy.cnmo.view.PullToRefreshView.OnFooterRefreshListener;
import com.gmy.cnmo.view.PullToRefreshView.OnHeaderRefreshListener;

public class GYJZongHe extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener, OnItemClickListener {
	private String url = Constant.URL_GYJ_ZONGHE;
	private PullToRefreshView pullToRefreshView;
	private List<GuangYingJi> list;
	private ListView listView;
	private GYJZongHeAdapter adapter;
	private CustomProgressDialog progressDialog;// ������ؽ�����
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter = new GYJZongHeAdapter(getActivity(), list);
				listView.setAdapter(adapter);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();// ��������ʧ
				}
			}
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dbg_xinpin, null);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.xinpin_view);
		listView = (ListView) view.findViewById(R.id.xinpin_list);
		progressDialog = new CustomProgressDialog(getActivity(), getResources().getString(
				R.string.inter_dialogprograss));
		progressDialog.show();
		new Thread() {
			public void run() {
				list = JsonParser.getJsonforGYJ(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		pullToRefreshView.setOnHeaderRefreshListener(this);
		pullToRefreshView.setOnFooterRefreshListener(this);
		pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		listView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ImageDetailActivity.class);
		intent.putExtra("PICID", list.get(position).getPicid());
		startActivity(intent);

	}
}
