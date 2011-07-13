package com.xpsoft.oa.action.system;

import com.xpsoft.core.util.StringUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.SysConfig;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.SysConfigService;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

public class LoginAction extends BaseAction {
	private AppUser user;
	private String username;
	private String password;
	private String checkCode;
	private String key = "RememberAppUser";

	@Resource
	private AppUserService userService;

	@Resource
	private SysConfigService sysConfigService;

	@Resource(name = "authenticationManager")
	private AuthenticationManager authenticationManager = null;

	public String login() {
		StringBuffer msg = new StringBuffer("{msg:'");

		SysConfig sysConfig = this.sysConfigService.findByKey("codeConfig");

		Captcha captcha = (Captcha) getSession().getAttribute("simpleCaptcha");
		/* 59 */Boolean login = Boolean.valueOf(false);

		/* 61 */String newPassword = null;

		/* 63 */if ((!"".equals(this.username)) && (this.username != null)) {
			/* 64 */setUser(this.userService.findByUserName(this.username));

			/* 66 */if (this.user != null) {
				/* 68 */if (org.apache.commons.lang.StringUtils
						.isNotEmpty(this.password)) {
					/* 70 */newPassword = StringUtil
							.encryptSha256(this.password);

					/* 72 */if (this.user.getPassword().equalsIgnoreCase(
							newPassword)) {
						/* 74 */if (sysConfig.getDataValue().equals(
								SysConfig.CODE_OPEN)) {
							/* 75 */if (captcha == null) {
								/* 76 */msg.append("请刷新验证码再登录.'");
							}
							/* 79 */else if (captcha.isCorrect(this.checkCode)) {
								/* 81 */if (this.user.getStatus().shortValue() == 1)
									/* 82 */login = Boolean.valueOf(true);
								else
									/* 84 */msg.append("此用户已被禁用.'");
							}
							/* 86 */else
								msg.append("验证码不正确.'");

						}
						/* 90 */else if (this.user.getStatus().shortValue() == 1)
							/* 91 */login = Boolean.valueOf(true);
						else
							/* 93 */msg.append("此用户已被禁用.'");
					} else
						/* 96 */msg.append("密码不正确.'");
				} else {
					/* 98 */msg.append("密码不能为空.'");
				}
			} else
				msg.append("用户不存在.'");
		}
		if (login.booleanValue()) {
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					this.username, this.password);
			SecurityContext securityContext = SecurityContextHolder.getContext();
			securityContext.setAuthentication(this.authenticationManager.authenticate(authRequest));
			SecurityContextHolder.setContext(securityContext);
			getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME",
					this.username);
			String rememberMe = getRequest().getParameter(
					"_spring_security_remember_me");
			if ((rememberMe != null) && (rememberMe.equals("on"))) {
				long tokenValiditySeconds = 1209600L;
				long tokenExpiryTime = System.currentTimeMillis()
						+ tokenValiditySeconds * 1000L;

				String signatureValue = DigestUtils
						.md5Hex(this.username + ":" + tokenExpiryTime + ":"
								+ this.user.getPassword() + ":" + this.key);

				String tokenValue = this.username + ":"
						+ tokenExpiryTime + ":" + signatureValue;
				String tokenValueBase64 = new String(
						Base64.encodeBase64(tokenValue.getBytes()));
				getResponse().addCookie(
						makeValidCookie(tokenExpiryTime, tokenValueBase64));
			}

			setJsonString("{success:true}");
		} else {
			msg.append(",failure:true}");
			setJsonString(msg.toString());
		}
		return "success";
	}

	protected Cookie makeValidCookie(long expiryTime, String tokenValueBase64) {
		/* 131 */HttpServletRequest request = getRequest();
		/* 132 */Cookie cookie = new Cookie(
				"SPRING_SECURITY_REMEMBER_ME_COOKIE", tokenValueBase64);
		/* 133 */cookie.setMaxAge(157680000);
		/* 134 */cookie.setPath(org.springframework.util.StringUtils
				.hasLength(request.getContextPath()) ? request.getContextPath()
				: "/");
		/* 135 */return cookie;
	}

	public AppUser getUser() {
		/* 139 */return this.user;
	}

	public void setUser(AppUser user) {
		/* 143 */this.user = user;
	}

	public String getUsername() {
		/* 147 */return this.username;
	}

	public void setUsername(String username) {
		/* 151 */this.username = username;
	}

	public String getPassword() {
		/* 155 */return this.password;
	}

	public void setPassword(String password) {
		/* 159 */this.password = password;
	}

	public String getCheckCode() {
		/* 163 */return this.checkCode;
	}

	public void setCheckCode(String checkCode) {
		/* 167 */this.checkCode = checkCode;
	}
}
