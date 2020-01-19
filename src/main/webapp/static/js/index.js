var $,tab,layer;
// 全局配置
layui.config({
	// 加载外部扩展模块
	base : "static/js/"
}).extend({
    "bodyTab" : "bodyTab"    // 组件扩展 , 所以这里bodyTab.js相当于layui的扩展模块,不用另外引用
});
layui.use(['bodyTab','form','element','layer','jquery'],function(){
	var form = layui.form,
		layer = layui.layer,
		element = layui.element,
		$ = layui.jquery;

		tab = layui.bodyTab({
			openTabNum : "50",  //最大可打开窗口数量
			url : "resource/tree", //获取菜单json地址
		});

    //渲染左侧菜单
    tab.render();

	//隐藏左侧导航
	$(".hideMenu").click(function(){
		$(".layui-layout-admin").toggleClass("showMenu");
		//渲染顶部窗口
		tab.tabMove();
	});

	//手机设备的简单适配
	var treeMobile = $('.site-tree-mobile'),
		shadeMobile = $('.site-mobile-shade');

	treeMobile.on('click', function(){
		$('body').addClass('site-mobile');
	});
	shadeMobile.on('click', function(){
		$('body').removeClass('site-mobile');
	});

	// 添加新窗口
	$("body").on("click",".layui-nav .layui-nav-item a",function(){
		//如果不存在子级
		if($(this).siblings().length == 0){
			addTab($(this));
			$('body').removeClass('site-mobile');  //移动端点击菜单关闭菜单层
		}
		$(this).parent("li").siblings().removeClass("layui-nav-itemed");
	});

    //刷新后还原打开的窗口
    if(window.sessionStorage.getItem("menu") != null){
        menu = JSON.parse(window.sessionStorage.getItem("menu"));
        curmenu = window.sessionStorage.getItem("curmenu");
        var openTitle = '';
        for(var i=0;i<menu.length;i++){
            openTitle = '';
            if(menu[i].icon){
                if(menu[i].icon.split("-")[0] == 'icon'){
                    openTitle += '<i class="iconfont1 '+menu[i].icon+'"></i>';
                }else{
                    openTitle += '<i class="layui-icon">'+menu[i].icon+'</i>';
                }
            }
            openTitle += '<cite>'+menu[i].title+'</cite>';
            openTitle += '<i class="layui-icon layui-unselect layui-tab-close" data-id="'+menu[i].layId+'">&#x1006;</i>';
            element.tabAdd("bodyTab",{
                title : openTitle,
                content :"<iframe src='"+menu[i].href+"' data-id='"+menu[i].layId+"'></frame>",
                id : menu[i].layId
            });
            //定位到刷新前的窗口
            if(curmenu != "undefined"){
                if(curmenu == '' || curmenu == "null"){  //定位到后台首页
                    element.tabChange("bodyTab",'');
                }else if(JSON.parse(curmenu).title == menu[i].title){  //定位到刷新前的页面
                    element.tabChange("bodyTab",menu[i].layId);
                }
            }else{
                element.tabChange("bodyTab",menu[menu.length-1].layId);
            }
        }
        //渲染顶部窗口
        tab.tabMove();
    }

	//公告层
	function showNotice(){
		layer.open({
	        type: 1,
	        title: "系统公告",
	        closeBtn: false,
	        area: '310px',
	        shade: 0.8,
	        id: 'LAY_layuipro',
	        btn: ['火速围观'],
	        moveType: 1,
	        content: '<div style="padding:15px 20px; text-align:justify; line-height: 22px; text-indent:2em;border-bottom:1px solid #e2e2e2;"><p>本系统是通过layui + easyui集成写的，基于ssm框架，mysql数据库。layui+easyui的集成也意味着有着更多的可能性！</p><p></p></div>',
	         success: function(layero){
				var btn = layero.find('.layui-layer-btn');
				btn.css('text-align', 'center');
				btn.on("click",function(){
					window.sessionStorage.setItem("showNotice","true");
				});
				if($(window).width() > 432){  //如果页面宽度不足以显示顶部“系统公告”按钮，则不提示
					btn.on("click",function(){
						layer.tips('系统公告躲在了这里', '#showNotice', {
							tips: 3,
                            time : 1000
						});
					})
				}
	        }
	    });
	}
	if(window.sessionStorage.getItem("showNotice") != "true"){
		showNotice();
	}
	$(".showNotice").on("click",function(){
		showNotice();
	});

    //退出
    $(".signOut").click(function(){
        window.sessionStorage.removeItem("menu");
        menu = [];
        window.sessionStorage.removeItem("curmenu");
    });
});

//打开新窗口
function addTab(_this){
	tab.tabAdd(_this);
}

// 显示当前时间
var weeks=["日","一","二","三","四","五","六"];
function showTime(){
    var date=new Date();
    var y=date.getFullYear();
    var m=date.getMonth()+1;
    var d=date.getDate();
    var w=date.getDay();
    var h=date.getHours();
    var mi=date.getMinutes();
    var s=date.getSeconds();
    var ms=m<10?"0"+m:m;
    var ds=d<10?"0"+d:d;
    var hs=h<10?"0"+h:h;
    var mis=mi<10?"0"+mi:mi;
    var ss=s<10?"0"+s:s;
    var time=y+"年"+ms+"月"+ds+"日  "+hs+":"+mis+":"+ss+"  星期"+weeks[w];
    document.getElementById("div_time").innerText=time;
    document.getElementById("div_time").style.color="#ffffff";
    document.getElementById("div_time").style.fontSize="14px";
    document.getElementById("div_time").style.lineHeight="60px";
    document.getElementById("div_time").style.marginLeft="20px";
    setTimeout("showTime()",1000);
}
