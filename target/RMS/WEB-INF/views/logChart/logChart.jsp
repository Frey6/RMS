<%--
  Created by IntelliJ IDEA.
  User: yongfeng.L
  Date: 2019/4/25
  Time: 3:54 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/global.jsp" %>
<!DOCTYPE html>
<html style="width: 100%;height: 100%;">
<head>
    <%@ include file="/commons/basejs.jsp" %>
    <meta http-equiv="X-UA-Compatible" content="edge" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%-- Echarts 图表组件--%>
    <%-- 这个是在线定制的图表组件,用于减少加载速度,完整版可用echarts.min.js --%>
    <script src="${staticPath }/static/echarts.min2.js"></script>
    <title>日志图表统计</title>
    <script type="text/javascript">
        function searchFun() {
            var jsonParam=$("#searchForm").serialize();
            getLogData(jsonParam);
        }
        function cleanFun() {
            $('#time').combobox('select','day');
            $("#timeday").show();
            $("#timemonth").hide();
            $("#timeyear").hide();

            $("#timeday").attr("name","createdateStart");
            $("#timemonth").attr("name","");
            $("#timeyear").attr("name","");
            var date = new Date();
            $('#timeday').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1) + '-'+addZero(date.getDate()));
            var jsonParam=$("#searchForm").serialize();
            getLogData(jsonParam);
        }
        //小提示框
        function showNotice(){
            layer.open({
                type: 1,
                title: "小提示",
                closeBtn: false,
                area: '310px',
                shade: 0.6,
                btn: ['确定'],
                // moveType: 1,
                content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>图表功能完善中, 后续开源</p><p><strong class="layui-red" style="color: red">请关注群消息！（876014073）</strong></p></div>',
                success: function(layero){
                    var btn = layero.find('.layui-layer-btn');
                    btn.css('text-align', 'center');
                }
            });
        }
    </script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false" style="width: 100%;height: 100%;">
<div data-options="region:'north',border:false" style="height: 50px; line-height: 50px; margin-left: 2%; overflow: hidden;background-color: #fff">
    <form id="searchForm">
        <table>
            <tr>
                <th>时间类型:</th>
                <td>
                    <select id="time" name = "time" data-options="editable:false,panelHeight:'auto'" style="width: 100px;"  class="easyui-combobox">
                        <option selected value="day">日</option>
                        <option value="month">月</option>
                        <option value="year">年</option>
                    </select>
                </td>
                <th>&nbsp;时间:</th>
                <td>
                    <input id="timeday" name="" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})" readonly="readonly" />
                    <input id="timemonth" name="" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM',isShowClear:false})" readonly="readonly" />
                    <input id="timeyear" name="" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy',isShowClear:false})" readonly="readonly" />

                    <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'define-search',plain:true" onclick="searchFun();">查询</a>
                    <a href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空查询</a>
                    <a href="javascript:;" id="tip" class="easyui-linkbutton" onclick="showNotice()" data-options="iconCls:'icon-tip',plain:true" style="margin-left: 40px">小提示</a>
                </td>
            </tr>
        </table>
    </form>
</div>

<!-- 初始化dom容器 -->
<div data-options = "region:'center'"style="width: 100%; height:100%">
    <%--74%--%>
    <div id="zu" style="width: 80%;height: 84%;padding-top: 2%;margin:0 auto;" ></div>
</div>

<script>
    // document.onreadystatechange = function () {
    //     if (document.readyState === "interactive") {
    //         progressLoad();
    //     }
    //     if (document.readyState === "complete") {
    //         progressClose();
    //     }
    // };
    $(function(){
        $("#timemonth").hide();
        $("#timeyear").hide();
        $("#timeday").attr("name","createdateStart");
        var date = new Date();
        $('#timeday').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1) + '-'+addZero(date.getDate()));
        var jsonParam=$("#searchForm").serialize();
        getLogData(jsonParam);
        layer.tips('点击查看小提示哦', '#tip', {
            tips: 2,
            time : 1500
        });
    });
    function getLogData(param) {
        progressLoad();
        $.ajax({
            url : "${staticPath}/logChart/data",
            type : "post",
            dataType : "json",
            data: param,
            success : function(data) {
                progressClose();
                loaderChart(data);
                console.log(data);
            },error : function() {
                alert("网络连接出错！");
                progressClose();
            }
        });
    }
    $("#time").combobox({
        onChange: function () {
            var selectValue = $("#time").combobox('getValue');
            // if (selectValue == "day") {
            //
            //     $("#createdateStart").attr("onclick","WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd',isShowClear:false})");
            //     var date = new Date();
            //     $('#createdateStart').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1) + '-'+addZero(date.getDate()));
            //
            // } else if(selectValue == "month"){
            //
            //     $("#createdateStart").attr("onclick","WdatePicker({readOnly:true,dateFmt:'yyyy-MM',isShowClear:false})");
            //     var date = new Date();
            //     $('#createdateStart').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1));
            //
            // } else if(selectValue == "year"){
            //
            //     $("#createdateStart").attr("onclick","WdatePicker({readOnly:true,dateFmt:'yyyy',isShowClear:false})");
            //     var date = new Date();
            //     $('#createdateStart').val(date.getFullYear().toString());
            // }
            if (selectValue == "day") {
                $("#timeday").show();
                $("#timemonth").hide();
                $("#timeyear").hide();

                var date = new Date();
                $('#timeday').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1) + '-'+addZero(date.getDate()));

                $("#timeday").attr("name","createdateStart");
                $("#timemonth").attr("name","");
                $("#timeyear").attr("name","");

            } else if(selectValue == "month"){
                $("#timemonth").show();
                $("#timeday").hide();
                $("#timeyear").hide();

                var date = new Date();
                $('#timemonth').val(date.getFullYear().toString() + '-'+addZero(date.getMonth() + 1));

                $("#timeday").attr("name","");
                $("#timemonth").attr("name","createdateStart");
                $("#timeyear").attr("name","");


            } else if(selectValue == "year"){
                $("#timeyear").show();
                $("#timemonth").hide();
                $("#timeday").hide();

                var date = new Date();
                $('#timeyear').val(date.getFullYear().toString());

                $("#timeday").attr("name","");
                $("#timemonth").attr("name","");
                $("#timeyear").attr("name","createdateStart");
            }

        }
    });
    function addZero(v) {
        if (v < 10) return '0' + v;return v.toString();
    }
    var myEcharts = echarts.init(document.getElementById('zu'));
    function loaderChart(data) {
        var option = {
            title: {
                // text: '日志统计',
                // x: 'left',
                // left: 60
            },
            xAxis: [{
                name: '时间',
                data:data.obj.xdata
            }],
            yAxis: [{
                name: '日志数量',
                type: 'value'
            }, {
                name: '日志占比(%)',
                type: 'value'
            }],
            series: [{
                type: 'bar',
                name: '日志数量',
                data:data.obj.ydata1,
                itemStyle: {
                    // color: 'red'
                }
            }, {
                type: 'line',
                name: '日志占比',
                yAxisIndex: 1,
                data: data.obj.ydata2,
                itemStyle: {
                    color: 'purple'
                }
            }],
            legend: {},
            tooltip: [{
                trigger: 'axis'
            }],
            toolbox: {
                right: '15%',
                feature: {
                    dataView: {},
                    magicType: {
                        type: ['bar', 'line', 'pie']
                    },
                    restore: {},
                    saveAsImage: {}
                }
            }
        };
        myEcharts.setOption(option);
    }
</script>
</body>
</html>
