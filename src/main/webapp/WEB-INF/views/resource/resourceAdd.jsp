<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<script type="text/javascript">
    $(function() {
        $('#pid').combotree({
            url : '${path }/resource/allTree',
            parentField : 'pid',
            lines : true,
            panelHeight : 'auto'
        });
        $('#resourceAddForm').form({
            url : '${path }/resource/add',
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
                    // parent.$.modalDialog.openner_treeGrid.treegrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_treeGrid这个对象，是因为resource.jsp页面预定义好了
                    // parent.layout_west_tree.tree('reload');
                    alert("添加成功");
                    parent.location.reload();
                }else{
                    layer.msg(result.msg)
                }
            }
        });
    });
</script>
<div style="padding: 3px;">
    <form id="resourceAddForm" method="post">
        <table class="grid">
            <tr>
                <td>资源名称</td>
                <td><input name="name" autocomplete="off" type="text" placeholder="请输入资源名称" class="easyui-validatebox span2" data-options="required:true" ></td>
                <td>资源类型</td>
                <td>
                    <select name="resourcetype" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                        <option value="0">菜单</option>
                        <option value="1">按钮</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>资源路径</td>
                <td><input name="url" autocomplete="off" type="text" placeholder="请输入资源路径" class="easyui-validatebox span2" data-options="width:140,height:29,required:true" ></td>
                <td>资源权限</td>
                <td><input name="permission" autocomplete="off" type="text" placeholder="请输入资源权限标识符" class="span2" data-options="width:140,height:29" ></td>
            </tr>
            <tr>
                <td>排序</td>
                <td><input name="seq" value="0"  class="easyui-numberspinner" style="width: 140px; height: 29px;" required="required" data-options="editable:false"></td>
                <td>菜单图标</td>
                <td><input name="icon" autocomplete="off" type="text" placeholder="请输入菜单图标" class="span2"/></td>
            </tr>
            <tr>
                <td>状态</td>
                <td ><select name="status" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                    <option value="0">正常</option>
                    <option value="1">停用</option>
                </select></td>
            </tr>
            <tr>
                <td>上级资源</td>
                <td colspan="3">
                    <select id="pid" name="pid" style="width: 200px; height: 29px;"></select>
                    <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pid').combotree('clear');" >清空</a>
                </td>
            </tr>
        </table>
    </form>
</div>
