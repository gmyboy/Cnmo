package com.gmy.cnmo.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.util.StackOfActivity;

public class GuideActivity extends Activity {
	private ViewPager viewPager;
	private PagerAdapter adapter;
	private int[] res = { R.drawable.guide_page1, R.drawable.guide_page2, R.drawable.guide_page3,
			R.drawable.guide_page4, R.drawable.guide_page5 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// activity放入后台自定义stack中,方便以后退出
		StackOfActivity.getInstance().addActivity(this);
		setContentView(R.layout.activity_new);
		viewPager = (ViewPager) this.findViewById(R.id.viewpage);
		adapter = new MyPageAdapter(res, GuideActivity.this);
		viewPager.setAdapter(adapter);

	}

	public class MyPageAdapter extends PagerAdapter {
		private int[] res;
		private Context context;

		public MyPageAdapter(int[] res, Context context) {
			super();
			this.res = res;
			this.context = context;
		}

		@Override
		public int getCount() {
			return res.length;
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
			View iv = LayoutInflater.from(context).inflate(R.layout.list_viewpage, null);
			Button btn = (Button) iv.findViewById(R.id.list_viewpage_btn);
			ImageView img = (ImageView) iv.findViewById(R.id.list_viewpage_img);
			// 最后一张图片加上按钮，点击按钮之后结束activity
			if (position == 4) {
				btn.setVisibility(View.VISIBLE);
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
			}
			img.setImageResource(res[position]);
			((ViewPager) container).addView(iv, 0);
			return iv;
		}

	}
}
