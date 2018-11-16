package com.yanglu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;

import com.yanglu.common.MyX509TrustManager;
import com.yanglu.common.WeChatAccessToken;

import net.sf.json.JSONObject;
 

 
public class weChatUtils {
	// 微信公众号的appId以及secret
	public static String appId = GlobalConstants.getInterfaceUrl("appid");
	public static String secret = GlobalConstants.getInterfaceUrl("AppSecret");
	//获取code的url
	public static String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
	// 获取网页授权access_token的Url，和基础服务access_token不同，记得区分
	public static String getAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	// 刷新网页授权access_token的Url，和基础服务access_token不同，记得区分
	private static String getRefreshAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
	// 检验授权凭证access_token是否有效,和基础服务access_token不同，记得区分
	private static String checkAccessTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID";
	// 获取用户信息的Url
	private static String getWXUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	private static Logger logger = Logger.getLogger(weChatUtils.class);
	/**
	 * 获取code值
	 * @throws IOException 
	 */
	public static String getCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//String redirectUrl = request.getRequestURL().toString();
		/*String redirectUrl = "http://21x91010v5.iok.la/weixin_read/f/weixin/index";
		redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
		System.out.println(weChatUtils.appId);
		String url = weChatUtils.getCodeUrl.replace("APPID", weChatUtils.appId).replace("REDIRECT_URI", redirectUrl);
		System.out.println("getcodeUrl:" + url);*/
		String redirectUrl = request.getRequestURL().toString();
		redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
		System.out.println("redirectUrl："+redirectUrl);
		String url = weChatUtils.getCodeUrl.replace("APPID", weChatUtils.appId).replace("REDIRECT_URI", redirectUrl);
		System.out.println("url"+url);
		//String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx36fe818c5b126c59&redirect_uri=http%3a%2f%2f21x91010v5.iok.la%2fweixin_read%2ff%2fweixin%2findex?response_type=code&scope=snsapi_base&state=1&connect_redirect=1#wechat_redirect";
		return url;
		//response.sendRedirect(url);
	}
	
	/**
	 * 获取openid
	 * @param request
	 * @param response
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getOpenId(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
		//Map<String,Object> map = new HashMap<String,Object>();  
		//String redirectUrl = request.getRequestURL().toString();
		/*String redirectUrl = "http://21x91010v5.iok.la/weixin_read/f/weixin/index";
		redirectUrl = URLEncoder.encode(redirectUrl,"UTF-8");
		String codeurl = weChatUtils.getCodeUrl.replace("APPID", weChatUtils.appId).replace("REDIRECT_URI", redirectUrl);
		System.out.println("getcodeUrl:" + codeurl);
		response.sendRedirect(codeurl);*/
		String code = request.getParameter("code");//微信会返回code值，用code获取openid
		logger.debug("get openid start" + code);
		if(code == null) {
			return "";
		}else {
			System.out.println("getcode:"+code);
			String url = weChatUtils.getAccessTokenUrl.replace("APPID", weChatUtils.appId).replace("SECRET", secret)
					.replace("CODE", code);
			System.out.println(url);
			JSONObject jsonObj=JSONObject.fromObject(httpRequest(url, "POST", null));
			WeChatAccessToken wechat = (WeChatAccessToken) JSONObject.toBean(jsonObj,WeChatAccessToken.class);
			//String openId = WxUtils.getOpendId(code);
			String openid = wechat.getOpenid();
			System.out.println("getopenid:"+openid);
			//map.put("openid", openid);
			return openid;
		}
	}
	
	/**
	 * 根据code获取到网页授权access_token
	 * 
	 * @param code
	 *            微信回调后带有的参数code值
	 */
	/*public static WeChatAccessToken getAccessToken(String code) {
		String url = weChatUtils.getAccessTokenUrl.replace("APPID", weChatUtils.appId).replace("SECRET", secret)
				.replace("CODE", code);
		JSONObject jsonObj=JSONObject.fromObject(httpRequest(url, "POST", null));
		return (WeChatAccessToken) JSONObject.toBean(jsonObj,WeChatAccessToken.class);
	}*/
 

	/**
	 * 根据在获取accessToken时返回的refreshToken刷新accessToken
	 * 
	 * @param refreshToken
	 */
	/*public static WeChatAccessToken getRefreshAccessToken(String refreshToken) {
		String url = weChatUtils.getRefreshAccessTokenUrl.replace("APPID", weChatUtils.appId).replace("REFRESH_TOKEN",
				refreshToken);
		JSONObject jsonObj=JSONObject.fromObject(httpRequest(url, "POST", null));
		return (WeChatAccessToken) JSONObject.toBean(jsonObj,WeChatAccessToken.class);
	}*/
 
	/**
	 * 获取微信用户信息
	 * 
	 * @param openId
	 *            微信标识openId
	 * @param accessToken
	 *            微信网页授权accessToken
	 */
	/*public static WechatUserinfo getWXUserInfoUrl(String openId, String accessToken) {
		String url = weChatUtils.getWXUserInfoUrl.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj=JSONObject.fromObject(httpRequest(url, "POST", null));
		return (WechatUserinfo) JSONObject.toBean(jsonObj,WechatUserinfo.class);
	}*/
 
	/**
	 * 检验授权凭证accessToken是否有效
	 * 
	 * @param openId
	 * @param accessToken
	 */
	/*public WechatAccessTokenCheck checkAccessToken(String openId, String accessToken) {
		String url = weChatUtils.checkAccessTokenUrl.replace("OPENID", openId).replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObj=JSONObject.fromObject(httpRequest(url, "POST", null));
		return (WechatAccessTokenCheck) JSONObject.toBean(jsonObj,WechatAccessTokenCheck.class);
	}*/
 
	/**
	 * get或者post请求
	 * 
	 * @param requestUrl
	 * @param requestMethod
	 *            GET or POST 需要大写*
	 * @param outputStr
	 * @return
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();
			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			System.out.println("Weixin server connection timed out." + ce.getMessage());
		} catch (Exception e) {
			System.out.println("https request error:{}" + e.getMessage());
		}
		return buffer.toString();
	}
 
}

