<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {

        $('#organizationId').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });

        $('#roleIds').combotree({
            url: '${path }/role/tree',
            multiple: true,
            required: true,
            panelHeight : 'auto'
        });

        $('#userAddForm').form({
            url : '${path }/user/add',
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
        
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="userAddForm" method="post">
            <table class="grid">
                <tr>
                    <td>登录名</td>
                    <td><input name="loginname" autocomplete="off" type="text" placeholder="请输入登录名" class="easyui-validatebox span2" data-options="required:true" ></td>
                    <td>姓名</td>
                    <td><input name="name" autocomplete="off" type="text" placeholder="请输入姓名" class="easyui-validatebox span2" data-options="required:true"></td>
                </tr>
                <tr>
                    <td>密码</td>
                    <td><input name="password" autocomplete="off" type="password" placeholder="请输入密码" class="easyui-validatebox span2" data-options="required:true"></td>
                    <td>性别</td>
                    <td>
                        <select name="sex" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0" >男</option>
                            <option value="1" >女</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>年龄</td>
                    <td><input name="age" autocomplete="off" placeholder="请输入年龄" type="text" class="easyui-numberbox span2"/></td>
                    <td>用户类型</td>
                    <td>
                        <select name="usertype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">管理员</option>
                            <option value="1" selected="selected">用户</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>部门</td>
                    <td><select id="organizationId" name="organizationId" style="width: 140px; height: 29px;" class="easyui-validatebox span2" data-options="required:true"></select></td>
                    <td>角色</td>
                    <td><select id="roleIds" name="roleIds" style="width: 140px; height: 29px; " data-options="required:true"></select></td>
                </tr>
                <tr>
                    <td>电话</td>
                    <td>
                        <input  name="phone" autocomplete="off" type="text" placeholder="请输入电话" class="easyui-numberbox span2"/>
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