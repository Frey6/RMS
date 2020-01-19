package SystemManage.ResourceManage.controller;

import SystemManage.Common.aop.SysLog;
import SystemManage.Common.controller.BaseController;
import SystemManage.Common.datasource.DataSource;
import SystemManage.Common.datasource.DynamicDataSource;
import SystemManage.Common.entity.LeftMenu;
import SystemManage.Common.entity.Result;
import SystemManage.Common.entity.Tree;
import SystemManage.ResourceManage.entity.Resource;
import SystemManage.ResourceManage.service.ResourceService;
import SystemManage.UserManage.entity.User;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 资源控制层
 */

@Controller
@RequestMapping("resource")
public class ResourceController extends BaseController {

    @Autowired
    private ResourceService resourceService ;

    // 加载左边菜单的树 (layui)
    @RequestMapping(value = "tree" ,method = RequestMethod.GET)
    @ResponseBody
    public List<LeftMenu> findTree1(){
        User currentUser = getCurrentUser();
        List<LeftMenu> leftMenu = resourceService.findMenuTree(currentUser);
        return  leftMenu ;
    }
    // 加载菜单树 (easyui)
    @RequestMapping(value = "tree2" ,method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> findTree2(){
        User currentUser = getCurrentUser();
        List<Tree>  tree= resourceService.findMenuTree2(currentUser);
        return  tree ;
    }

    /**
     * 资源管理页
     */
    @SysLog(value = "资源信息查看")
    @RequiresPermissions("resource:list")
    @RequestMapping(value = "/manager", method = RequestMethod.GET)
    public String manager() {
        return "resource/resource";
    }

    /**
     * 加载所有资源
     */
    @RequiresPermissions("resource:list")
    @RequestMapping(value = "/treeGrid", method = RequestMethod.POST)
    @ResponseBody
    public List<Resource> treeGrid() {
        List<Resource> treeGrid = resourceService.findResourceAll();
        // 转义回去
        for (Resource resource : treeGrid){
           String icon = StringEscapeUtils.escapeHtml(resource.getIcon());
           resource.setIcon(icon);
        }
        return treeGrid;
    }

    /**
     * 编辑资源页
     *
     * @param request
     * @param id
     * @return
     */
    @RequiresPermissions("resource:edit")
    @RequestMapping("/editPage")
    public String editPage(HttpServletRequest request, Long id) {
        // 根据 树形网络表格中选中的行得到id 并根据id查出所对应的信息保存在request中
        Resource resource = resourceService.findResourceById(id);

        // 转义回去
        String icon = StringEscapeUtils.escapeHtml(resource.getIcon());
        resource.setIcon(icon);

        request.setAttribute("resource", resource);
        return "resource/resourceEdit";
    }

    /**
     * 编辑资源
     *
     * @param resource
     * @return
     */
    @SysLog(value = "资源信息编辑")
    @RequiresPermissions("resource:edit")
    @RequestMapping("/edit")
    @ResponseBody
    public Result edit(Resource resource) {
        Result result = new Result();
        try {
            // 带有&#的图标符号需要转义
            String icon = StringEscapeUtils.unescapeHtml(resource.getIcon());
            resource.setIcon(icon);
            resourceService.updateResource(resource);
            result.setSuccess(true);
            result.setMsg("编辑成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("编辑失败");
            return result;
        }
    }

    /**
     * 删除资源
     *
     * @param id
     * @return
     */
    @SysLog(value = "资源信息删除")
    @RequiresPermissions("resource:delete")
    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(Long id) {
        Result result = new Result();
        try {
            resourceService.deleteResourceById(id);
            result.setMsg("删除成功！");
            result.setSuccess(true);
            return result;
        } catch (RuntimeException e) {
            result.setMsg("删除失败");
            return result;
        }
    }

    /**
     * 添加资源页
     * @return
     */
    @RequiresPermissions("resource:add")
    @RequestMapping("/addPage")
    public String addPage() {
        return "resource/resourceAdd";
    }

    /**
     * 添加资源
     *
     * @param resource
     * @return
     */
    @SysLog(value = "资源信息添加")
    @RequiresPermissions("resource:add")
    @RequestMapping("/add")
    @ResponseBody
    public Result add(Resource resource) {
        Result result = new Result();
        try {
            resourceService.addResource(resource);
            result.setSuccess(true);
            result.setMsg("添加成功！");
            return result;
        } catch (RuntimeException e) {
            result.setMsg("添加失败");
            return result;
        }
    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    @RequestMapping("/allTree")
    @ResponseBody
    public List<Tree> allTree() {
        return resourceService.findAllTree();
    }

    /**
     * 查询所有的资源tree ( 角色管理中的授权树 )
     *
     * @return
     */
    @RequestMapping(value = "/allTrees", method = RequestMethod.POST)
    @ResponseBody
    public List<Tree> allTrees() {
        return resourceService.findAllTrees();
    }

}
