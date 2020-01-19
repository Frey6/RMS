<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
        <form method="post">
            <table class="grid">
                <tr>
                    <td>操作</td>
                    <td><input autocomplete="off" type="text" class="span2" data-options="required:true" value="${log.operationName}"></td>
                    <td>请求路径</td>
                    <td><input style="width: 140px; height: 29px;padding-left: 4px" required="required" data-options="editable:false" value="${log.operationAddress}"></td>
                </tr>
                <tr>
                    <td>请求类</td>
                    <td colspan="3"><input style="width: 394px; height: 29px;padding-left: 4px" required="required" data-options="editable:false" value="${log.operationClass}"></td>
                </tr>
                <tr>
                    <td>请求参数</td>
                    <td colspan="3"><input style="width: 394px; height: 29px;padding-left: 4px" required="required" data-options="editable:false" value="${log.params}"></td>
                </tr>
                <tr>
                    <td>IP</td>
                    <td><input autocomplete="off" type="text" placeholder="请输入角色名称" class="easyui-validatebox span2" data-options="required:true" value="${log.ip}"></td>
                    <td>操作人</td>
                    <td><input style="width: 140px; height: 29px;padding-left: 4px" required="required" data-options="editable:false" value="${log.loginName}"></td>
                </tr>
                <tr>
                    <td>创建时间</td>
                    <td colspan="3"><input style="width: 394px; height: 29px;padding-left: 4px" required="required" data-options="editable:false" value="${log.createTime}"></td>
                </tr>
            </table>
        </form>
    </div>
</div>