package com.gmy.cnmo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.FirstArticle;
import com.gmy.cnmo.util.ImageDownloader;
import com.gmy.cnmo.util.OnImageDownload;

public class FirstArticlelistAdapter extends BaseAdapter {
	private Context context;
	private List<FirstArticle> list;
	ImageDownloader mdownloader;
	private ListView listView;

	public FirstArticlelistAdapter(Context context, List<FirstArticle> list, ListView listView) {
		super();
		this.context = context;
		this.list = list;
		this.listView = listView;
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
		FirstArticle article;
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
		holder.img.setTag(article.getPicUrl());
		ImageDownloader mdownloader = new ImageDownloader();
		holder.img.setImageResource(R.drawable.default_article_list);
		if (mdownloader != null) {
			// 异步下载图片
			mdownloader.imageDownload(article.getPicUrl(), holder.img, "/cnmo", context,
					new OnImageDownload() {
						@Override
						public void onDownloadSucc(Bitmap bitmap, String c_url, ImageView mimageView) {
							ImageView imageView = (ImageView) listView.findViewWithTag(c_url);
							if (imageView != null) {
								imageView.setImageBitmap(bitmap);
								imageView.setTag("");
							}
						}
					});
		}
	
		holder.title.setText(article.getTitle());
		holder.content1.setText(article.getContent().substring(0, 20));
		holder.content2.setText(article.getContent().substring(21));
		holder.date.setText(article.getDate());
		return convertView;
	}

	class MyHolder {
		private ImageView img;// 左边图片
		private TextView title;// 标题
		private TextView content1;// 第一行内容
		private TextView content2;// 第二行内容
		private TextView date;// 时间
	}

}
