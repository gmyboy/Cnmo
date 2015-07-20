package com.gmy.cnmo.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.adapter.RecommendAdapter;
import com.gmy.cnmo.entity.Recommend;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.util.StackOfActivity;

public class RecommendActivity extends Activity implements OnClickListener {
	private String url = Constant.URL_APPRECOMMEND;
	private Button btn_back;
	private List<Recommend> list;
	private RecommendAdapter adapter;
	private ListView listView;
	private TextView txt_url;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter = new RecommendAdapter(RecommendActivity.this, list);
				listView.setAdapter(adapter);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_recommend);
		init();
		new Thread() {
			public void run() {
				list = JsonParser.getRecommend(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		btn_back.setOnClickListener(this);
		txt_url.setOnClickListener(this);
	}

	private void init() {
		btn_back = (Button) this.findViewById(R.id.rec_btn_back);
		listView = (ListView) this.findViewById(R.id.rec_mlist_recommend);
		txt_url = (TextView) this.findViewById(R.id.rec_txtDownloadApp);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rec_btn_back:
			finish();
			break;
		case R.id.rec_txtDownloadApp:
			ConnectUtil.goToWeb(RecommendActivity.this, "http://www.cnmo.com");
			break;
		default:
			break;
		}
	}
}
