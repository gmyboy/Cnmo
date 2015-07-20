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

import com.gmy.cnmo.entity.AiMeiZhi;
import com.gmy.cnmo.entity.AiMeiZhiImageDetail;
import com.gmy.cnmo.entity.ArticleDetail;
import com.gmy.cnmo.entity.Comment;
import com.gmy.cnmo.entity.DbgArticle;
import com.gmy.cnmo.entity.FirstArticle;
import com.gmy.cnmo.entity.GuangYingJi;
import com.gmy.cnmo.entity.ImageDetail;
import com.gmy.cnmo.entity.Recommend;

/**
 * 
 * 
 * @author GMY
 * 
 */
public class JsonParser {
	/**
	 * 获取爱美志详细内容
	 * 
	 * @param url
	 * @return
	 */
	public static AiMeiZhiImageDetail getImgJsonForAmz(String url) {
		AiMeiZhiImageDetail item = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONObject row = jsondata.getJSONObject("info");
					item = new AiMeiZhiImageDetail();
					item.setId(row.getString("docid"));
					item.setCid(row.getString("cid"));
					item.setIsCollected(row.getString("isCollected"));
					item.setType(row.getString("type"));
					item.setAuthor(row.getString("author"));
					item.setTitle(row.getString("title"));
					item.setDate(row.getString("date"));
					item.setDocUrl(row.getString("docUrl"));
					JSONArray array1 = row.getJSONArray("content");
					String[] mStrings1 = new String[array1.length()];
					for (int i = 0; i < array1.length(); i++) {
						mStrings1[i] = array1.getString(i);
					}
					item.setContent(mStrings1);
					item.setPicurl(mStrings1);
					JSONArray array2 = row.getJSONArray("picUrl");
					String[] mStrings2 = new String[array2.length()];
					for (int i = 0; i < array2.length(); i++) {
						mStrings2[i] = array2.getString(i);
					}
					item.setPicurl(mStrings2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 获取光影集详细内容
	 * 
	 * @param url
	 * @return
	 */
	public static ImageDetail getImgJsonForGyj(String url) {
		ImageDetail item = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);

				if (jsonObject.getString("status").equals("1")) {
					JSONObject row = jsonObject.getJSONObject("data");
					item = new ImageDetail();
					item.setId(row.getString("id"));
					item.setTitle(row.getString("title"));
					item.setCid(row.getString("cid"));
					item.setIsCollected(row.getString("isCollected"));
					item.setDate(row.getString("date"));
					item.setAuthor(row.getString("author"));
					item.setDigest(row.getString("digest"));
					JSONArray array = row.getJSONArray("picUrl");
					String[] mStrings = new String[array.length()];
					for (int i = 0; i < array.length(); i++) {
						mStrings[i] = array.getString(i);
					}
					item.setPicUrl(mStrings);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 获取谍报馆推荐文章的json数据
	 * 
	 * @param url
	 * @return
	 */
	public static List<FirstArticle> getJsonForFirst(String url) {
		List<FirstArticle> lists = null;
		FirstArticle article = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONObject jsonlist = jsondata.getJSONObject("articleList");
					JSONArray array = jsonlist.getJSONArray("list");
					lists = new ArrayList<FirstArticle>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						article = new FirstArticle();
						row = array.getJSONObject(i);
						article.setId(row.getString("id"));
						article.setTitle(row.getString("title"));
						article.setDate(row.getString("date"));
						article.setPicUrl(row.getString("picUrl"));
						article.setType(row.getString("type"));
						article.setContent(row.getString("content"));
						lists.add(article);
						article = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取谍报馆新新品，价格，体验，应用的json数据
	 * 
	 * @param url
	 * @return
	 */
	public static List<DbgArticle> getJsonforDbg(String url) {
		List<DbgArticle> lists = null;
		DbgArticle article = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONArray array = jsondata.getJSONArray("result");
					lists = new ArrayList<DbgArticle>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						article = new DbgArticle();
						row = array.getJSONObject(i);
						article.setContent(row.getString("content"));
						article.setDocid(row.getString("docid"));
						article.setTitle(row.getString("title"));
						article.setPicUrl(row.getString("picUrl"));
						article.setType(row.getString("type"));
						article.setDate(row.getString("date"));
						lists.add(article);
						article = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取爱美志表单
	 * 
	 * @param url
	 * @return
	 */
	public static List<AiMeiZhi> getJsonforAiMeiZhi(String url) {
		List<AiMeiZhi> lists = null;
		AiMeiZhi article = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();

		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONArray array = jsondata.getJSONArray("result");
					lists = new ArrayList<AiMeiZhi>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						article = new AiMeiZhi();
						// 初始化爱美志对象
						row = array.getJSONObject(i);
						article.setDocid(row.getString("docid"));
						article.setPic(row.getString("pic"));
						article.setTitle(row.getString("title"));
						article.setAuthor(row.getString("author"));
						article.setDate(row.getString("date"));
						article.setContent(row.getString("content"));
						lists.add(article);
						article = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取文章评论列表
	 * 
	 * @param url
	 */
	public static List<Comment> getCommentJson(String url) {
		List<Comment> lists = null;
		Comment item = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONArray array = jsondata.getJSONArray("result");
					lists = new ArrayList<Comment>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						item = new Comment();
						// 初始化对象
						row = array.getJSONObject(i);
						item.setUid(row.getString("uid"));
						item.setUsername(row.getString("username"));
						item.setAvatar(row.getString("avatar"));
						item.setDate(row.getString("date"));
						item.setContent(row.getString("content"));
						lists.add(item);
						item = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取光影集json对象
	 * 
	 * @param url
	 */
	public static List<GuangYingJi> getJsonforGYJ(String url) {
		List<GuangYingJi> lists = null;
		GuangYingJi article = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONArray array = jsondata.getJSONArray("result");
					lists = new ArrayList<GuangYingJi>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						article = new GuangYingJi();
						// 初始化对象
						row = array.getJSONObject(i);
						article.setPicid(row.getString("picid"));
						article.setTitle(row.getString("title"));
						article.setDate(row.getString("date"));
						article.setContent(row.getString("content"));
						article.setPicUrl(row.getString("picUrl"));
						lists.add(article);
						article = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	/**
	 * 获取文章详细内容
	 * 
	 * @param url
	 */
	public static ArticleDetail getArticleDetail(String url) {
		ArticleDetail article = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					article = new ArticleDetail();
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONObject jsoninfo = jsondata.getJSONObject("info");
					article.setContent(jsoninfo.getString("content"));
					article.setCid(jsoninfo.getString("cid"));// 谍报馆1 生活2 图片3
					article.setDocid(jsoninfo.getString("docid"));
					article.setType(jsoninfo.getString("type"));
					article.setDate(jsoninfo.getString("date"));
					article.setIscollected(jsoninfo.getString("isCollected"));
					article.setTitle(jsoninfo.getString("title"));
					JSONObject jsonpage = jsoninfo.getJSONObject("paging");
					article.setTotal(jsonpage.getString("total"));
					article.setCurrentpage(jsonpage.getString("currentPage"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return article;
	}

	/**
	 * 获取推荐应用列表
	 * 
	 * @param url
	 */
	public static List<Recommend> getRecommend(String url) {
		List<Recommend> lists = null;
		Recommend item = null;
		JSONObject jsonObject;
		HttpClient httpClient = new DefaultHttpClient();
		try {
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String msg = EntityUtils.toString(response.getEntity());
				jsonObject = new JSONObject(msg);
				if (jsonObject.getString("status").equals("1")) {
					JSONObject jsondata = jsonObject.getJSONObject("data");
					JSONArray array = jsondata.getJSONArray("recommendAppList");
					lists = new ArrayList<Recommend>();
					JSONObject row = null;
					for (int i = 0; i < array.length(); i++) {
						item = new Recommend();
						// 初始化对象
						row = array.getJSONObject(i);
						item.setAppid(row.getString("appid"));
						item.setName(row.getString("name"));
						item.setGoodname(row.getString("goodName"));
						item.setFilesize(row.getString("filesize"));
						item.setDownloads(row.getString("downloads"));
						item.setDownloadUrl(row.getString("downloadUrl"));
						item.setScore(row.getString("score"));
						item.setHeadPictureSrc(row.getString("headPictureSrc"));
						item.setApppackage(row.getString("package"));
						lists.add(item);
						item = null;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}
}