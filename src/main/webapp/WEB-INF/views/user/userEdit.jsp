<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        var roleIds = ${roleIds};
        $('#organizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            value : '${user.organizationId}'
        });

        $('#roleIds').combotree({
            url : '${path }/role/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto',
            multiple : true,
            required : true,
            cascadeCheck : false,
            value : roleIds
        });

        $('#userEditForm').form({
            url : '${path }/user/edit',
            onSubmit : function() {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success : function(result) {
                result = $.parseJSON(result);
                progressClose();
                parent.$.modalDialog.handler.dialog('close');
                if (result.success) {
                    parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
                    layer.msg(result.msg);
                } else {
                    layer.msg(result.msg);
                }
            }
        });
        $("#sex").val('${user.sex}');
        $("#usertype").val('${user.usertype}');
        $("#status").val('${user.status}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userEditForm" method="post">
            <div class="light-info" style="overflow: hidden;padding: 3px;">
                <div>密码不修改请留空。</div>
            </div>
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="id" type="hidden"  value="${user.id}">
                    <input name="loginname" autocomplete="off" type="text" placeholder="请输入登录名" class="easyui-validatebox span2" data-options="required:true" value="${user.loginname}"></td>
                    <td>姓名</td>
                    <td><input name="name" autocomplete="off" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true" value="${user.name}"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input type="text" autocomplete="off" name="password" class="span2"/></td>
                    <td>性别</td>
                    <td>
                        <select name="sex" id="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">男</option>
                            <option value="1">女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>年龄</td>
                    <td><input type="text" autocomplete="off" name="age" value="${user.age}" class="easyui-numberbox span2"/></td>
                    <td>用户类型</td>
                    <td>
                        <select name="usertype" id="usertype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">管理员</option>
                            <option value="1">用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td>
                        <select name="organizationId" id="organizationId" autocomplete="off" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select>
                    </td>
                    <td>角色</td>
                    <td><input name="roleIds" id="roleIds" autocomplete="off" style="width: 140px; height: 29px;"/></td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input name="phone" autocomplete="off" type="text" class="easyui-numberbox span2" value="${user.phone}"/>
                    </td>
                    <td>用户状态</td>
                    <td>
                        <select id="status" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">正常</option>
                            <option value="1">停用</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>