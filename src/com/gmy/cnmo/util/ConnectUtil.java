package com.gmy.cnmo.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.gmy.cnmo.entity.User;

public class ConnectUtil {
	/**
	 * 提交评论
	 * 
	 * @param docid
	 * @param ispic
	 * @param username
	 * @param uid
	 * @param content
	 * @return
	 */
	public static String submitComment(String docid, String username, String uid, String key,
			String content,String ispic) {
		final String str1 = docid;
		// final String str2 = ispic;
		final String str3 = username;
		final String str4 = uid;
		final String str5 = content;
		final String str6 = key;
		final String str7 = ispic;
		final HttpClient httpClient = new DefaultHttpClient();
		try {

			HttpPost post = new HttpPost(Constant.URL_COMMENT_SUBMIT);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("authkey", str6));
			params.add(new BasicNameValuePair("docid", str1));
			// params.add(new BasicNameValuePair("ispic", str2));
			params.add(new BasicNameValuePair("username", str3));
			params.add(new BasicNameValuePair("uid", str4));
			params.add(new BasicNameValuePair("content", str5));
			if (ispic.equals("1")) {
				params.add(new BasicNameValuePair("ispic", str7));
			}
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				String message = jsonObject.getString("message");
				// 返回json message
				return message;
			} else {
				return "评论失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "评论失败";
		}
	}

	/**
	 * 收藏(取消收藏)文章
	 * 
	 * @param str1
	 *            docid
	 * @param str2
	 *            cid
	 * @param str3
	 *            authkey
	 * @param str4
	 *            uid
	 * @param str5
	 *            method
	 * @return
	 */
	public static String collection(String str1, String str2, String str3, String str4, String str5) {
		final String docid = str1;
		final String cid = str2;
		final String authkey = str3;
		final String uid = str4;
		final String method = str5;
		final HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpPost post = new HttpPost(Constant.URL_COLLECTION);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("docid", docid));
			params.add(new BasicNameValuePair("cid", cid));
			params.add(new BasicNameValuePair("authkey", authkey));
			params.add(new BasicNameValuePair("uid", uid));
			params.add(new BasicNameValuePair("method", method));
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				String message = jsonObject.getString("message");
				// 返回json message
				return message;
			} else {
				return "收藏失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "收藏失败";
		}
	}

	/**
	 * 注册帐号
	 * 
	 * @param str2
	 *            用户名
	 * @param str3
	 *            密码
	 * @param str4
	 *            邮箱
	 * @return
	 */
	public static String register(String str2, String str3, String str4) {
		final String username = str2;
		final String password = str3;
		final String email = str4;
		final HttpClient httpClient = new DefaultHttpClient();
		try {

			HttpPost post = new HttpPost(Constant.URL_REGISTER);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("email", email));
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				String message = jsonObject.getString("message");
				// 返回json message
				return message;
			} else {
				return "注册失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "注册失败";
		}
	}

	/**
	 * 登录
	 * 
	 * @param str2
	 *            用户名
	 * @param str3
	 *            密码
	 * @return
	 */
	public static User checkLogin(String str2, String str3) {
		User user;
		final HttpClient httpClient = new DefaultHttpClient();
		final String username = str2;
		final String password = str3;
		try {
			HttpPost post = new HttpPost(Constant.URL_LOGIN);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));// 用户名
			params.add(new BasicNameValuePair("password", password));// 密码
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				user = new User();
				JSONObject jsonObject = new JSONObject(msg);
				JSONObject data = jsonObject.getJSONObject("data");
				user.setUid(data.getString("uid"));
				user.setKey(data.getString("key"));
				// 返回json message
				return user;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 反馈信息
	 * 
	 * @param str2
	 *            用户名
	 * @param str3
	 *            密码
	 * @return
	 */
	public static String feedBack(String username, String uid, String imei, String email,
			String content) {
		final HttpClient httpClient = new DefaultHttpClient();
		final String str1 = username;
		final String str2 = uid;
		final String str3 = imei;
		final String str4 = email;
		final String str5 = content;
		try {
			HttpPost post = new HttpPost(Constant.URL_FEEDBACK);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("clientversion", "1.0"));// 版本号
			params.add(new BasicNameValuePair("username", str1));// 用户名
			params.add(new BasicNameValuePair("uid", str2));// 用户id
			params.add(new BasicNameValuePair("imei", str3));// 手机imei码
			params.add(new BasicNameValuePair("email", str4));// 用户邮箱
			params.add(new BasicNameValuePair("content", str5));// 反馈具体内容
			post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				JSONObject jsonObject = new JSONObject(msg);
				String message = jsonObject.getString("message");
				// 返回json message
				return message;
			} else {
				return "反馈发送失败";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "反馈发送失败";
		}
	}

	/*
	 * 访问官网
	 */
	public static void goToWeb(Context context, String url) {
		Uri uriUrl = Uri.parse(url);
		Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
		context.startActivity(launchBrowser);
	}
}
