package com.gmy.cnmo.fragment.fsb;

import java.util.ArrayList;
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
import android.widget.GridView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.activity.ImageDetailForAiMeiZhiActivity;
import com.gmy.cnmo.adapter.AiMeiZhiAdapter;
import com.gmy.cnmo.entity.AiMeiZhi;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.view.CustomProgressDialog;
import com.gmy.cnmo.view.PullToRefreshView;
import com.gmy.cnmo.view.PullToRefreshView.OnFooterRefreshListener;
import com.gmy.cnmo.view.PullToRefreshView.OnHeaderRefreshListener;

public class FSBAiMeiZhi extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener, OnItemClickListener {
	private String url = Constant.URL_FSB_AIMEIZHI;
	private PullToRefreshView pullToRefreshView;
	private AiMeiZhiAdapter adapter;
	private List<AiMeiZhi> list;
	private GridView gridView;
	private CustomProgressDialog progressDialog;// 网络加载进度条
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter = new AiMeiZhiAdapter(getActivity(), list,gridView);
				gridView.setAdapter(adapter);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();// 进度条消失
				}
			}
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_fsb_aimeizhi, null);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.aimeizhi_view);
		gridView = (GridView) view.findViewById(R.id.amz_grid);
		progressDialog = new CustomProgressDialog(getActivity(), getResources().getString(
				R.string.inter_dialogprograss));
		progressDialog.show();
		list = new ArrayList<AiMeiZhi>();
		new Thread() {
			public void run() {
				list = JsonParser.getJsonforAiMeiZhi(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		pullToRefreshView.setOnHeaderRefreshListener(this);
		pullToRefreshView.setOnFooterRefreshListener(this);
		pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		gridView.setOnItemClickListener(this);
		return view;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ImageDetailForAiMeiZhiActivity.class);
		intent.putExtra("DOCID", list.get(position).getDocid());
		startActivity(intent);
	}
}
