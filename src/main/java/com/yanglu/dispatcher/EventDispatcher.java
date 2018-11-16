package com.yanglu.dispatcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yanglu.message.resp.Article;
import com.yanglu.message.resp.NewsMessage;
import com.yanglu.util.HttpPostUploadUtil;
import com.yanglu.util.MessageUtil;

import net.sf.json.JSONObject;

public class EventDispatcher {
    public static String processEvent(Map<String, String> map) {
    	String openid=map.get("FromUserName"); //用户openid
    	String mpid=map.get("ToUserName");   //公众号原始ID 
    	NewsMessage newmsg=new NewsMessage();
    	newmsg.setToUserName(openid);
    	newmsg.setFromUserName(mpid);
    	newmsg.setCreateTime(new Date().getTime());
    	newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

    	if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { // 图文消息
    	    System.out.println("==============这是图文消息！");
    	    Article article=new Article();
    	    HttpPostUploadUtil util=new HttpPostUploadUtil();
    	    String filepath="C:\\Users\\Hester\\Desktop\\pic1.jpg";  
    	    Map<String, String> textMap = new HashMap<String, String>();  
    	    textMap.put("name", "testname");  
    	    Map<String, String> fileMap = new HashMap<String, String>();  
    	    fileMap.put("userfile", filepath); 
    	    String mediaidrs = util.formUpload(textMap, fileMap);
    	    System.out.println(mediaidrs);
    	    String PicUrl=JSONObject.fromObject(mediaidrs).getString("media_id");
    	    article.setDescription("这是图文消息 "); //图文消息的描述
    	    //article.setPicUrl("D:/workplace/eclipseSpace/testWechat/src/main/webapp/pic1.jpg"); //图文消息图片地址
    	    article.setPicUrl(PicUrl); //图文消息图片地址
    	    article.setTitle("图文消息");  //图文消息标题
    	    article.setUrl("http://www.baidu.com");  //图文 url 链接
    	    List<Article> list=new ArrayList<Article>();
    	    list.add(article);//这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
    	    newmsg.setArticleCount(list.size());
    	    newmsg.setArticles(list);
    	    return MessageUtil.newsMessageToXml(newmsg);
    	}
    	
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) { //关注事件
            System.out.println("==============这是关注事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) { //取消关注事件
            System.out.println("==============这是取消关注事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_SCAN)) { //扫描二维码事件
            System.out.println("==============这是扫描二维码事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_LOCATION)) { //位置上报事件
            System.out.println("==============这是位置上报事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_CLICK)) { //自定义菜单点击事件
            System.out.println("==============这是自定义菜单点击事件！");
        }
         
        if (map.get("Event").equals(MessageUtil.EVENT_TYPE_VIEW)) { //自定义菜单View事件
            System.out.println("==============这是自定义菜单View事件！");
        }
         
        return null;
    }
}
