package com.yanglu.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.yanglu.util.GlobalConstants;
import com.yanglu.util.HttpUtils;

import net.sf.json.JSONObject;

/**
 * ClassName: WeChatTask
 * @Description: 微信两小时定时任务体
 */
public class WeChatTask {
	/**
	 * @Description: 任务执行体
	 * @param @throws Exception
	 */
	public void getToken_getTicket() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		//获取token执行体
		params.put("grant_type", "client_credential");
		params.put("appid", GlobalConstants.getInterfaceUrl("appid"));
		params.put("secret", GlobalConstants.getInterfaceUrl("AppSecret"));
		//System.out.println(GlobalConstants.getInterfaceUrl("tokenUrl"));
		String jstoken = HttpUtils.sendGet(
				GlobalConstants.getInterfaceUrl("tokenUrl"), params);
		String access_token = JSONObject.fromObject(jstoken).getString(
				"access_token"); // 获取到token并赋值保存
		GlobalConstants.interfaceUrlProperties.put("access_token", access_token);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
		
		//获取jsticket的执行体
		/*params.clear();
		params.put("access_token", access_token);
		params.put("type", "jsapi");
		String jsticket = HttpUtils.sendGet(
				GlobalConstants.getInterfaceUrl("ticketUrl"), params);
		System.out.println(GlobalConstants.getInterfaceUrl("ticketUrl"));
		String jsapi_ticket = JSONObject.fromObject(jsticket).getString(
				"ticket"); 
		GlobalConstants.interfaceUrlProperties.put("jsapi_ticket", jsapi_ticket); // 获取到js-SDK的ticket并赋值保存
		System.out.println("jsapi_ticket================================================" + jsapi_ticket);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"token为=============================="+access_token);
*/
	}
}