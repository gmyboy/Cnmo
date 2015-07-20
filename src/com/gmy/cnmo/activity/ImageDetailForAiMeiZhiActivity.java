package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.AiMeiZhiImageDetail;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.DownloadImg;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.util.StackOfActivity;
import com.gmy.cnmo.view.CustomProgressDialog;

public class ImageDetailForAiMeiZhiActivity extends Activity implements OnClickListener {
	private String url = Constant.URL_DETAIL_AIMEIZHI;// url
	private Button btn_back, btn_lookmessage, btn_all, btn_open;// 返回按钮 跟贴按钮
	private Button btn_send, btn_share, btn_collection, btn_save;// 发送 分享 收藏按钮
	private TextView title;
	private TextView content, hint;
	private Intent intent;// 接收传来参数
	private CustomProgressDialog progressDialog;// 网络加载进度条
	private EditText edt_message;
	private String username, uid, key, msgcollection, msgsend;// 用户信息包括
	private String pid;
	private ViewPager viewPager;
	private LinearLayout lay_yes, lay_no, content_lay, txt_lay;
	private SamplePagerAdapter adapter;
	private SharedPreferences spf;
	private AiMeiZhiImageDetail detail;
	@SuppressWarnings("unused")
	private boolean isshow = false;
	private boolean isCollected = false;
	private boolean isOpen = true;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				content.setText(detail.getContent()[0]);
				title.setText(detail.getTitle());
				adapter = new SamplePagerAdapter();
				viewPager.setAdapter(adapter);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
			}
			if (msg.what == 2) {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, msgcollection);
				btn_collection.setBackgroundResource(R.drawable.collection);
				isCollected = true;
			}
			if (msg.what == 3) {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, msgcollection);
				btn_collection.setBackgroundResource(R.drawable.collectionoff);
				isCollected = false;
			}
			if (msg.what == 4) {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, msgsend);
				SetBottonFocuse(false);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_img_detail);
		init();
		pid = intent.getStringExtra("DOCID");
		url = url + "&docid=" + pid;

		progressDialog.show();
		new Thread() {
			public void run() {
				detail = JsonParser.getImgJsonForAmz(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		hint.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_lookmessage.setOnClickListener(this);
		btn_collection.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_send.setOnClickListener(this);
		btn_save.setOnClickListener(this);
		btn_all.setOnClickListener(this);
		btn_open.setOnClickListener(this);
		content_lay.setOnClickListener(this);
		txt_lay.setOnClickListener(this);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				content.setText(detail.getContent()[arg0]);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void init() {
		spf = ImageDetailForAiMeiZhiActivity.this.getSharedPreferences("Setting", 1);
		username = spf.getString("username", null);// 用户名
		uid = spf.getString("uid", null);// 用户id
		key = spf.getString("key", null);// 用户key
		progressDialog = new CustomProgressDialog(ImageDetailForAiMeiZhiActivity.this,
				getResources().getString(R.string.inter_dialogprograss));
		intent = getIntent();
		btn_back = (Button) this.findViewById(R.id.img_det_btn_back);
		btn_lookmessage = (Button) this.findViewById(R.id.img_det_btn_lookmessage);
		btn_all = (Button) this.findViewById(R.id.img_det_btn_all);
		btn_send = (Button) this.findViewById(R.id.img_det_btn_send);
		btn_share = (Button) this.findViewById(R.id.img_det_btn_share);
		btn_save = (Button) this.findViewById(R.id.img_det_btn_save);
		btn_collection = (Button) this.findViewById(R.id.img_det_btn_collection);
		btn_open = (Button) this.findViewById(R.id.img_det_btn_on);
		edt_message = (EditText) this.findViewById(R.id.img_det_edit_message);
		viewPager = (ViewPager) this.findViewById(R.id.img_det_viewPagePic);
		content = (TextView) this.findViewById(R.id.img_det_tv_content);
		hint = (TextView) this.findViewById(R.id.img_det_text_hint);
		title = (TextView) this.findViewById(R.id.img_det_text_title);
		lay_no = (LinearLayout) this.findViewById(R.id.img_det_layout_bottom_not);
		lay_yes = (LinearLayout) this.findViewById(R.id.img_det_layout_bottom_yes);
		content_lay = (LinearLayout) this.findViewById(R.id.img_det_layout_text);
		txt_lay = (LinearLayout) this.findViewById(R.id.img_det_layout_content);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_det_btn_back:
			finish();
			break;
		case R.id.img_det_btn_save:
			CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, "保存成功");
			break;
		case R.id.img_det_btn_share:
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT, detail.getTitle() + "\n" + detail.getDocUrl());
			shareIntent.setType("text/plain");
			startActivity(Intent.createChooser(shareIntent, "分享到..."));
			break;
		case R.id.img_det_btn_lookmessage:
			if (username != null) {
				Intent intent = new Intent();
				intent.setClass(ImageDetailForAiMeiZhiActivity.this, CommentListActivity.class);
				intent.putExtra("type", "1");
				intent.putExtra("docid", detail.getId());
				intent.putExtra("uid", uid);
				startActivity(intent);
			} else {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ImageDetailForAiMeiZhiActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.img_det_text_hint:
			SetBottonFocuse(true);
			break;
		case R.id.img_det_layout_text:
			SetBottonFocuse(false);
			break;
		case R.id.img_det_layout_content:
			SetBottonFocuse(false);
			break;
		case R.id.img_det_btn_on:
			if (isOpen) {
				String temp = detail.getContent()[0];
				content.setText(detail.getContent()[0].substring(0, temp.length() / 2));
				btn_open.setBackgroundResource(R.drawable.down);
				isOpen = false;
			} else {
				content.setText(detail.getContent()[0]);
				btn_open.setBackgroundResource(R.drawable.up);
				isOpen = true;
			}

			break;
		case R.id.img_det_btn_collection:
			if (username != null) {
				if (!isCollected) {
					new Thread() {
						@Override
						public void run() {
							msgcollection = ConnectUtil.collection(detail.getId(), "1", key, uid,
									"collection");
							handler.sendEmptyMessage(2);
						}
					}.start();
				} else {
					new Thread() {
						@Override
						public void run() {
							msgcollection = ConnectUtil.collection(detail.getId(), "1", key, uid,
									"cancel");
							handler.sendEmptyMessage(3);
						}
					}.start();
				}

			} else {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ImageDetailForAiMeiZhiActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.img_det_btn_send:
			if (username != null) {
				final String content = edt_message.getText().toString();
				if (content != null) {
					new Thread() {
						@Override
						public void run() {
							msgsend = ConnectUtil.submitComment(detail.getId(), username, uid, key,
									content, "1");
							handler.sendEmptyMessage(4);
						}
					}.start();
				} else {
					CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, "发送内容不能为空!!!");
				}
			} else {
				CommonUtil.showToast(ImageDetailForAiMeiZhiActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ImageDetailForAiMeiZhiActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * 设置编辑框的显示
	 * 
	 * @param paramBoolean
	 */
	private void SetBottonFocuse(boolean paramBoolean) {
		isshow = paramBoolean;
		if (paramBoolean) {
			edt_message.requestFocus();
			((InputMethodManager) edt_message.getContext().getSystemService("input_method"))
					.toggleSoftInput(0, 2);
			lay_no.setVisibility(View.GONE);
			lay_yes.setVisibility(View.VISIBLE);
		} else {
			edt_message.clearFocus();
			((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(
					edt_message.getWindowToken(), 0);
			lay_no.setVisibility(View.VISIBLE);
			lay_yes.setVisibility(View.GONE);
		}
	}

	private class SamplePagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return detail.getPicurl().length;
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
			View iv = LayoutInflater.from(ImageDetailForAiMeiZhiActivity.this).inflate(
					R.layout.list_gyj_viewpage, null);
			ImageView img = (ImageView) iv.findViewById(R.id.list_gyj_viewpage_img);
			DownloadImg.getImgloadInstance().getImgDrawable(detail.getPicurl()[position], img);
			((ViewPager) container).addView(iv, 0);
			return iv;
		}
	}
}
