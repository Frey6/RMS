package SystemManage.Common.controller;

import SystemManage.Common.until.DateUtil2;
import SystemManage.UserManage.entity.ShiroUser;
import SystemManage.Common.until.StringEscapeEditor;
import SystemManage.UserManage.entity.User;
import SystemManage.UserManage.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基础 Controller
 */
public class BaseController {

    @Autowired
    private UserService userService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        /**
//         * 自动转换日期类型的字段格式
//         */
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM"), true));
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy"), true));
//
//
//        /**
//         * 防止XSS攻击
//         */
//        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
//    }
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 使用自定义的 DATE数据绑定类
        binder.registerCustomEditor(Date.class, new DateUtil2());

        /**
         * 防止XSS攻击
         */
        binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
    }

    /**
     * 获取当前登录用户对象
     * @return
     */
    public User getCurrentUser() {
        ShiroUser shiroUser= (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        User currentUser = userService.findUserById(shiroUser.id);
        return currentUser;
    }

    /**
     * 获取当前登录用户id
     * @return
     */
    public Long getUserId() {
        return this.getCurrentUser().getId();
    }

    /**
     * 获取当前登录用户名
     * @return
     */
    public String getStaffName() {
        return this.getCurrentUser().getName();
    }

    /**
     *  获取当前用户 性别
     */
    public Integer getUserSex(){
        return this.getCurrentUser().getSex();
    }
}
