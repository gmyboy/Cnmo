package com.gmy.cnmo.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.adapter.CommentListAdapter;
import com.gmy.cnmo.entity.Comment;
import com.gmy.cnmo.util.CommonUtil;
import com.gmy.cnmo.util.ConnectUtil;
import com.gmy.cnmo.util.Constant;
import com.gmy.cnmo.util.JsonParser;
import com.gmy.cnmo.util.StackOfActivity;

public class CommentListActivity extends Activity implements OnClickListener {
	private String url = Constant.URL_COMMENT_LIST;
	private Button btn_back, btn_send;
	private ListView listView;
	private TextView noComment;
	private TextView hintText;
	private EditText edt_message;
	private LinearLayout bottom_yes, bottom_no;
	private Intent intent;
	@SuppressWarnings("unused")
	private boolean isshow = false;
	private List<Comment> list;
	private CommentListAdapter adapter;
	private SharedPreferences spf;
	private String username, uid, key, msgsend;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				if (list == null) {
					noComment.setVisibility(View.VISIBLE);
				} else {
					adapter = new CommentListAdapter(CommentListActivity.this, list);
					listView.setAdapter(adapter);
				}
			}
			if (msg.what == 2) {
				CommonUtil.showToast(CommentListActivity.this, msgsend);
				SetBottonFocuse(false);
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity�����̨�Զ���stack��,�����Ժ��˳�
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_commentlist);
		init();
		if (intent.getStringExtra("type").equals("1")) {
			url = url + "&docid=" + intent.getStringExtra("docid")+"&ispic="+1;
		}else {
			url = url + "&docid=" + intent.getStringExtra("docid");
		}
		new Thread() {
			public void run() {
				list = JsonParser.getCommentJson(url);
				handler.sendEmptyMessage(1);
			};
		}.start();
		bottom_no.setOnClickListener(this);
		hintText.setOnClickListener(this);
		// listView.setOnClickListener(this);
		btn_back.setOnClickListener(this);
		btn_send.setOnClickListener(this);
	}

	private void init() {
		spf = CommentListActivity.this.getSharedPreferences("Setting", 1);
		username = spf.getString("username", null);// �û���
		uid = spf.getString("uid", null);// �û�id
		key = spf.getString("key", null);// �û�key
		intent = getIntent();// ���մ���������
		btn_back = (Button) this.findViewById(R.id.comm_btn_back);// ���ذ�ť
		btn_send = (Button) this.findViewById(R.id.comm_btn_send);// ���Ͱ�ť
		listView = (ListView) this.findViewById(R.id.comm_list_view);// ��ʾ�����б��listview
		noComment = (TextView) this.findViewById(R.id.comm_txtPageNoData);// û������ʱ��ʾ��textview
		hintText = (TextView) this.findViewById(R.id.comm_text_hint);// �༭����ʾ����
		edt_message = (EditText) this.findViewById(R.id.comm_edit_message);// �༭������
		bottom_yes = (LinearLayout) this.findViewById(R.id.comm_layout_bottom_yes);// �༭��
		bottom_no = (LinearLayout) this.findViewById(R.id.comm_layout_bottom_no);// �༭��
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.comm_btn_back:
			finish();
			break;
		case R.id.comm_text_hint:
			SetBottonFocuse(true);
			break;
		case R.id.comm_list_view:
			SetBottonFocuse(false);
			break;

		case R.id.comm_btn_send:
			if (username != null) {
				final String content = edt_message.getText().toString();
				if (content != null) {
					new Thread() {
						@Override
						public void run() {
							msgsend = ConnectUtil.submitComment(intent.getStringExtra("docid"),
									username, uid, key, content,intent.getStringExtra("type"));
							handler.sendEmptyMessage(2);
						}
					}.start();
				} else {
					CommonUtil.showToast(CommentListActivity.this, "�������ݲ���Ϊ��!!!");
				}
			} else {
				CommonUtil.showToast(CommentListActivity.this, "��û�е�¼...");
				Intent intent = new Intent();
				intent.setClass(CommentListActivity.this, LoginActivity.class);
				startActivity(intent);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * ���ñ༭�����ʾ
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
