package com.gmy.cnmo.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmy.cnmo.R;
import com.gmy.cnmo.entity.Recommend;
import com.gmy.cnmo.util.AsyncImageLoader;
import com.gmy.cnmo.util.ConnectUtil;

public class RecommendAdapter extends BaseAdapter {
	private Context context;
	private List<Recommend> list;

	public RecommendAdapter(Context context, List<Recommend> list) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.list_recommend, null);
		}
		final ImageView imageView = (ImageView) convertView.findViewById(R.id.img_adp_pic);
		TextView text_title = (TextView) convertView.findViewById(R.id.text_adp_title);
		TextView text_content = (TextView) convertView.findViewById(R.id.text_adp_content);
		Button btn_download = (Button) convertView.findViewById(R.id.btn_download);
		Recommend bean = list.get(position);
		text_title.setText(bean.getName());
		text_content.setText(bean.getGoodname());

		AsyncImageLoader loader = new AsyncImageLoader(context);
		// 将图片缓存至外部文件中
		loader.setCache2File(true); // false
		// 设置外部缓存文件夹
		loader.setCachedDir(context.getCacheDir().getAbsolutePath());
		loader.downloadImage(bean.getHeadPictureSrc(), new AsyncImageLoader.ImageCallback() {

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
		final String url = bean.getDownloadUrl();
		btn_download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectUtil.goToWeb(context, url);
			}
		});
		return convertView;
	}
}
