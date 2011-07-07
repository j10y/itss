package com.htsoft.oa.action.system;

import com.htsoft.core.util.StringUtil;
import com.htsoft.core.web.action.BaseAction;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.model.system.SysConfig;
import com.htsoft.oa.service.system.AppUserService;
import com.htsoft.oa.service.system.SysConfigService;
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
	/* 37 */private String key = "RememberAppUser";

	@Resource
	private AppUserService userService;

	@Resource
	private SysConfigService sysConfigService;

	@Resource(name = "authenticationManager")
	/* 45 */private AuthenticationManager authenticationManager = null;

	public String login() {
		/* 54 */StringBuffer msg = new StringBuffer("{msg:'");

		/* 56 */SysConfig sysConfig = this.sysConfigService
				.findByKey("codeConfig");

		/* 58 */Captcha captcha = (Captcha) getSession().getAttribute(
				"simpleCaptcha");
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
		/* 102 */if (login.booleanValue()) {
			/* 103 */UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
					this.username, this.password);
			/* 104 */SecurityContext securityContext = SecurityContextHolder
					.getContext();
			/* 105 */securityContext
					.setAuthentication(this.authenticationManager
							.authenticate(authRequest));
			/* 106 */SecurityContextHolder.setContext(securityContext);
			/* 107 */getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME",
					this.username);
			/* 108 */String rememberMe = getRequest().getParameter(
					"_spring_security_remember_me");
			/* 109 */if ((rememberMe != null) && (rememberMe.equals("on"))) {
				/* 111 */long tokenValiditySeconds = 1209600L;
				/* 112 */long tokenExpiryTime = System.currentTimeMillis()
						+ tokenValiditySeconds * 1000L;

				/* 114 */String signatureValue = DigestUtils
						.md5Hex(this.username + ":" + tokenExpiryTime + ":"
								+ this.user.getPassword() + ":" + this.key);

				/* 116 */String tokenValue = this.username + ":"
						+ tokenExpiryTime + ":" + signatureValue;
				/* 117 */String tokenValueBase64 = new String(
						Base64.encodeBase64(tokenValue.getBytes()));
				/* 118 */getResponse().addCookie(
						makeValidCookie(tokenExpiryTime, tokenValueBase64));
			}

			/* 122 */setJsonString("{success:true}");
		} else {
			/* 124 */msg.append(",failure:true}");
			/* 125 */setJsonString(msg.toString());
		}
		/* 127 */return "success";
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
