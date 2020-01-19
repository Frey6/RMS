package SystemManage.UserManage.controller;

import SystemManage.Common.aop.SysLog;
import SystemManage.Common.controller.BaseController;
import SystemManage.Common.datasource.DataSource;
import SystemManage.Common.entity.Result;
import SystemManage.UserManage.dao.UserDao;
import SystemManage.UserManage.entity.UserVo;
import SystemManage.Common.shiro.ShiroMD5;
import SystemManage.Common.until.PageInfo;
import SystemManage.RoleManage.entity.Role;
import SystemManage.UserManage.entity.User;
import SystemManage.UserManage.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 用户 Controller
 */

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService ;

    /**
     * 用户列表
     * @return
     */
    @SysLog(value = "用户信息查看")
    @RequiresPermissions("user:list")
    @RequestMapping("/manager")
    public String  manage(){
        return "user/user";
    }

    /**
     * 用户管理列表
     *
     * @param userVo
     * @param page
     * @param rows
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("user:list")
    @RequestMapping(value = "/dataGrid", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo dataGrid(UserVo userVo, Integer page, Integer rows, String sort, String order) {
        PageInfo pageInfo = new PageInfo(page, rows ,sort, order);

        // 查询的参数列表
        Map<String, Object> condition = new HashMap<String, Object>();

        if (userVo.getOrganizationId() != null) {
            System.out.println("得到的数据是: " + userVo.getOrganizationId());
            String[] organizationIds = userVo.getOrganizationId().split(",");
            List<String> batch = new ArrayList<>();
            for (String organizationId : organizationIds) {
                batch.add(organizationId);
            }
            condition.put("organizationIds" , batch);
        }
        if (StringUtils.isNoneBlank(userVo.getName())) {
            condition.put("name", userVo.getName());
        }
        if (userVo.getCreatedateStart() != null) {
            condition.put("startTime", userVo.getCreatedateStart());
        }
        if (userVo.getCreatedateEnd() != null) {
            condition.put("endTime", userVo.getCreatedateEnd());
        }
        pageInfo.setCondition(condition);
        userService.findDataGrid(pageInfo);
        return pageInfo;
    }

    /**
     * 用户编辑页面
     *
     */
    @RequiresPermissions("user:edit")
    @RequestMapping("/editPage")
    public String edit(Long id , Model model){
        UserVo userVo = userService.findUserVoById(id);
        List<Role> rolesList = userVo.getRolesList();
        List<Long> ids = new ArrayList<Long>();
        for (Role role : rolesList) {
            ids.add(role.getId());
        }
        model.addAttribute("roleIds", ids);
        model.addAttribute("user", userVo);
        return "user/userEdit" ;
    }

    /**
     * 编辑用户
     * @param userVo
     * @return
     */
    @SysLog(value = "用户信息编辑")
    @RequiresPermissions("user:edit")
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(UserVo userVo) {
        Result result = new Result();
        User user = userService.findUserByLoginName(userVo.getLoginname());
        if (user != null && user.getId() != userVo.getId()) {
            result.setMsg("用户名已存在!");
            return result;
        }
        try {
            if (userVo.getPassword() == null || userVo.getPassword().length() == 0){
                userVo.setPassword(null);
            }else{
                userVo.setPassword(ShiroMD5.GetPwd(userVo.getLoginname() , userVo.getPassword()));
            }
            userService.updateUser(userVo);
            result.setSuccess(true);
            result.setMsg("编辑成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("编辑失败！");
            return result;
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @SysLog(value = "用户信息删除")
    @RequiresPermissions("user:delete")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long id) {
        Result result = new Result();
        if (id == getUserId()){
            result.setMsg("不可以删除自己!");
            return result;
        }
        try {
            userService.deleteUserById(id);
            result.setMsg("删除成功！");
            result.setSuccess(true);
            return result;
        } catch (RuntimeException e) {
            result.setMsg("删除失败！");
            return result;
        }
    }

    /**
     * 添加用户页
     *
     * @return
     */
    @RequiresPermissions("user:add")
    @RequestMapping(value = "/addPage", method = RequestMethod.GET)
    public String addPage() {
        return "/user/userAdd";
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @SysLog(value = "用户信息添加")
    @RequiresPermissions("user:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result add(UserVo userVo) {
        Result result = new Result();
        User u = userService.findUserByLoginName(userVo.getLoginname());
        if (u != null) {
            result.setMsg("用户名已存在!");
            return result;
        }
        try {
            // 加密
            userVo.setPassword(ShiroMD5.GetPwd(userVo.getLoginname() , userVo.getPassword()));
            userService.addUser(userVo);
            result.setSuccess(true);
            result.setMsg("添加成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("添加失败！");
            return result;
        }
    }

    /**
     * 修改密码页
     *
     * @return
     */
    @RequestMapping(value = "/editPwdPage", method = RequestMethod.GET)
    public String editPwdPage() {
        return "user/userEditPwd";
    }

    /**
     * 修改密码
     *
     * @param request
     * @param oldPwd
     * @param newPwd
     * @return
     */
    @SysLog(value = "修改密码")
    @RequestMapping("/editUserPwd")
    @ResponseBody
    public Result editUserPwd(HttpServletRequest request, String oldPwd, String newPwd) {
        Result result = new Result();
        if (getCurrentUser().getLoginname().equals("test")){
            result.setMsg("测试账户不允许修改密码！");
            return result;
        }
        if (!getCurrentUser().getPassword().equals(ShiroMD5.GetPwd(getCurrentUser().getLoginname(),oldPwd))) {
            result.setMsg("原密码不正确!");
            return result;
        }
        try {
            userService.updateUserPwdById(getUserId(), ShiroMD5.GetPwd(getCurrentUser().getLoginname(),newPwd) );
            result.setSuccess(true);
            result.setMsg("密码修改成功！");
            return result;
        } catch (Exception e) {
            result.setMsg("密码修改失败！");
            return result;
        }
    }

    /**
     * 用户个人资料页
     * @return
     */
    @RequestMapping(value = "/personFile", method = RequestMethod.GET)
    public String personFile(Model model , HttpServletRequest request) {
        UserVo userVo = userService.findUserVoById(getUserId());
        List<Role> rolesList = userVo.getRolesList();
        List<String> names = new ArrayList<String>();
        for (Role role : rolesList) {
            names.add(role.getName());
        }
        model.addAttribute("user", userVo);
        request.setAttribute("roleNames", names);
        return "user/userPersonalFile";
    }

    /**
     * 用户个人资料编辑
     * @param userVo
     * @return
     */
    @SysLog(value = "个人资料编辑")
    @RequestMapping("/personFileEdit")
    @ResponseBody
    public Result editPersonFile(UserVo userVo) {
        Result result = new Result();
        try {
            userService.updatePersonFile(userVo);
            result.setSuccess(true);
            result.setMsg("编辑成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("编辑失败！");
            return result;
        }
    }

}