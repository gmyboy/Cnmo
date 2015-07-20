package com.gmy.cnmo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.AiMeiZhi;
import com.gmy.cnmo.util.ImageDownloader;
import com.gmy.cnmo.util.OnImageDownload;

public class AiMeiZhiAdapter extends BaseAdapter {
	private Context context;
	private List<AiMeiZhi> list;
	private GridView gridView;

	public AiMeiZhiAdapter(Context context, List<AiMeiZhi> list, GridView gridView) {
		super();
		this.context = context;
		this.list = list;
		this.gridView = gridView;
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
		AiMeiZhi item;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.list_fsbamz, null);
			holder = new MyHolder();
			holder.img = (ImageView) convertView.findViewById(R.id.list_amz_image_item);
			holder.title = (TextView) convertView.findViewById(R.id.list_amz_text_item);
			convertView.setTag(holder);
		}
		holder = (MyHolder) convertView.getTag();
		item = list.get(position);
		// 下载图片缓存
		holder.img.setTag(item.getPic());
		ImageDownloader mdownloader = new ImageDownloader();
		if (mdownloader != null) {
			// 异步下载图片
			mdownloader.imageDownload(item.getPic(), holder.img, "/cnmo", context,
					new OnImageDownload() {
						@Override
						public void onDownloadSucc(Bitmap bitmap, String c_url, ImageView mimageView) {
							ImageView imageView = (ImageView) gridView.findViewWithTag(c_url);
							if (imageView != null) {
								imageView.setImageBitmap(bitmap);
								imageView.setTag("");
							}
						}
					});
		}
		// final ImageView i = holder.img;
		// AsyncImageLoader loader = new AsyncImageLoader(context);
		// // 将图片缓存至外部文件中
		// loader.setCache2File(true); // false
		// // 设置外部缓存文件夹
		// loader.setCachedDir(context.getCacheDir().getAbsolutePath());
		// loader.downloadImage(item.getPic(), new
		// AsyncImageLoader.ImageCallback() {
		//
		// @Override
		// public void onImageLoaded(Bitmap bitmap, String imageUrl) {
		// if (bitmap != null) {
		// i.setImageBitmap(bitmap);
		// } else {
		// // 下载失败，设置默认图片
		// i.setBackgroundResource(R.drawable.default_article_list);
		// }
		// }
		// });

		holder.title.setText(item.getTitle());
		return convertView;
	}

	class MyHolder {
		private TextView title;
		private ImageView img;
	}
}
