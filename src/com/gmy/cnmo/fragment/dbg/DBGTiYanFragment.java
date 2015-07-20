package com.gmy.cnmo.fragment.dbg;

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
import com.gmy.cnmo.activity.ArticleDetailActivity;
import com.gmy.cnmo.adapter.ArticlelistAdapter;
import com.gmy.cnmo.entity.DbgArticle;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.view.CustomProgressDialog;
import com.gmy.cnmo.view.PullToRefreshView;
import com.gmy.cnmo.view.PullToRefreshView.OnFooterRefreshListener;
import com.gmy.cnmo.view.PullToRefreshView.OnHeaderRefreshListener;

@SuppressWarnings("deprecation")
public class DBGTiYanFragment extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener, OnItemClickListener {
	private String url = Constant.URL_DBG_TIYAN;
	private PullToRefreshView pullToRefreshView;
	private List<DbgArticle> list;
	private ListView listView;
	private ArticlelistAdapter adapter;
	private CustomProgressDialog progressDialog;// 网络加载进度条
	private int offset = 10;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter = new ArticlelistAdapter(getActivity(), list,listView);
				listView.setAdapter(adapter);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();// 进度条消失
				}
			}
			if (msg.what == 2) {
				adapter.notifyDataSetChanged();
				offset = offset + 10;
				pullToRefreshView.onFooterRefreshComplete();
			}
			if (msg.what == 3) {
				adapter.notifyDataSetChanged();
				pullToRefreshView.onHeaderRefreshComplete("更新于:" + new Date().toLocaleString());
			}
		};
	};

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
				list = JsonParser.getJsonforDbg(url);
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
		new Thread() {
			public void run() {
				String pullurl = url + "&offset=" + offset;
				List<DbgArticle> p_articlelist = JsonParser.getJsonforDbg(pullurl);
				for (int i = 0; i < 10; i++) {
					list.add(p_articlelist.get(i));
				}

				handler.sendEmptyMessage(2);
			};
		}.start();

	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		new Thread() {
			public void run() {
				List<DbgArticle> p_articlelist = JsonParser.getJsonforDbg(url);
				for (int i = 0; i < 10; i++) {
					list.add(0, p_articlelist.get(i));
				}
				handler.sendEmptyMessage(3);
			};
		}.start();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ArticleDetailActivity.class);
		intent.putExtra("DOCID", list.get(position).getDocid());
		startActivity(intent);

	}
}
