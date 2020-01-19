package SystemManage.Common.controller;

import SystemManage.Common.aop.SysLog;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController {

    // 后台首页
    @RequestMapping("/")
    public  String  index(){
        return "index";
    }
    // 后台首页
    @RequestMapping("/index")
    public  String  index2(){
        return "index";
    }
    // 后台主页面
    @RequestMapping("/main")
    public  String  main(){
        return "main" ;
    }
    // 登录页面
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public  String  login(){
        return "login" ;
    }

    /**
     * 未授权
     */
    @RequestMapping(value = "/unAuth")
    public String unAuth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "error/unAuth";
    }
}
