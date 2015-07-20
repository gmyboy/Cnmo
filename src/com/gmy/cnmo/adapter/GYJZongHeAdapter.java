package com.gmy.cnmo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.GuangYingJi;
import com.gmy.cnmo.util.AsyncImageLoader;

public class GYJZongHeAdapter extends BaseAdapter {
	private Context context;
	private List<GuangYingJi> list;

	public GYJZongHeAdapter(Context context, List<GuangYingJi> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyHolder holder;
		GuangYingJi article;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_article, null);
			holder = new MyHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.list_article_img_pic);
			holder.title = (TextView) convertView.findViewById(R.id.list_article_text_title);
			holder.content1 = (TextView) convertView.findViewById(R.id.list_article_text_content1);
			holder.content2 = (TextView) convertView.findViewById(R.id.list_article_text_content2);
			holder.date = (TextView) convertView.findViewById(R.id.list_article_text_date);
			convertView.setTag(holder);
		}
		holder = (MyHolder) convertView.getTag();
		article = list.get(position);
		// ����ͼƬ����
		final ImageView i = holder.img;
		AsyncImageLoader loader = new AsyncImageLoader(context);
		// ��ͼƬ�������ⲿ�ļ���
		loader.setCache2File(true); // false
		// �����ⲿ�����ļ���
		loader.setCachedDir(context.getCacheDir().getAbsolutePath());
		loader.downloadImage(article.getPicUrl(), new AsyncImageLoader.ImageCallback() {

			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl) {
				if (bitmap != null) {
					i.setImageBitmap(bitmap);
				} else {
					// ����ʧ�ܣ�����Ĭ��ͼƬ
					i.setBackgroundResource(R.drawable.default_article_list);
				}
			}
		});
		holder.title.setText(article.getTitle());
		holder.content1.setText(article.getContent().substring(0, 20));
		holder.content2.setText(article.getContent().substring(21));
		holder.date.setText(article.getDate());
		return convertView;
	}

	class MyHolder {
		private ImageView img;// ���ͼƬ
		private TextView title;// ����
		private TextView content1;// ��һ������
		private TextView content2;// �ڶ�������
		private TextView date;// ʱ��
	}

}
