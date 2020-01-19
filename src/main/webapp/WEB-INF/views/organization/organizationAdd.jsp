<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">

    $(function() {
        
        $('#pid').combotree({
            url : '${path }/organization/tree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        
        $('#organizationAddForm').form({
            url : '${path }/organization/add',
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
                    parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为organization.jsp页面预定义好了
                    layer.msg(result.msg);
                }else{
                    layer.msg(result.msg);
                }
            }
        });
        
    });
</script>
<div style="padding: 3px;">
    <form id="organizationAddForm" method="post">
        <table class="grid">
            <tr>
                <td>编号</td>
                <td><input name="code" autocomplete="off" type="text" placeholder="请输入部门编号" class="easyui-validatebox span2" data-options="required:true" ></td>
                <td>部门名称</td>
                <td><input name="name" autocomplete="off" type="text" placeholder="请输入部门名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false" value="0"></td>
                <td>菜单图标</td>
                <td><input  name="icon" autocomplete="off" class="span2" type="text" placeholder="请输入菜单图标" value="define-depart"/></td>
            </tr>
            <tr>
                <td>地址</td>
                <td colspan="3"><input name="address" autocomplete="off" placeholder="请输入地址" type="text" class="span2" style="width: 300px;"/></td>
            </tr>
            <tr>
                <td>上级部门</td>
                <td colspan="3"><select id="pid" name="pid" style="width:200px;height: 29px;"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a></td>
                
            </tr>
        </table>
    </form>
</div>