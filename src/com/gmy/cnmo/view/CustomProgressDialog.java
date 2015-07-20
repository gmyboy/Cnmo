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
 * �Զ������ progressdialog
 * 
 * @author GMY
 * 
 */
public class CustomProgressDialog extends Dialog {
	/**
	 * Ҫ��ʾ����Ϣ
	 */
	private String msg;
	/**
	 * dialog�����岼��
	 */
	private LinearLayout view;
	/**
	 * ����parm
	 */
	private LayoutParams params;
	/**
	 * ת����imageview
	 */
	private ImageView image;
	/**
	 * �����ʾ��Ϣ��Textview
	 */
	private TextView textView;
	/**
	 * progress��ת����
	 */
	private Animation animation;

	public CustomProgressDialog(Context context) {
		super(context);
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	/**
	 * ��ʼ��
	 * 
	 * @param context
	 *            ������
	 * @param msg
	 *            Ҫ��ʾ����Ϣ
	 */
	@SuppressWarnings("deprecation")
	public CustomProgressDialog(Context context, String msg) {
		super(context, R.style.loading_dialog);
		this.msg = msg;
		this.view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.view_dialog, null)
				.findViewById(R.id.dialog_view);// �õ�����view
		this.image = (ImageView) view.findViewById(R.id.img);
		this.textView = (TextView) view.findViewById(R.id.tipTextView);
		this.params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT);
		this.animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
	}

	/**
	 * ����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.textView.setText(msg);
		this.image.startAnimation(animation);
		setContentView(view, params);
	}

	/**
	 * ʧȥ����ʱ��dialog����ʧ
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (!hasFocus) {
			dismiss();
		}
	}
}
