package com.yanglu.start;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;

import com.yanglu.util.GlobalConstants;

/**
 * ClassName: InterfaceUrlIntis
 * @Description: 项目启动初始化方法
 */
public class InterfaceUrlInti {

	//@Value("{#wechat.properties}")
	public synchronized static void init(){
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		if(GlobalConstants.interfaceUrlProperties==null){
			GlobalConstants.interfaceUrlProperties = new Properties();
		}
		InputStream in = null;
		try {
			in = cl.getResourceAsStream("/resource/interface_url.properties");
			props.load(in);
			for(Object key : props.keySet()){
				GlobalConstants.interfaceUrlProperties.put(key, props.get(key));
			}
			
			props = new Properties();
			in = cl.getResourceAsStream("/resource/wechat.properties");
			props.load(in);
			for(Object key : props.keySet()){
				GlobalConstants.interfaceUrlProperties.put(key, props.get(key));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return;
	}

}
