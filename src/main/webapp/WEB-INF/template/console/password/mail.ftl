<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>${message("admin.find.password")}</title>
<link rel="shortcut icon" type="image/x-icon" href="${base}/resources/images/favicon.ico" media="screen" /> 
</head>
<body>
	<p>${username}:</p>
	<p>${message("admin.password.welcome")}</p>
	<p>${message("admin.password.content",siteName)}(${message("admin.password.expire",pwdTokenExpiryTime)})</p>
	<p>
		<a href="${siteUrl}/console/password/resetPwdPage.jhtml?userName=${username}&pwdToken=${pwdToken}" target="_blank">${siteUrl}/console/password/resetPwdPage.jhtml?userName=${username}&pwdToken=${pwdToken}</a>
	</p>
	<p>${siteName}</p>
	<p>${.now?string("yyyy-MM-dd")}</p>
</body>
</html>