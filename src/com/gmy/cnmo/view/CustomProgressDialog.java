package com.gmy.cnmo.view;

import com.gmy.cnmo.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 自定义风格的 progressdialog
 * 
 * @author GMY
 * 
 */
public class CustomProgressDialog extends Dialog {
	/**
	 * 要提示的信息
	 */
	private String msg;
	/**
	 * dialog的整体布局
	 */
	private LinearLayout view;
	/**
	 * 布局parm
	 */
	private LayoutParams params;
	/**
	 * 转动的imageview
	 */
	private ImageView image;
	/**
	 * 存放提示信息的Textview
	 */
	private TextView textView;
	/**
	 * progress旋转动画
	 */
	private Animation animation;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * 初始化
	 * 
	 * @param context
	 *            上下文
	 * @param msg
	 *            要提示的信息
	 */
	@SuppressWarnings("deprecation")
	public CustomProgressDialog(Context context, String msg) {
		super(context, R.style.loading_dialog);
		this.msg = msg;
		this.view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_dialog, null)
				.findViewById(R.id.dialog_view);// 得到加载view
		this.image = (ImageView) view.findViewById(R.id.img);
		this.textView = (TextView) view.findViewById(R.id.tipTextView);
		this.params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		this.animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
	}

	/**
	 * 创建
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.textView.setText(msg);
		this.image.startAnimation(animation);
		setContentView(view, params);
	}

	/**
	 * 失去焦点时，dialog会消失
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (!hasFocus) {
			dismiss();
		}
	}
}
