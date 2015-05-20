package com.chinalbs.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinalbs.beans.CommonAttributes;
import com.chinalbs.entity.User;
import com.chinalbs.entity.User.ModeType;
import com.chinalbs.framework.controller.BaseController;
import com.chinalbs.service.CaptchaService;
import com.chinalbs.service.RSAService;
import com.chinalbs.service.UserService;
import com.chinalbs.utils.ApiUtils;
import com.chinalbs.utils.JsonUtils;
import com.chinalbs.utils.SpringUtils;

/**
 * Controller - 共用
 * 
 */
@Controller("commonController")
@RequestMapping("/console/common")
public class CommonController extends BaseController {

    @Resource(name = "rsaServiceImpl")
    private RSAService rsaService;
	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name="userServiceImpl")
	private UserService userService;


	/**
	 * 验证码
	 */
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-")
				.append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".")
				.append("erot").append("se").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaService.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
	
	/**
	 * 主页
	 */
  @RequestMapping(value = "/main", method = RequestMethod.GET)
  public String main(ModelMap model, ModeType mode, HttpSession session) {
    try {
      User user = userService.getCurrent();
      model.addAttribute("user", user);
      String userId = (String) session.getAttribute(CommonAttributes.API_USERID_SESSION);
      if(userId==null){
          SpringUtils.loginApiResultAnalysis(ApiUtils.apiLogin(user), session);
      }
      if (user.getIsSystem() == 0 && user.getModeType() == null) {
        return "/console/setting/main";
      } else if (user.getIsSystem() == 0 && user.getModeType().equals(ModeType.DEVMODE)
          && user.getConfIsEnable().equals(0)) {
        return "/console/devmode/main";
      } else if (user.getIsSystem() == 0 && user.getModeType().equals(ModeType.BACKENDMODE)
          && user.getConfIsEnable().equals(0)) {
        return "/console/backendmode/main";
      } else {
        return "/console/common/main";
      }
    } catch (Exception e) {
     e.printStackTrace();
    }
    return "/console/common/main";

  }
	

	/**
	 * 错误提示
	 */
	@RequestMapping("/error")
	public String error() {
		return ERROR_VIEW;
	}

	/**
	 * 权限错误
	 */
	@RequestMapping("/unauthorized")
	public String unauthorized(HttpServletRequest request,
			HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null
				&& requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/console/common/unauthorized";
	}
	
	/**
     * 退出
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
      Subject subject = SecurityUtils.getSubject();
      if (subject.isAuthenticated()) {
        Cookie cookie = new Cookie("pre_url", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
      }
      return "redirect:/";
    }
	
    
    /**
     * 公钥
     */
    @RequestMapping(value = "/public_key", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, String> publicKey(HttpServletRequest request) {
        RSAPublicKey publicKey = rsaService.generateKey(request);
        Map<String, String> data = new HashMap<String, String>();
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        return data;
    }
    
    /**
     * 公钥（ChinaLbs获取公匙）
     */
    @RequestMapping(value = "/lbs_public_key", method = RequestMethod.GET)
    public @ResponseBody
      void lbspublicKey(HttpServletRequest request,HttpServletResponse response) {
      
        String callbackFunName = request.getParameter("callback");
        RSAPublicKey publicKey = rsaService.generateKey(request);
        Map<String, String> data = new HashMap<String, String>();
        data.put("modulus", Base64.encodeBase64String(publicKey.getModulus().toByteArray()));
        data.put("exponent", Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray()));
        try {
          response.getWriter().write(callbackFunName +"("+ JsonUtils.obj2Str(data)+")");
        } catch (IOException e) {
          e.printStackTrace();
        }
        response.setContentType("text/javascript");
    }


}
