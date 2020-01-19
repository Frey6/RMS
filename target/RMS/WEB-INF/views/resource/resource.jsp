<%--
  Created by IntelliJ IDEA.
  User: yongfeng.L
  Date: 2018/12/8
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <title>资源管理</title>
    <script type="text/javascript">
        var treeGrid;
        $(function() {
            treeGrid = $('#treeGrid').treegrid({
                url : '${path }/resource/treeGrid',
                idField : 'id',
                treeField : 'name',
                parentField : 'pid',
                rownumbers : true,
                fit : true,  // 是否自适应父容器的大小
                fitColumns : false,
                border : false,
                animate : true ,
                frozenColumns : [ [ {
                    title : '编号',
                    field : 'id',
                    width : 40,
                    align : 'center'
                } ] ],
                columns : [ [ {
                    field : 'name',
                    title : '资源名称',
                    width : 150
                }, {
                    field : 'url',
                    title : '资源路径',
                    width : 200,
                    align : 'center'
                },{
                    field : 'permission',
                    title : '资源权限',
                    width : 150,
                    align : 'center'
                }, {
                    field : 'seq',
                    title : '排序',
                    width : 40,
                    align : 'center'
                }, {
                    field : 'iconCls',
                    title : '图标',
                    width : 150,
                    align : 'center'
                }, {
                    field : 'resourcetype',
                    title : '资源类型',
                    width : 80,
                    align : 'center',
                    // 单元格的格式化函数
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 0:
                                return '菜单';
                            case 1:
                                return '按钮';
                        }
                    }
                }, {
                    field : 'pid',
                    title : '上级资源ID',
                    width : 150,
                    hidden : true,
                    align : 'center'
                }, {
                    field : 'status',
                    title : '状态',
                    width : 50,
                    align : 'center',
                    formatter : function(value, row, index) {
                        switch (value) {
                            case 0:
                                return '正常';
                            case 1:
                                return '停用';
                        }
                    }
                }, {
                    field : 'action',
                    title : '操作',
                    width : 130,
                    align : 'center',
                    formatter : function(value, row, index) {
                        var str = '';
                        <shiro:hasPermission name="resource:edit">
                        str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-edit" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="resource:delete">
                        str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                        str += $.formatString('<a href="javascript:void(0)" class="resource-easyui-linkbutton-del" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                        return str;
                    }
                } ] ],
                onLoadSuccess:function(data){
                    $('.resource-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                    $('.resource-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
                    $(this).treegrid("fixRowHeight");
                },
                toolbar : '#toolbar'
            });
        });
        function editFun(id) {
            if (id != undefined) {
                // 获取选择的id
                treeGrid.treegrid('select', id);
            }
            // 得到选中行的参数值
            var node = treeGrid.treegrid('getSelected');
            if (node) {
                parent.$.modalDialog({
                    title : '编辑',
                    width : 500,
                    height : 350,
                    href : '${path }/resource/editPage?id=' + node.id,
                    buttons : [ {
                        text : '确定',
                        handler : function() {
                            parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                            var f = parent.$.modalDialog.handler.find('#resourceEditForm');
                            f.submit();
                        }
                    } ]
                });
            }
        }
        function deleteFun(id) {
            if (id != undefined) {
                treeGrid.treegrid('select', id);
            }
            var node = treeGrid.treegrid('getSelected');
            if (node) {
                parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
                    if (b) {
                        progressLoad();
                        // Jquery 简单的 Post 请求函数
                        $.post('${pageContext.request.contextPath}/resource/delete', {
                            id : node.id
                        }, function(result) {
                            progressClose();
                            if (result.success) {
                                // parent.$.messager.alert('提示', result.msg, 'info');
                                // treeGrid.treegrid('reload');
                                alert("删除成功");
                                parent.location.reload();
                            }else{
                                layer.msg(result.msg);
                            }

                        }, 'JSON');
                    }
                });
            }
        }
        function addFun() {
            parent.$.modalDialog({
                title : '添加',
                width : 500,
                height : 350,
                href : '${path }/resource/addPage',
                buttons : [ {
                    text : '添加',
                    handler : function() {
                        parent.$.modalDialog.openner_treeGrid = treeGrid; //因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                        var f = parent.$.modalDialog.handler.find('#resourceAddForm');
                        f.submit();
                    }
                } ]
            });
        }
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false"  style="overflow: hidden;">
        <table id="treeGrid"></table>
    </div>
</div>

<div id="toolbar" style="display: none;">
    <shiro:hasPermission name="resource:add">
        <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
    </shiro:hasPermission>
</div>

</body>
</html>
