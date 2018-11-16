package com.yanglu.start;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
 
/**
 * 
 * ClassName: InterfaceUrlIntiServlet
 * 
 * @Description: 项目文件初始化
 * 
 */
public class InterfaceUrlIntiServlet extends HttpServlet {
 
    private static final long serialVersionUID = 1L;
 
    @Override
    public void init(ServletConfig config) throws ServletException {
        InterfaceUrlInti.init();
    }
 
}
