package com.gmy.cnmo.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gmy.cnmo.entity.FirstImage;

/**
 * 
 * @author GMY
 * 
 */
public class JsonImgParser {
	/**
	 * 获取开机logo
	 * 
	 * @param url
	 * @return
	 */
	public static String getImgforLoGo(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				return jsonObject.getString("logoSrc");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取谍报馆推荐图片
	 * 
	 * @param url
	 * @return
	 */
	public static List<FirstImage> getJsonforDbg(String url) {
		HttpClient httpClient = new DefaultHttpClient();
		List<FirstImage> lists = null;
		FirstImage firstImage = null;
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				JSONObject jsondata = jsonObject.getJSONObject("data");
				JSONArray array = jsondata.getJSONArray("focusPicture");
				lists = new ArrayList<FirstImage>();
				JSONObject row = null;
				for (int i = 0; i < array.length(); i++) {
					firstImage = new FirstImage();
					row = array.getJSONObject(i);
					firstImage.setId(row.getString("id"));
					firstImage.setTitle(row.getString("title"));
					firstImage.setType(row.getString("date"));
					firstImage.setPicUrl(row.getString("picUrl"));
					lists.add(firstImage);
					firstImage = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
}
