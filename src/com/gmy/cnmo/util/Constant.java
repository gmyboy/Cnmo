package com.gmy.cnmo.util;

public class Constant {
	public static long TIMESTAMP = System.currentTimeMillis() / 1000;//��ȡ��ǰʱ��
	public static String TOKEN1 = MD5Util.MD5("d19cf361181f5a169c107872e1f5b722" + TIMESTAMP);//��ȡtoken
	
	public static String URL_LOGO = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_startuplogo&encoding=utf8&returnformat=json";//����logo
	public static String URL_APPUPDATE = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_updatedbg&encoding=utf8&returnformat=json";//app���½ӿ�
	
	public static String URL_DBG_TOUTIAO = "http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_indexrecommend&encoding=utf8&returnformat=json";//������ͷ���Ƽ�
	public static String URL_DBG_XINPIN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=1&isclass=0";//��������Ʒ
	public static String URL_DBG_JIAGE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=2&isclass=0";//�����ݼ۸�
	public static String URL_DBG_TIYAN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=3&isclass=0";//����������
	public static String URL_DBG_YINGYONG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=4&isclass=0";//������Ӧ��
	
	public static String URL_FSB_ZONGHE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=1&isclass=1";//�ۺ�
	public static String URL_FSB_AIMEIZHUANG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=2&isclass=1";//����ױ
	public static String URL_FSB_AIMEIFANG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=3&isclass=1";//������
	public static String URL_FSB_WENYANWEN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_articlelist&encoding=utf8&returnformat=json&cid=4&isclass=1";//������
	public static String URL_FSB_AIMEIZHI="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_aimeizhi&encoding=utf8&returnformat=json";//����־
	
	public static String URL_GYJ_ZONGHE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=1";//��Ӱ���ۺ�
	public static String URL_GYJ_JINPINYUANCHAUNG="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=2";//��Ӱ����Ʒԭ��
	public static String URL_GYJ_SHUMAMANTAN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=3";//��Ӱ��������̸
	public static String URL_GYJ_SHOUJIMEITU="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=4";//��Ӱ���ֻ���ͼ
	public static String URL_GYJ_PINBANMEITU="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tulist&encoding=utf8&returnformat=json&cid=5";//��Ӱ��ƽ����ͼ
	
	public static String URL_DETAIL_ARTICLE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";//������ϸ
	public static String URL_DETAIL_PICTURE="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";//ͼƬ������ϸ
	public static String URL_DETAIL_GYJ="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_tudetail&encoding=utf8&returnformat=json";// ��Ӱ��ͼƬ��ϸ
	public static String URL_DETAIL_AIMEIZHI="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_detail&encoding=utf8&returnformat=json";// ����־ͼƬ��ϸ
	
	public static String URL_COMMENT_LIST="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_commentlist&encoding=utf8&returnformat=json";//��ʾ���������б�
	public static String URL_COMMENT_SUBMIT="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1="+ TOKEN1+ "&module=api_libraries_sjdbg_comment&encoding=utf8&returnformat=json";//�ύ����
	
	public static String URL_LOGIN="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP+ "&token1="+ TOKEN1+ "&module=api_libraries_common_login&encoding=utf8&returnformat=json";//��¼�ӿ�
	public static String URL_REGISTER="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_common_register&encoding=utf8&returnformat=json";//ע��ӿ�
	public static String URL_COLLECTION="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_sjdbg_articlecollect&encoding=utf8&returnformat=json";//�ղ�����
	public static String URL_FEEDBACK="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_common_feedback&encoding=utf8&returnformat=json";//�������
	public static String URL_APPRECOMMEND="http://api.cnmo.com/client?apiid=3&timestamp="+ TIMESTAMP + "&token1=" + TOKEN1+ "&module=api_libraries_kuruanhui_indexrecommend&encoding=utf8&returnformat=json";//�������
}
