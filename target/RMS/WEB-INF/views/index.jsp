<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta charset="utf-8">
    <title>BM后台管理系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <link rel="icon" href="${path}/static/images/BM32px.ico">
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css" media="all" />
    <link rel="stylesheet" href="${path}/static/css/iconfont1/iconfont.css" media="all">
    <link rel="stylesheet" href="${path}/static/css/index.css" media="all" />
    <style type="text/css">
        /*#box0 {*/
            /*overflow-x: hidden;*/
            /*overflow-y: scroll;*/
        /*}*/
        /*#box0::-webkit-scrollbar {*/
            /*display: none;*/
        /*}*/
    </style>
</head>
<body class="main_body" style="width:100%; height:100%" onload="showTime()">
<div class="layui-layout layui-layout-admin">
    <!-- 顶部 -->
    <div class="layui-header header">
        <div class="layui-main">
            <a href="${path}/index" class="logo"><img src="${path}/static/images/BM.png"></a>
            <!-- 显示/隐藏菜单 -->
            <a href="javascript:;" class="iconfont1 hideMenu icon-toggle"></a>
            <%-- 当前时间 --%>
            <div id="div_time"></div>
            <!-- 天气信息 -->
            <div class="weather" pc>
                <div id="tp-weather-widget"></div>
                <script>(function (T, h, i, n, k, P, a, g, e) {
                    g = function () {
                        P = h.createElement(i);
                        a = h.getElementsByTagName(i)[0];
                        P.src = k;
                        P.charset = "utf-8";
                        P.async = 1;
                        a.parentNode.insertBefore(P, a)
                    };
                    T["ThinkPageWeatherWidgetObject"] = n;
                    T[n] || (T[n] = function () {
                        (T[n].q = T[n].q || []).push(arguments)
                    });
                    T[n].l = +new Date();
                    if (T.attachEvent) {
                        T.attachEvent("onload", g)
                    } else {
                        T.addEventListener("load", g, false)
                    }
                }(window, document, "script", "tpwidget", "//widget.seniverse.com/widget/chameleon.js"))</script>
                <script>
                    tpwidget("init", {
                    "flavor": "slim",
                    "location": "WX4FBXXFKE4F",
                    "geolocation": "enabled",
                    "language": "zh-chs",
                    "unit": "c",
                    "theme": "chameleon",
                    "container": "tp-weather-widget",
                    "bubble": "disabled",
                    "alarmType": "badge",
                    "color": "#FFFFFF",
                    "uid": "U9EC08A15F",
                    "hash": "039da28f5581f4bcb5c799fb4cdfb673"
                });
                tpwidget("show");</script>
            </div>
            <!-- 顶部右侧菜单 -->
            <ul class="layui-nav top_menu layui-layout-right">
                <li class="layui-nav-item showNotice" id="showNotice" pc>
                    <a href="javascript:;"><i class="iconfont1 icon-xitonggonggao1"></i> <cite>系统公告</cite></a>
                </li>

                <li class="layui-nav-item" mobile>
                    <a href="javascript:;" class="mobileAddTab" data-url="${path }/user/editPwdPage"><i class="iconfont1 icon-xiugaimima1" data-icon="icon-xiugaimima1"></i><cite>修改密码</cite></a>
                </li>
                <li class="layui-nav-item" mobile>
                    <a href="${path}/logout" class="signOut"><i class="iconfont1 icon-tuichu1"></i> 退出登录</a>
                </li>
                <li class="layui-nav-item" pc>
                    <a href="javascript:;">
                        <i class="layui-icon">&#xe612;</i>
                        <shiro:principal property="name"></shiro:principal>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" data-url="${path}/user/personFile"><i class="iconfont1 icon-iconzhanghunor01" data-icon="icon-iconzhanghunor01"></i><cite> 个人资料</cite></a></dd>
                        <dd><a href="javascript:;" data-url="${path}/user/editPwdPage"><i class="iconfont1 icon-xiugaimima1" data-icon="icon-xiugaimima1"></i><cite> 修改密码</cite></a></dd>
                        <%--<dd><a  href="${path}/logout" class="signOut"><i class="iconfont1 icon-tuichu1"></i><cite> 退出登录</cite></a></dd>--%>
                    </dl>
                </li>
                <li class="layui-nav-item" pc>
                    <a  href="${path}/logout" class="signOut"><i class="iconfont1 icon-tuichu1"></i><cite> 退出登录</cite></a>
                </li>
            </ul>
        </div>
    </div>
    <!-- 左侧导航 -->
    <div class="layui-side layui-bg-black" id="box0">
        <div class="navBar"></div>
    </div>
    <!-- 右侧内容 -->
    <div class="layui-body layui-form">
        <div class="layui-tab marg0" lay-filter="bodyTab" id="top_tabs_box">
            <ul class="layui-tab-title top_tab" id="top_tabs">
                <li class="layui-this" lay-id=""><i class="iconfont1 icon-tianchongxing-"></i><cite>&nbsp;后台首页</cite></li>
            </ul>
            <!-- 当前页面操作 -->
            <ul class="layui-nav closeBox">
                <li class="layui-nav-item" pc>
                    <a href="javascript:;"><i class="iconfont1 icon-caozuo"></i> 页面操作</a>
                    <dl class="layui-nav-child">
                        <dd><a href="javascript:;" class="refresh refreshThis"><i class="layui-icon">&#x1002;</i> 刷新当前</a></dd>
                        <dd><a href="javascript:;" class="closePageOther"><i class="iconfont1 icon-jujue"></i> 关闭其他</a></dd>
                        <dd><a href="javascript:;" class="closePageAll"><i class="iconfont1 icon-guanbi"></i> 关闭全部</a></dd>
                    </dl>
                </li>
            </ul>
            <div class="layui-tab-content clildFrame">
                <div class="layui-tab-item layui-show">
                    <iframe src="${path}/main"></iframe>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 移动导航 -->
<div class="site-tree-mobile layui-hide"><i class="layui-icon">&#xe602;</i></div>
<div class="site-mobile-shade"></div>

<script type="text/javascript" src="${path}/static/layui/layui.js"></script>
<script type="text/javascript" src="${path}/static/js/index.js"></script>
<script type="text/javascript" src="${path}/static/js/leftNav.js"></script>
</body>
</html>
