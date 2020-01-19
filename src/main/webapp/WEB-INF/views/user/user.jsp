<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/commons/basejs.jsp" %>
<meta http-equiv="X-UA-Compatible" content="edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
    <script type="text/javascript">

    var dataGrid;
    var organizationTree;

    $(function() {
        organizationTree = $('#organizationTree').tree({
            url : '${path}/organization/tree',
            parentField : 'pid',
            lines : true,
            onClick : function(node) {
                var childrenNodes = $('#organizationTree').tree('getChildren', node.target);
                // Es6 新增的属性 判断是否为空
                var nodeId = node.id ;
                if (Object.keys(childrenNodes).length != 0){
                    for (var childrenNode in childrenNodes){
                        nodeId += ","+childrenNodes[childrenNode].id
                    }
                }
                dataGrid.datagrid('load', {
                    organizationId: nodeId
                });
            }
        });

        dataGrid = $('#dataGrid').datagrid({
            url : '${path }/user/dataGrid',
            fit : true,
            // striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            idField : 'id',
            sortName : 'id',
            sortOrder : 'asc',
            pageSize : 10,
            pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
            columns : [ [ {
                width : '80',
                title : '登录名',
                field : 'loginname',
                sortable : true,
                align : 'center'
            }, {
                width : '80',
                title : '姓名',
                field : 'name',
                sortable : true,
                align : 'center'
            },{
                width : '80',
                title : '部门ID',
                field : 'organizationId',
                hidden : true,
                align : 'center'
            },{
                width : '80',
                title : '所属部门',
                field : 'organizationName',
                align : 'center'
            },{
                width : '130s',
                title : '创建时间',
                field : 'createdate',
                sortable : true,
                align : 'center'
            },  {
                width : '40',
                title : '性别',
                field : 'sex',
                sortable : true,
                align : 'center',
                formatter : function(value, row, index) {
                    switch (value) {
                    case 0:
                        return '男';
                    case 1:
                        return '女';
                    }
                }
            }, {
                width : '40',
                title : '年龄',
                field : 'age',
                sortable : true,
                align : 'center'
            },{
                width : '120',
                title : '电话',
                field : 'phone',
                sortable : true,
                align : 'center'
            }, 
            {
                width : '200',
                title : '角色',
                field : 'rolesList',
                align : 'center',
                formatter : function(value, row, index) {
                    var roles = [];
                    for(var i = 0; i< value.length; i++) {
                        roles.push(value[i].name);  
                    }
                    return(roles.join('\n'));
                }
            }, {
                width : '60',
                title : '用户类型',
                field : 'usertype',
                sortable : true,
                align : 'center',
                formatter : function(value, row, index) {
                    if(value == 0) {
                        return "管理员";
                    }else if(value == 1) {
                        return "用户";
                    }
                    return "未知类型";
                }
            },{
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
            } , {
                field : 'action',
                title : '操作',
                width : 130,
                align : 'center',
                formatter : function(value, row, index) {
                    var str = '';
                        <shiro:hasPermission name="user:edit">
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-edit" onclick="editFun(\'{0}\');" >编辑</a>', row.id);
                        </shiro:hasPermission>
                        <shiro:hasPermission name="user:delete">
                            str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
                            str += $.formatString('<a href="javascript:void(0)" class="user-easyui-linkbutton-del" onclick="deleteFun(\'{0}\');" >删除</a>', row.id);
                        </shiro:hasPermission>
                    return str;
                }
            }] ],
            onLoadSuccess:function(data){
                $('.user-easyui-linkbutton-edit').linkbutton({text:'编辑',plain:true,iconCls:'define-edit'});
                $('.user-easyui-linkbutton-del').linkbutton({text:'删除',plain:true,iconCls:'define-del'});
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
            href : '${path }/user/addPage',
            buttons : [ {
                text : '添加',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userAddForm');
                    f.submit();
                }
            } ]
        });
    }
    function deleteFun(id) {
        if (id == undefined) {//点击右键菜单才会触发这个
            var rows = dataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {//点击操作里面的删除图标会触发这个
            dataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
            if (b) {
                progressLoad();
                $.post('${path }/user/delete', {
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
            href : '${path }/user/editPage?id=' + id,
            buttons : [ {
                text : '确定',
                handler : function() {
                    parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#userEditForm');
                    f.submit();
                }
            } ]
        });
    }
    function searchFun() {
        dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
    }
    function cleanFun() {
        $('#searchForm input').val('');
        dataGrid.datagrid('load', {});
    }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false" style="height: 50px; line-height: 50px; margin-left: 2%; overflow: hidden;background-color: #fff">
        <form id="searchForm">
            <table>
                <tr>
                    <th>姓名:</th>
                    <td><input name="name" autocomplete="off" placeholder="请输入用户姓名"/></td>
                    <th>创建时间:</th>
                    <td>
                        <input name="createdateStart" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                            至
                        <input  name="createdateEnd" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'define-search',plain:true" onclick="searchFun();">查询</a>
                        <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'center',border:true,title:'用户列表'" >
        <table id="dataGrid" data-options="fit:true,border:false"></table>
    </div>
    <div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:150px;overflow: hidden; ">
        <ul id="organizationTree"  style="width:160px;margin: 10px 10px 10px 10px">
        </ul>
    </div>
    <div id="toolbar" style="display: none;">
        <shiro:hasPermission name="user:add">
            <a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'define-add'">添加</a>
        </shiro:hasPermission>
    </div>
</body>
</html>