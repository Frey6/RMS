<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#roleEditForm').form({
            url : '${path }/role/edit',
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
        $("#description").val('${role.description}');
        $("#status").val('${role.status}');
    });
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form id="roleEditForm" method="post">
            <table class="grid">
                <tr>
                    <td>角色名称</td>
                    <td><input name="id" type="hidden"  value="${role.id}">
                    <input name="name" autocomplete="off" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value="${role.name}"></td>
                </tr>
                <tr>
                    <td>排序</td>
                    <td><input name="seq" class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="${role.seq}"></td>
                </tr>
                <tr>
                    <td>状态</td>
	                <td >
		                <select id="status" name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                            <option value="0">正常</option>
                            <option value="1">停用</option>
		                </select>
	                </td>
                </tr>
                <tr>
                    <td>描述</td>
                    <td colspan="3"><textarea id="description" name="description" style="padding-left: 4px" rows="" cols="" ></textarea></td>
                </tr>
            </table>
        </form>
    </div>
</div>