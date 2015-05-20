package com.chinalbs.beans;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.chinalbs.beans.Setting.CaptchaType;
import com.chinalbs.entity.User;
import com.chinalbs.service.UserService;
import com.chinalbs.service.CaptchaService;
import com.chinalbs.utils.MD5Util;

/**
 * 权限认证
 * 
 */
public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;
	@Resource(name = "userServiceImpl")
	private UserService userService;

	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		Boolean isAutoLogin = authenticationToken.getIsAutoLogin();
		if(!isAutoLogin){
  		  if (!captchaService.isValid(CaptchaType.userLogin, captchaId, captcha)) {
              throw new UnsupportedTokenException();
          }
		}
		if (username != null && password != null) {
			User user = userService.findByUserName(username);
			if (user == null) {
				throw new UnknownAccountException();
			}
			if (!MD5Util.getMd5(password).equalsIgnoreCase(user.getPassword())) {
			//if (!DigestUtils.md5Hex(password).equals(user.getPassword())) {
				throw new IncorrectCredentialsException();
			}
			user.setLoginIp(ip);
			user.setLoginDate(new Date());
			userService.update(user);
			return new SimpleAuthenticationInfo(new Principal(user.getId(), username), password, getName());
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		if (principal != null) {
			List<String> authorities = userService.findAuthorities(principal.getId());
			if (authorities != null) {
				SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
				authorizationInfo.addStringPermissions(authorities);
				return authorizationInfo;
			}
		}
		return null;
	}

}