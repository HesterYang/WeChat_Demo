package com.yanglu.dispatcher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.yanglu.message.resp.Article;
import com.yanglu.message.resp.Image;
import com.yanglu.message.resp.ImageMessage;
import com.yanglu.message.resp.NewsMessage;
import com.yanglu.message.resp.TextMessage;
import com.yanglu.util.HttpPostUploadUtil;
import com.yanglu.util.MessageUtil;

import net.sf.json.JSONObject;

 
/**
 * ClassName: MsgDispatcher
 * @Description: 消息业务处理分发器
 * 
 */
public class MsgDispatcher {
    public static String processMessage(Map<String, String> map) throws Exception {
    	
    	String openid=map.get("FromUserName"); //用户openid
    	String mpid=map.get("ToUserName");   //公众号原始ID 
    	         
    	//普通文本消息
    	TextMessage txtmsg=new TextMessage();
    	txtmsg.setToUserName(openid);
    	txtmsg.setFromUserName(mpid);
    	txtmsg.setCreateTime(new Date().getTime());
    	txtmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
    	         
    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
    		System.out.println("==============这是文本消息！");
    		String content=map.get("Content");
    	    if("1".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是1！");
    	    }else if("2".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是2！");
    	    }else if("3".equals(content)){
    	        txtmsg.setContent("你好，你发送的内容是3！");
    	    }else if("4".equals(content)){
    	        txtmsg.setContent("<a href=\"http://www.baidu.com\">进入百度界面</a>");
    	    }else{
    	        txtmsg.setContent("你好，欢迎！");
    	    }
    	    //txtmsg.setContent("success");
    	    return MessageUtil.textMessageToXml(txtmsg);
    	}
    	
    	/*//对图文消息
    	NewsMessage newmsg=new NewsMessage();
    	newmsg.setToUserName(openid);
    	newmsg.setFromUserName(mpid);
    	newmsg.setCreateTime(new Date().getTime());
    	newmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);

    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
    	    System.out.println("==============这是图片消息！");
    	    Article article=new Article();
    	    article.setDescription("这是图文消息 "); //图文消息的描述
    	    article.setPicUrl("/wechat/src/main/webapp/pic1.jpg"); //图文消息图片地址(必须为微信服务器上的地址)
    	    article.setTitle("图文消息");  //图文消息标题
    	    article.setUrl("http://www.baidu.com");  //图文 url 链接
    	    List<Article> list=new ArrayList<Article>();
    	    list.add(article);     //这里发送的是单图文，如果需要发送多图文则在这里 list 中加入多个 Article 即可！
    	    newmsg.setArticleCount(list.size());
    	    newmsg.setArticles(list);
    	    return MessageUtil.newsMessageToXml(newmsg);
    	} */
    	
    	//图片消息回复
    	ImageMessage imgmsg = new ImageMessage();
    	imgmsg.setToUserName(openid);
    	imgmsg.setFromUserName(mpid);
    	imgmsg.setCreateTime(new Date().getTime());
    	imgmsg.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_Image);
    	if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 关注事件
    	    System.out.println("==============这是图片事件！");
    	    Image img = new Image();
    	    HttpPostUploadUtil util=new HttpPostUploadUtil();
    	    String filepath="C:\\Users\\Hester\\Desktop\\pic1.jpg";  
    	    Map<String, String> textMap = new HashMap<String, String>();  
    	    textMap.put("name", "testname");  
    	    Map<String, String> fileMap = new HashMap<String, String>();  
    	    fileMap.put("userfile", filepath); 
    	    String mediaidrs = util.formUpload(textMap, fileMap);
    	    System.out.println(mediaidrs);
    	    String mediaid=JSONObject.fromObject(mediaidrs).getString("media_id");
    	    img.setMediaId(mediaid);
    	    imgmsg.setImage(img);
    	    return MessageUtil.imageMessageToXml(imgmsg);
    	}
    	
        /*if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { // 文本消息
            System.out.println("==============这是文本消息！");
        }
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) { // 图片消息
            System.out.println("==============这是图片消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) { // 链接消息
            System.out.println("==============这是链接消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) { // 位置消息
            System.out.println("==============这是位置消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) { // 视频消息
            System.out.println("==============这是视频消息！");
        }
         
         
        if (map.get("MsgType").equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) { // 语音消息
            System.out.println("==============这是语音消息！");
        }*/
 
        return null;
    	
    }
}
