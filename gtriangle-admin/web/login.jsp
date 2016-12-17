<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <%@ page import="org.apache.shiro.SecurityUtils" %>
            <%@ page import="org.apache.shiro.subject.Subject" %>
                <%@ page import="com.gtriangle.admin.util.RequestUtils" %>
                    <!DOCTYPE html>
                    <html>

                    <head>
                        <%@ include file="common.jsp" %>


                            <meta charset="utf-8" />
                            <title>ZANK管理后台</title>
                            <meta http-equiv="X-UA-Compatible" content="IE=edge">
                            <meta content="width=device-width, initial-scale=1" name="viewport" />

                            <meta content="Preview page of Metronic Admin Theme #2 for " name="description" />
                            <meta content="" name="author" />

                            <link href="<%=fc_cdn_static%>img/bitbug_favicon.ico<%=CDN_VERSION%>" rel="Shortcut Icon">
                            <link href="<%=fc_cdn_lib%>css/login-2.min.css<%=CDN_VERSION%>" rel="stylesheet" type="text/css" />
                            <link href="<%=fc_cdn_lib%>css/zanke.admin.min.css<%=CDN_VERSION%>" rel="stylesheet" type="text/css" />
                            <link href="<%=fc_cdn_static%>css/custom.css<%=CDN_VERSION%>" rel="stylesheet" type="text/css" />
                    </head>

                    <body class="login">
                        <input id="inputErrorMsg" value="${shiroLoginFailure}" type="hidden">
                        <%
                                Subject subject = SecurityUtils.getSubject();
                                if(subject.isAuthenticated()) {
                                    response.sendRedirect("mgt/v_index");
                                }
                            %>
                            <div class="logo">
                                <a href="javascript:;;"><img src="<%=fc_cdn_static%>img/logo.png<%=CDN_VERSION%>" style="height: 30px;" alt="" /> </a>
                            </div>
                            <div class="content">
                                <form class="login-form" action="" method="post" id="loginForm">
                                    <input type="hidden" name="appId" value="zank_mgt" id="shopLoginAppId" />
                                    <div class="form-title">
                                        <span class="form-title">欢迎！</span>
                                        <span class="form-subtitle">请登录系统</span>
                                    </div>
                                    <div class="alert alert-danger display-hide">
                                        <button class="close" data-close="alert"></button>
                                        <span> 请输入你的账号和密码 </span>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label visible-ie8 visible-ie9">手机号</label>
                                        <input class="form-control form-control-solid placeholder-no-fix" type="text" autocomplete="off" name="username" maxlength="11" placeholder="请输入手机号" id="inputUserName" />
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label visible-ie8 visible-ie9">密码</label>
                                        <input class="form-control form-control-solid placeholder-no-fix" type="password" autocomplete="off" name="password" placeholder="请输入密码" id="inputPassword" />
                                    </div>
                                    <c:if test="${jcaptchaEnabled}">
                                        <div class="form-group">
                                            <input type="text" class="form-control input-inline input-medium" maxlength="10" autocomplete="off" placeholder="请输入图片内容" name="jcaptchaCode" id="inputJcaptchaCode">
                                            <span class="code-img">
                                                 <img style="width:96px;cursor: pointer;" id="inputJcaptchaCodeImg" data-src="<%=fc_account_domain%>captcha.svl" src="<%=fc_account_domain%>captcha.svl?t=<%=Math.random()%>" >
                                                 <span class="pwd-tool-btn" id="spanChangeJcaptchaCode" style="cursor: pointer;"> 
                                                    <i class="fa fa-refresh cl-grey" title="点击更换验证码"></i>
                                                 </span>
                                            </span>
                                        </div>
                                    </c:if>
                                    <div class="form-actions">
                                        <button type="button" id="btnLoginAction" class="btn red btn-block uppercase">登录</button>
                                    </div>
                                </form>
                            </div>
                            <div class="copyright hide"> 2016 © ZANK</div>

                            <script src="<%=fc_cdn_lib%>js/zanke.admin.min.js<%=CDN_VERSION%>" type="text/javascript"></script>
                            <script src="<%=fc_cdn_static%>js/login.js<%=CDN_VERSION%>" type="text/javascript"></script>
                            <%@ include file="act.jsp" %>
                    </body>

                    </html>