package com.gmy.cnmo.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.ArticleDetail;
import com.gmy.cnmo.util.AsyncImageLoader;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.util.StackOfActivity;
import com.gmy.cnmo.view.CustomProgressDialog;

public class ArticleDetailActivity extends Activity implements OnClickListener {
	private String url = Constant.URL_DETAIL_ARTICLE;
	private Button btn_back, btn_lookmessage;
	private Button btn_send, btn_share, btn_collection;
	private TextView title, date;
	private TextView content, hint, loadmore;
	private Spanned str_content;// 接收文章内容信息
	private Intent intent;
	private Drawable drawable;// 加载内容里面的图片
	private CustomProgressDialog progressDialog;// 网络加载进度条
	private ArticleDetail articleDetail;
	private ProgressBar progressBar;
	private LinearLayout bottom_yes, bottom_no, lay_content;
	private EditText edt_message;
	@SuppressWarnings("unused")
	private boolean isshow = false;
	private boolean isCollected = false;
	private SharedPreferences spf;
	private String username, uid, key, msgcollection, msgsend;
	private Html.ImageGetter myGetter = new Html.ImageGetter() {

		@Override
		public Drawable getDrawable(String source) {
			Context context = ArticleDetailActivity.this;

			AsyncImageLoader loader = new AsyncImageLoader(context);
			// 将图片缓存至外部文件中
			loader.setCache2File(true); // false
			// 设置外部缓存文件夹
			loader.setCachedDir(context.getCacheDir().getAbsolutePath());
			loader.downloadImage(source, new AsyncImageLoader.ImageCallback() {

				@SuppressWarnings("deprecation")
				@Override
				public void onImageLoaded(Bitmap bitmap, String imageUrl) {
					if (bitmap != null) {
						BitmapDrawable bitDrawable = new BitmapDrawable(bitmap);
						drawable = bitDrawable;
					} else {
						// 下载失败，设置默认图片
						drawable = getResources().getDrawable(R.drawable.default_article_list);
					}
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth() * 6,
							drawable.getIntrinsicHeight() * 6);
				}
			});
			return drawable;
		}
	};
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 2) {
				CommonUtil.showToast(ArticleDetailActivity.this, msgcollection);
				btn_collection.setBackgroundResource(R.drawable.collection);
				isCollected = true;
			}
			if (msg.what == 3) {
				CommonUtil.showToast(ArticleDetailActivity.this, msgcollection);
				btn_collection.setBackgroundResource(R.drawable.collectionoff);
				isCollected = false;
			}
			if (msg.what == 4) {
				CommonUtil.showToast(ArticleDetailActivity.this, msgsend);
				SetBottonFocuse(false);
			}
			if (msg.what == 1) {
				progressBar.setVisibility(View.GONE);
				title.setText(articleDetail.getTitle());
				date.setText(articleDetail.getDate());
				str_content = Html.fromHtml(articleDetail.getContent(), myGetter, null);
				content.setText(str_content);
				if (progressDialog.isShowing()) {
					progressDialog.dismiss();// 进度条消失
				}
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_detail);
		init();
		progressDialog.show();
		url = url + "&docid=" + intent.getStringExtra("DOCID") + "&offset=0";
		new Thread() {
			public void run() {
				articleDetail = JsonParser.getArticleDetail(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		// if ((articleDetail.getTotal().equals("1") ||
		// articleDetail.getTotal().equals(""))
		// && (articleDetail.getCurrentpage().equals("1") ||
		// articleDetail.getCurrentpage()
		// .equals(""))) {
		// loadmore.setVisibility(View.GONE);
		// }
		loadmore.setOnClickListener(this);
		hint.setOnClickListener(this);
		lay_content.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_lookmessage.setOnClickListener(this);
		btn_collection.setOnClickListener(this);
		btn_share.setOnClickListener(this);
		btn_send.setOnClickListener(this);
	}

	private void init() {
		spf = ArticleDetailActivity.this.getSharedPreferences("Setting", 1);
		username = spf.getString("username", null);// 用户名
		uid = spf.getString("uid", null);// 用户id
		key = spf.getString("key", null);// 用户key
		intent = getIntent();// 接受转来消息
		articleDetail = new ArticleDetail();// 初始化对象
		btn_back = (Button) this.findViewById(R.id.det_btn_back);// 返回按钮
		btn_lookmessage = (Button) this.findViewById(R.id.det_btn_lookmessage);// 跟贴
		btn_send = (Button) this.findViewById(R.id.det_btn_send);// 发送
		btn_collection = (Button) this.findViewById(R.id.det_btn_collection);// 收藏
		btn_share = (Button) this.findViewById(R.id.det_btn_share);// 分享
		title = (TextView) this.findViewById(R.id.det_text_title);// 标题
		date = (TextView) this.findViewById(R.id.det_text_date);// 日期
		content = (TextView) this.findViewById(R.id.det_article_content);// 内容
		bottom_no = (LinearLayout) this.findViewById(R.id.det_layout_bottom_no);// 编辑框
		bottom_yes = (LinearLayout) this.findViewById(R.id.det_layout_bottom_yes);// 编辑框
		lay_content = (LinearLayout) this.findViewById(R.id.det_layout_article);// 显示内容布局
		hint = (TextView) this.findViewById(R.id.det_text_hint);// 编辑框hint
		edt_message = (EditText) this.findViewById(R.id.det_edit_message);// 编辑框内容
		progressBar = (ProgressBar) this.findViewById(R.id.det_progressBar);// 进度条
		loadmore = (TextView) this.findViewById(R.id.det_textLoadMore);// 加载更多
		progressDialog = new CustomProgressDialog(ArticleDetailActivity.this, getResources()
				.getString(R.string.inter_dialogprograss));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.det_btn_back:
			finish();
			break;
		case R.id.det_btn_send:
			if (username != null) {
				final String content = edt_message.getText().toString();
				if (content != null) {
					new Thread() {
						@Override
						public void run() {
							msgsend = ConnectUtil.submitComment(articleDetail.getDocid(), username,
									uid, key, content, "0");
							handler.sendEmptyMessage(4);
						}
					}.start();
				} else {
					CommonUtil.showToast(ArticleDetailActivity.this, "发送内容不能为空!!!");
				}
			} else {
				CommonUtil.showToast(ArticleDetailActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ArticleDetailActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.det_btn_collection:
			if (username != null) {
				if (!isCollected) {
					new Thread() {
						@Override
						public void run() {
							msgcollection = ConnectUtil.collection(articleDetail.getDocid(), "1",
									key, uid, "collection");
							handler.sendEmptyMessage(2);
						}
					}.start();
				} else {
					new Thread() {
						@Override
						public void run() {
							msgcollection = ConnectUtil.collection(articleDetail.getDocid(), "1",
									key, uid, "cancel");
							handler.sendEmptyMessage(3);
						}
					}.start();
				}

			} else {
				CommonUtil.showToast(ArticleDetailActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ArticleDetailActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.det_btn_share:
			Intent shareIntent = new Intent();
			shareIntent.setAction(Intent.ACTION_SEND);
			shareIntent.putExtra(Intent.EXTRA_TEXT,
					articleDetail.getTitle() + "\n" + articleDetail.getContent());
			shareIntent.setType("text/plain");
			startActivity(Intent.createChooser(shareIntent, "分享到..."));
			break;
		case R.id.det_btn_lookmessage:
			if (username != null) {
				Intent intent = new Intent();
				intent.setClass(ArticleDetailActivity.this, CommentListActivity.class);
				intent.putExtra("type", articleDetail.getType());
				intent.putExtra("docid", articleDetail.getDocid());
				intent.putExtra("uid", uid);
				startActivity(intent);
			} else {
				CommonUtil.showToast(ArticleDetailActivity.this, "还没有登录...");
				Intent intent = new Intent();
				intent.setClass(ArticleDetailActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		case R.id.det_text_hint:
			SetBottonFocuse(true);
			break;
		case R.id.det_layout_article:
			SetBottonFocuse(false);
			break;
		case R.id.det_textLoadMore:
			loadmore.setVisibility(View.GONE);
			progressBar.setVisibility(View.VISIBLE);
			url = url.substring(0, url.length() - 2) + 1;
			new Thread() {
				public void run() {
					articleDetail = JsonParser.getArticleDetail(url);
					handler.sendEmptyMessage(1);
				};
			}.start();
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
			bottom_no.setVisibility(View.GONE);
			bottom_yes.setVisibility(View.VISIBLE);
		} else {
			edt_message.clearFocus();
			((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(
					edt_message.getWindowToken(), 0);
			bottom_no.setVisibility(View.VISIBLE);
			bottom_yes.setVisibility(View.GONE);
		}
	}

}
