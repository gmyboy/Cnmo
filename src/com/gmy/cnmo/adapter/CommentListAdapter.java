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
import com.gmy.cnmo.entity.Comment;
import com.gmy.cnmo.util.AsyncImageLoader;

public class CommentListAdapter extends BaseAdapter {
	private Context context;
	private List<Comment> list;

	public CommentListAdapter(Context context, List<Comment> list) {
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
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_comment, null);
		}
		final ImageView imageView = (ImageView) convertView.findViewById(R.id.img_avatar);
		TextView text_username = (TextView) convertView.findViewById(R.id.text_username);
		TextView text_comment = (TextView) convertView.findViewById(R.id.text_comment);
		TextView text_date = (TextView) convertView.findViewById(R.id.text_date);
		Comment bean = list.get(position);
		text_username.setText(bean.getUsername());
		if (bean.getContent().length() < 25) {
			text_comment.setText(bean.getContent());
		} else {
			text_comment.setText(bean.getContent().substring(0, 23) + "...");
		}
		text_date.setText(bean.getDate().substring(0, 16));
		AsyncImageLoader loader = new AsyncImageLoader(context);
		// 将图片缓存至外部文件中
		loader.setCache2File(true); // false
		// 设置外部缓存文件夹
		loader.setCachedDir(context.getCacheDir().getAbsolutePath());
		loader.downloadImage(bean.getAvatar(), new AsyncImageLoader.ImageCallback() {

			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl) {
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					// 下载失败，设置默认图片
					imageView.setBackgroundResource(R.drawable.default_article_list);
				}
			}
		});
		return convertView;
	}

}
