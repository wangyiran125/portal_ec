package com.chinalbs.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import com.chinalbs.beans.CommonAttributes;

/**
 * Utils - Spring
 * 
 */
@Component("springUtils")
@Lazy(false)
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

	/** applicationContext */
	private static ApplicationContext applicationContext;

	/**
	 * 不可实例化
	 */
	private SpringUtils() {
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtils.applicationContext = applicationContext;
	}

	public void destroy() throws Exception {
		applicationContext = null;
	}

	/**
	 * 获取applicationContext
	 * 
	 * @return applicationContext
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 获取实例
	 * 
	 * @param name
	 *            Bean名称
	 * @return 实例
	 */
	public static Object getBean(String name) {
		Assert.hasText(name);
		return applicationContext.getBean(name);
	}

	/**
	 * 获取实例
	 * 
	 * @param name
	 *            Bean名称
	 * @param type
	 *            Bean类型
	 * @return 实例
	 */
	public static <T> T getBean(String name, Class<T> type) {
		Assert.hasText(name);
		Assert.notNull(type);
		return applicationContext.getBean(name, type);
	}

	/**
	 * 获取国际化消息
	 * 
	 * @param code
	 *            代码
	 * @param args
	 *            参数
	 * @return 国际化消息
	 */
	public static String getMessage(String code, Object... args) {
		LocaleResolver localeResolver = getBean("localeResolver", LocaleResolver.class);
		Locale locale = localeResolver.resolveLocale(null);
		return applicationContext.getMessage(code, args, locale);
	}
	
	public static String getMd5(String pwd) {
	        if (pwd == null) {
	            return null;
	        }
	        try {
	            // Create MessageDigest object for MD5
	            MessageDigest digest = MessageDigest.getInstance("MD5");
	            // Update input pwd in pwd digest
	            digest.update(pwd.toUpperCase().getBytes());
	            // Converts pwd digest value in base 16 (hex)
	            byte[] byteArray = digest.digest();
	            StringBuffer md5StrBuff = new StringBuffer();
	            for (int i = 0; i < byteArray.length; i++) {
	                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
	                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
	                else
	                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
	            }
	            return md5StrBuff.toString().toUpperCase();
	        } catch (NoSuchAlgorithmException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	
	/**
	 * 解析从调用登陆api后返回的结果,并将其中的token和user_id存放到session中
	 * @param arg
	 * @return
	 */
	public static void loginApiResultAnalysis(String arg,HttpSession session){
	  String[] strings = arg.split(",");
      if(strings.length>1){
        for (String string : strings) {
          if (string.contains(CommonAttributes.API_TOKEN)) {
            if(string.length() >0){
              String tempString = string.split(":")[1];
              if (!StringUtils.isEmpty(tempString)) {
                Assert.notNull(session);
                session.setAttribute(CommonAttributes.API_TOKEN_SESSION, tempString.substring(1, tempString.length()-1));
              }
            }
          }
          if (string.contains(CommonAttributes.API_USERID)) {
            if(string.length() >0){
              String tempString = string.split(":")[1];
              if (!StringUtils.isEmpty(tempString)) {
                Assert.notNull(session);
                session.setAttribute(CommonAttributes.API_USERID_SESSION, tempString);
              }
            }
          }
        }
     
      }
	}
	
    public static boolean downLoadFile(String filePath,
        HttpServletResponse response, String fileName, String fileType)
        throws Exception {
        File file = new File(filePath);  //根据文件路径获得File文件
        //设置文件类型(这样设置就不止是下Excel文件了，一举多得)
        if("pdf".equals(fileType)){
           response.setContentType("application/pdf;charset=GBK");
        }else if("xls".equals(fileType)){
           response.setContentType("application/msexcel;charset=GBK");
        }else if("doc".equals(fileType)){
           response.setContentType("application/msword;charset=GBK");
        }
        //文件名
        response.setHeader("Content-Disposition", "attachment;filename=\""
            + new String(fileName.getBytes(), "ISO8859-1") + "\"");
        response.setContentLength((int) file.length());
        byte[] buffer = new byte[4096];// 缓冲区
        BufferedOutputStream output = null;
        BufferedInputStream input = null;
        try {
          output = new BufferedOutputStream(response.getOutputStream());
          input = new BufferedInputStream(new FileInputStream(file));
          int n = -1;
          //遍历，开始下载
          while ((n = input.read(buffer, 0, 4096)) > -1) {
             output.write(buffer, 0, n);
          }
          output.flush();   //不可少
          response.flushBuffer();//不可少
        } catch (Exception e) {
          //异常自己捕捉       
        } finally {
           //关闭流，不可少
           if (input != null)
                input.close();
           if (output != null)
                output.close();
        }
       return false;
    }

}