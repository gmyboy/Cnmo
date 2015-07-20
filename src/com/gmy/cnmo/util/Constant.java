package com.gmy.cnmo.util;

public class Constant {
	public static long TIMESTAMP = System.currentTimeMillis() / 1000;//获取当前时间
	public static String TOKEN1 = MD5Util.MD5("d19cf361181f5a169c107872e1f5b722" + TIMESTAMP);//获取token
	
	public static String URL_LOGO = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_startuplogo&encoding=utf8&returnformat=json";//开机logo
	public static String URL_APPUPDATE = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_updatedbg&encoding=utf8&returnformat=json";//app更新接口
	
	public static String URL_DBG_TOUTIAO = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_indexrecommend&encoding=utf8&returnformat=json";//谍报馆头条推荐
	public static String URL_DBG_XINPIN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=1&isclass=0";//谍报馆新品
	public static String URL_DBG_JIAGE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=2&isclass=0";//谍报馆价格
	public static String URL_DBG_TIYAN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=3&isclass=0";//谍报馆体验
	public static String URL_DBG_YINGYONG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=4&isclass=0";//谍报馆应用
	
	public static String URL_FSB_ZONGHE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=1&isclass=1";//综合
	public static String URL_FSB_AIMEIZHUANG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=2&isclass=1";//爱美妆
	public static String URL_FSB_AIMEIFANG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=3&isclass=1";//爱美访
	public static String URL_FSB_WENYANWEN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=4&isclass=1";//雯琰文
	public static String URL_FSB_AIMEIZHI="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_aimeizhi&encoding=utf8&returnformat=json";//爱美志
	
	public static String URL_GYJ_ZONGHE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=1";//光影集综合
	public static String URL_GYJ_JINPINYUANCHAUNG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=2";//光影集精品原创
	public static String URL_GYJ_SHUMAMANTAN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=3";//光影集数码漫谈
	public static String URL_GYJ_SHOUJIMEITU="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=4";//光影集手机美图
	public static String URL_GYJ_PINBANMEITU="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=5";//光影集平板美图
	
	public static String URL_DETAIL_ARTICLE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";//文章详细
	public static String URL_DETAIL_PICTURE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";//图片文章详细
	public static String URL_DETAIL_GYJ="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tudetail&encoding=utf8&returnformat=json";// 光影集图片详细
	public static String URL_DETAIL_AIMEIZHI="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";// 爱美志图片详细
	
	public static String URL_COMMENT_LIST="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_commentlist&encoding=utf8&returnformat=json";//显示文章评论列表
	public static String URL_COMMENT_SUBMIT="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_comment&encoding=utf8&returnformat=json";//提交评论
	
	public static String URL_LOGIN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP+ "&token1="+ TOKEN1+ "&module=api_libraries_common_login&encoding=utf8&returnformat=json";//登录接口
	public static String URL_REGISTER="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_common_register&encoding=utf8&returnformat=json";//注册接口
	public static String URL_COLLECTION="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_sjdbg_articlecollect&encoding=utf8&returnformat=json";//收藏文章
	public static String URL_FEEDBACK="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_common_feedback&encoding=utf8&returnformat=json";//意见反馈
	public static String URL_APPRECOMMEND="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_kuruanhui_indexrecommend&encoding=utf8&returnformat=json";//意见反馈
}
