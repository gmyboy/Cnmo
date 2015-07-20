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

public class GuangYingJiAdapter extends BaseAdapter {
	private Context context;
	private List<GuangYingJi> list;

	public GuangYingJiAdapter(Context context, List<GuangYingJi> list) {
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
		GuangYingJi item;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_fsbamz, null);
			holder = new MyHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.list_amz_image_item);
			holder.title = (TextView) convertView.findViewById(R.id.list_amz_text_item);
			convertView.setTag(holder);
		}
		holder = (MyHolder) convertView.getTag();
		item = list.get(position);
		holder.title.setText(item.getTitle());
		final ImageView i = holder.img;
		AsyncImageLoader loader = new AsyncImageLoader(context);
		// 将图片缓存至外部文件中
		loader.setCache2File(true); // false
		// 设置外部缓存文件夹
		loader.setCachedDir(context.getCacheDir().getAbsolutePath());
		loader.downloadImage(item.getPicUrl(), new AsyncImageLoader.ImageCallback() {

			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl) {
				if (bitmap != null) {
					i.setImageBitmap(bitmap);
				} else {
					// 下载失败，设置默认图片
					i.setBackgroundResource(R.drawable.default_article_list);
				}
			}
		});
		return convertView;
	}

	class MyHolder {
		private TextView title;
		private ImageView img;
	}
}
