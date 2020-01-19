<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>角色管理</title>
    <script type="text/javascript">
    var dataGrid;
    $(function() {
        dataGrid = $('#dataGrid').datagrid({
            url : '${path }' + '/role/dataGrid',
            //设置为 true , 则把行条纹化。（即奇偶行使用不同背景色）
            // striped : true,
            // 设置为true , 则显示带有行号的列
            rownumbers : true,
            // 是否启用分页
            pagination : true,
            // 是否设置单个选中
            singleSelect : true,
            // 指定哪个字段是表示字段
            idField : 'id',
            // 定义可以排序的列
            sortName : 'id',
            // 定义列的排序顺序
            sortOrder : 'asc',
            // 当设置了 pagination 属性时, 初始化页面尺寸
            pageSize : 10,
            // 当设置了 pagination 属性时,初始化页面尺寸的选择列表
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            // frozenColumns 所指向的列会被冻结在左边
            columns : [ [ {
                width : '100',
                title : 'id',
                field : 'id',
                sortable : true,
                align : 'center'
            }, {
                width : '80',
                title : '名称',
                field : 'name',
                sortable : true,
                align : 'center'
            } , {
                width : '80',
                title : '排序号',
                field : 'seq',
                sortable : true,
                align : 'center'
            }, {
                width : '200',
                title : '描述',
                field : 'description',
                align : 'center'
            } , {
                width : '60',
                title : '状态',
                field : 'status',
                sortable : true,
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
                align : 'center',
                width : 200,
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="role:grant">
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-ok" onclick="grantFun(\'{0}\');" >授权</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:edit">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-edit" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="role:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="role-easyui-linkbutton-del" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            } ] ],
            onLoadSuccess:function(data){
                $('.role-easyui-linkbutton-ok').linkbutton({text:'授权',plain:true,iconCls:'define-true'});
                $('.role-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                $('.role-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
                $(this).datagrid("fixRowHeight");
            },
            toolbar : '#toolbar'
        });
    });

    function addFun() {
        parent.$.modalDialog({
            title : '添加',
            width : 500,
            height : 300,
            href : '${path }/role/addPage',
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleAddForm');
                    f.submit();
                }
            } ]
        });
    }

    function editFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '编辑',
            width : 500,
            height : 300,
            href : '${path }/role/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleEditForm');
                    f.submit();
                }
            } ]
        });
    }

    function deleteFun(id) {
        if (id == undefined) { //点击右键菜单才会触发这个
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else { //点击操作里面的删除图标会触发这个
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path}/role/delete', {
                    id : id
                }, function(result) {
                    progressClose();
                    if (result.success) {
                        dataGrid.datagrid('reload');
                        layer.msg(result.msg);
                    }else{
                        layer.msg(result.msg);
                    }
                }, 'JSON');
            }
        });
    }
    function grantFun(id) {
        if (id == undefined) {
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '授权',
            width : 500,
            height : 500,
            href : '${path }/role/grantPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#roleGrantForm');
                    f.submit();
                }
            } ]
        });
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',fit:true,border:false">
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="role:add">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add2'">添加</a>
        </shiro:hasPermission>
    </div>
</body>
</html>