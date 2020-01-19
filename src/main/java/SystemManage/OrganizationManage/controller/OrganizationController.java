package SystemManage.OrganizationManage.controller;

import SystemManage.Common.aop.SysLog;
import SystemManage.Common.controller.BaseController;
import SystemManage.Common.entity.Result;
import SystemManage.Common.entity.Tree;
import SystemManage.OrganizationManage.entity.Organization;
import SystemManage.OrganizationManage.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description
 *
 */

@Controller
@RequestMapping("organization")
public class OrganizationController extends BaseController {

    @Autowired
    private OrganizationService organizationService ;

    /**
     * 部门管理主页
     * @return
     */
    @SysLog(value = "部门信息查看")
    @RequiresPermissions("organization:list")
    @RequestMapping("/manager")
    public String  manage(){
        return "organization/organization";
    }

    /**
     * @description 用户管理中 组织机构的 部门资源树
     *
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> tree() {
        List<Tree> trees = organizationService.findTree();
        return trees;
    }

    /**
     * 部门列表
     *
     * @return
     */
    @RequiresPermissions("organization:list")
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<Organization> treeGrid() {
        List<Organization> treeGrid = organizationService.findTreeGrid();
        return treeGrid;
    }

    /**
     * 添加部门页
     *
     * @return
     */
    @RequiresPermissions("organization:add")
    @RequestMapping("/addPage")
    public String addPage() {
        return "organization/organizationAdd";
    }

    /**
     * 添加部门
     *
     * @param organization
     * @return
     */
    @SysLog(value = "部门信息添加")
    @RequiresPermissions("organization:add")
    @RequestMapping("/add")
    @ResponseBody
    public Result add(Organization organization) {
        Result result = new Result();
        try {
            organizationService.addOrganization(organization);
            result.setSuccess(true);
            result.setMsg("添加成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("添加失败！");
            return result;
        }
    }

    /**
     * 编辑部门页
     *
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions("organization:edit")
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Long id) {
        Organization organization = organizationService.findOrganizationById(id);
        request.setAttribute("organization", organization);
        return "organization/organizationEdit";
    }

    /**
     * 编辑部门
     *
     * @param organization
     * @return
     */
    @SysLog(value = "部门信息编辑")
    @RequiresPermissions("organization:edit")
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(Organization organization) {
        Result result = new Result();
        try {
            organizationService.updateOrganization(organization);
            result.setSuccess(true);
            result.setMsg("编辑成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("编辑失败！");
            return result;
        }
    }

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    @SysLog(value = "部门信息删除")
    @RequiresPermissions("organization:delete")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long id) {
        Result result = new Result();
        try {
            organizationService.deleteOrganizationById(id);
            result.setMsg("删除成功！");
            result.setSuccess(true);
            return result;
        } catch (RuntimeException e) {
            result.setMsg("删除失败！");
            return result;
        }
    }
}
