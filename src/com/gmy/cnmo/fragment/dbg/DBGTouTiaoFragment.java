package com.gmy.cnmo.fragment.dbg;

import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.activity.ArticleDetailActivity;
import com.gmy.cnmo.adapter.FirstArticlelistAdapter;
import com.gmy.cnmo.entity.FirstArticle;
import com.gmy.cnmo.entity.FirstImage;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.DownloadImg;
import com.gmy.cnmo.util.JsonImgParser;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.util.SDCardUtil;
import com.gmy.cnmo.view.AutoScrollViewPager;
import com.gmy.cnmo.view.CustomProgressDialog;
import com.gmy.cnmo.view.PullToRefreshView;
import com.gmy.cnmo.view.PullToRefreshView.OnFooterRefreshListener;
import com.gmy.cnmo.view.PullToRefreshView.OnHeaderRefreshListener;

public class DBGTouTiaoFragment extends Fragment implements OnHeaderRefreshListener,
		OnFooterRefreshListener, OnItemClickListener, OnClickListener {
	private String url = Constant.URL_DBG_TOUTIAO ;
	private PullToRefreshView pullToRefreshView;
	private ListView listView;
	private CustomProgressDialog progressDialog;// 网络加载进度条
	private AutoScrollViewPager viewPager;
	private List<FirstArticle> articlelist, p_articlelist;
	private List<FirstImage> imglist, p_imglist;
	private FirstArticlelistAdapter articleadapter;
	private SamplePagerAdapter imgAdapter;
	private View headerView;
	private int offset = 10;
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@SuppressWarnings("deprecation")
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				articleadapter = new FirstArticlelistAdapter(getActivity(), articlelist, listView);
				imgAdapter = new SamplePagerAdapter();
				viewPager.setAdapter(imgAdapter);
				listView.setAdapter(articleadapter);
				// 一有图片进来，就开始自滚动
				viewPager.startAutoScroll();
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();// 进度条消失
				}
			}
			if (msg.what == 2) {
				imgAdapter.notifyDataSetChanged();
				articleadapter.notifyDataSetChanged();
				offset = offset + 10;
				pullToRefreshView.onFooterRefreshComplete();
			}
			if (msg.what == 3) {
				articleadapter.notifyDataSetChanged();
				pullToRefreshView.onHeaderRefreshComplete("更新于:" + new Date().toLocaleString());
			}
		};
	};

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dbg_toutiao, null);
		pullToRefreshView = (PullToRefreshView) view.findViewById(R.id.toutiao_view);
		headerView = LayoutInflater.from(getActivity()).inflate(R.layout.view_firstviewpager, null);
		viewPager = (AutoScrollViewPager) headerView.findViewById(R.id.toutiao_viewpager);
		listView = (ListView) view.findViewById(R.id.toutiao_list);
		listView.addHeaderView(headerView);// 给listview添加头部
		SDCardUtil.flag = 0;
		progressDialog = new CustomProgressDialog(getActivity(), getResources().getString(
				R.string.inter_dialogprograss));
		progressDialog.show();
		// 设置是否循环显示
		viewPager.setCycle(true);
		// 设置触摸时继续自滚动
		viewPager.setStopScrollWhenTouch(true);
		new Thread() {
			public void run() {
				imglist = JsonImgParser.getJsonforDbg(url);
				articlelist = JsonParser.getJsonForFirst(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		pullToRefreshView.setOnHeaderRefreshListener(this);
		pullToRefreshView.setOnFooterRefreshListener(this);
		pullToRefreshView.setLastUpdated(new Date().toLocaleString());
		listView.setOnItemClickListener(this);
		viewPager.setOnClickListener(this);
		return view;
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		new Thread() {
			public void run() {
				String pullurl = url + "&offset=" + offset;
				p_imglist = JsonImgParser.getJsonforDbg(pullurl);
				p_articlelist = JsonParser.getJsonForFirst(pullurl);
				for (int i = 0; i < 10; i++) {
					articlelist.add(p_articlelist.get(i));
				}
				for (int i = 0; i < 6; i++) {
					imglist.add(p_imglist.get(i));
				}
				handler.sendEmptyMessage(2);
			};
		}.start();
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		new Thread() {
			public void run() {
				List<FirstArticle> p_articlelist = JsonParser.getJsonForFirst(url);
				for (int i = 0; i < 10; i++) {
					articlelist.add(0, p_articlelist.get(i));
				}
				handler.sendEmptyMessage(3);
			};
		}.start();
	}

	private class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imglist.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (View) arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View iv = LayoutInflater.from(getActivity())
					.inflate(R.layout.list_first_viewpage, null);
			ImageView img = (ImageView) iv.findViewById(R.id.list_first_viewpage_img);
			TextView txt = (TextView) iv.findViewById(R.id.list_first_viewpage_txt);
			DownloadImg.getImgloadInstance().getImgDrawable(imglist.get(position).getPicUrl(), img);
			txt.setText(imglist.get(position).getTitle());
			((ViewPager) container).addView(iv, 0);
			return iv;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent();
		intent.setClass(getActivity(), ArticleDetailActivity.class);
		intent.putExtra("DOCID", articlelist.get(position).getId());
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		
	}
}
