<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <title>个人资料</title>
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${path}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${path}/static/js/jquery-3.2.1.min.js"></script>
    <style>
        .layui-form-item .layui-input-inline{
            width:500px;
        }
    </style>
</head>
<body style="padding-top: 40px;">
<form class="layui-form" id="personFile">
    <input type="hidden" name="id" value="${user.id}">
    <div class="layui-form-item">
        <label class="layui-form-label">登录名：</label>
        <div class="layui-input-inline">
            <input id="loginName" onclick="logN()" class="layui-input" value="${user.loginname}" readonly="readonly" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">姓名：</label>
        <div class="layui-input-inline">
            <input name="name" required  lay-verify="required" value="${user.name}" autocomplete="off" placeholder="请输入名字" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">性别：</label>
        <div class="layui-input-inline">
            <select name="sex" id="sex">
                <option value="0">男</option>
                <option value="1">女</option>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">年龄：</label>
        <div class="layui-input-inline">
            <input name="age" required  lay-verify="required|number" value="${user.age}" autocomplete="off" placeholder="请输入年龄" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">电话：</label>
        <div class="layui-input-inline">
            <input name="phone" required  lay-verify="required" value="${user.phone}" autocomplete="off" placeholder="请输入电话" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户类型：</label>
        <div class="layui-input-inline">
            <input id="userType" onclick="useT()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">部门：</label>
        <div class="layui-input-inline">
            <input id="organization" onclick="organ()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">角色：</label>
        <div class="layui-input-inline">
            <input id="role" onclick="rol()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">用户状态：</label>
        <div class="layui-input-inline">
            <input id="status" onclick="stat()" readonly="readonly" class="layui-input" type="text">
        </div>
    </div>
    <div class="layui-form-item" id="dButton" style="margin-left: 160px;margin-top: 20px">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="personFileEdit">保存</button>
        </div>
    </div>
</form>
</body>

<script>
    var  path = "${path}" ;
    var sex = ${user.sex} ;
    var organizationName = "${user.organizationName}";
    // Request 设置的值可以直接取
    var roleNames = "${roleNames}";
    var usersStatus = ${user.status};
    var type = ${user.usertype};
</script>
<script type="text/javascript" src="${path}/static/js/personalFile.js"></script>

</html>
