<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
    <title>修改密码</title>
    <link rel="stylesheet" href="${path}/static/layui/css/layui.css" media="all" />
    <script type="text/javascript" src="${path}/static/layui/layui.js"></script>
    <script type="text/javascript" src="${path}/static/js/jquery-3.2.1.min.js"></script>
    <style type="text/css">
        /*修改密码*/
        .changePwd{
            width:90%;
            margin:3% auto;
        }
        .layui-form-item .layui-input-inline{
            width:400px;
        }
        .layui-form-item{
            margin-top: 30px;
        }
    </style>
</head>
<body>
<form class="layui-form changePwd" id="changeForm">
    <div class="layui-form-item">
        <label class="layui-form-label">旧密码</label>
        <div class="layui-input-inline">
            <input type="password" name="oldPwd" placeholder="请输入旧密码" lay-verify="oldPwd" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-inline">
            <input type="password" name="newPwd" id="newPwd" placeholder="请输入新密码" lay-verify="newPwd" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">确认密码</label>
        <div class="layui-input-inline">
            <input type="password" name="rePwd" placeholder="请确认密码" lay-verify="repass" class="layui-input" >
        </div>
    </div>
    <div class="layui-form-item" style="margin-left: 60px; margin-top: 40px">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit lay-filter="editPwdForm">立即修改</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
        </div>
    </div>
</form>

<script>
    layui.use(['layer','form'],function () {
        var layer=layui.layer,
            form =layui.form ;
        form.verify({
            oldPwd: [/(.+){3,16}$/, '密码必须3到16位'],
            newPwd: [/(.+){3,16}$/, '密码必须3到16位'],
            repass: function(value){
                var rePassValue = $('#newPwd').val();
                if(value != rePassValue){
                    return '两次输入的密码不一致!';
                }
            }
        });
        form.on('submit(editPwdForm)', function(data){
            var url =  "${path }/user/editUserPwd" ;
            $.ajax({
                url:url,
                method:'post',
                data:data.field,
                dataType:'JSON',
                success:function(data){
                    if (data.success) {
                        layer.msg( data.msg ,{offset:['12%','22%'],icon: 6,anim: 6,time: 1000});
                        $("#changeForm")[0].reset();
                    }else{
                        layer.msg(data.msg ,{offset:['12%','22%'],icon: 5,anim: 6,time: 1000});
                        $("#changeForm")[0].reset();
                    }
                }
            });
            return false ;
        });
    });
</script>
</body>
</html>
