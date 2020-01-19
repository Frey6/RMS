package SystemManage.ResourceManage.service;

import SystemManage.Common.datasource.DataSource;
import SystemManage.Common.entity.LeftMenu;
import SystemManage.Common.entity.Tree;
import SystemManage.Common.until.Config;
import SystemManage.ResourceManage.dao.ResourceDao;
import SystemManage.ResourceManage.entity.Resource;
import SystemManage.RoleManage.dao.RoleDao;
import SystemManage.UserManage.dao.UserRoleDao;
import SystemManage.UserManage.entity.User;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ResourceService {

    @Autowired
    private UserRoleDao userRoleDao ;
    @Autowired
    private RoleDao roleDao ;
    @Autowired
    private ResourceDao resourceDao ;

    /**
     * 查询树形菜单列表 (Layui)
     * @return
     */
    public List<LeftMenu> findMenuTree(User user) {

        List<LeftMenu> leftMenus = new ArrayList<LeftMenu>();
        // 超级管理员给与最大权限,加载的时候直接全部加载
        if (user.getLoginname().equals("admin")) {
            // 寻找父资源
            List<Resource> resourceFather = resourceDao.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
            if (resourceFather == null) {
                return null;
            }
            for (Resource resourceOne : resourceFather) {
                LeftMenu menuOne = new LeftMenu();
                menuOne.setId(resourceOne.getId());
                menuOne.setTitle(resourceOne.getName());
                menuOne.setIcon(resourceOne.getIcon());
                menuOne.setHref(resourceOne.getUrl());
                menuOne.setSpread(true);
                List<Resource> resourceSon = resourceDao.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());
                if (resourceSon != null) {
                    List<LeftMenu> leftMenu = new ArrayList<LeftMenu>();
                    for (Resource resourceTwo : resourceSon) {
                        LeftMenu menuTwo = new LeftMenu();
                        menuTwo.setId(resourceTwo.getId());
                        menuTwo.setTitle(resourceTwo.getName());
                        menuTwo.setIcon(resourceTwo.getIcon());
                        menuTwo.setHref(resourceTwo.getUrl());
                        menuTwo.setSpread(true);
                        leftMenu.add(menuTwo);
                    }
                    menuOne.setChildren(leftMenu);
                } else {
                    menuOne.setSpread(false);
                }
                leftMenus.add(menuOne);
            }
            return leftMenus;
        }

        // 其他用户或管理员通过角色的不同加载相应权限
        List<Resource> resourceIdList = new ArrayList<Resource>();
        List<Long> roleIdList = userRoleDao.findRoleIdListByUserId(user.getId());
        for (Long i : roleIdList) {
            List<Resource> resourceIdLists = roleDao.findResourceIdListByRoleIdAndType(i);
            for (Resource resource: resourceIdLists) {
                resourceIdList.add(resource);
            }
        }
        for (Resource resource : resourceIdList) {
            if (resource != null && resource.getPid() == null) {
                LeftMenu menuOne = new LeftMenu();
                menuOne.setId(resource.getId());
                menuOne.setTitle(resource.getName());
                menuOne.setIcon(resource.getIcon());
                menuOne.setHref(resource.getUrl());
                menuOne.setSpread(true);

                List<LeftMenu> leftMenu = new ArrayList<LeftMenu>();
                for (Resource resourceTwo : resourceIdList) {
                    if (resourceTwo.getPid() != null && resource.getId().longValue() == resourceTwo.getPid().longValue()) {
                        LeftMenu menuTwo = new LeftMenu();
                        menuTwo.setId(resourceTwo.getId());
                        menuTwo.setTitle(resourceTwo.getName());
                        menuTwo.setIcon(resourceTwo.getIcon());
                        menuTwo.setHref(resourceTwo.getUrl());
                        menuTwo.setSpread(true);
                        leftMenu.add(menuTwo);
                    }
                }
                menuOne.setChildren(leftMenu);
                leftMenus.add(menuOne);
            }
        }
        return leftMenus;
    }


    /**
     * 查询树形菜单列表 (Easyui)
     * @return
     */
    public List<Tree> findMenuTree2(User user) {
        List<Tree> trees = new ArrayList<Tree>();
        // 超级管理
        if (user.getLoginname().equals("admin")) {
            // 寻找父资源
            List<Resource> resourceFather = resourceDao.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
            if (resourceFather == null) {
                return null;
            }
            for (Resource resourceOne : resourceFather) {
                Tree treeOne = new Tree();

                treeOne.setId(resourceOne.getId());
                treeOne.setText(resourceOne.getName());
                treeOne.setIconCls(resourceOne.getIcon());
                treeOne.setAttributes(resourceOne.getUrl());
                List<Resource> resourceSon = resourceDao.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

                if (resourceSon != null) {
                    List<Tree> tree = new ArrayList<Tree>();
                    for (Resource resourceTwo : resourceSon) {
                        Tree treeTwo = new Tree();
                        treeTwo.setId(resourceTwo.getId());
                        treeTwo.setText(resourceTwo.getName());
                        treeTwo.setIconCls(resourceTwo.getIcon());
                        treeTwo.setAttributes(resourceTwo.getUrl());
                        tree.add(treeTwo);
                    }
                    treeOne.setChildren(tree);
                } else {
                    treeOne.setState("closed");
                }
                trees.add(treeOne);
            }
            return trees;
        }

        // 普通用户
        Set<Resource> resourceIdList = new HashSet<Resource>();
        List<Long> roleIdList = userRoleDao.findRoleIdListByUserId(user.getId());
        for (Long i : roleIdList) {
            List<Resource> resourceIdLists = roleDao.findResourceIdListByRoleIdAndType(i);
            for (Resource resource: resourceIdLists) {
                resourceIdList.add(resource);
            }
        }
        for (Resource resource : resourceIdList) {
            if (resource != null && resource.getPid() == null) {
                Tree treeOne = new Tree();
                treeOne.setId(resource.getId());
                treeOne.setText(resource.getName());
                treeOne.setIconCls(resource.getIcon());
                treeOne.setAttributes(resource.getUrl());
                List<Tree> tree = new ArrayList<Tree>();
                for (Resource resourceTwo : resourceIdList) {
                    if (resourceTwo.getPid() != null && resource.getId().longValue() == resourceTwo.getPid().longValue()) {
                        Tree treeTwo = new Tree();
                        treeTwo.setId(resourceTwo.getId());
                        treeTwo.setText(resourceTwo.getName());
                        treeTwo.setIconCls(resourceTwo.getIcon());
                        treeTwo.setAttributes(resourceTwo.getUrl());
                        tree.add(treeTwo);
                    }
                }
                treeOne.setChildren(tree);
                trees.add(treeOne);
            }
        }
        return trees;
    }

    /**
     * 查找二级树
     * @return
     */
    public List<Tree> findAllTree() {
        List<Tree> trees = new ArrayList<Tree>();
        // 查询所有的一级树
        List<Resource> resources = resourceDao.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
        if (resources == null) {
            return null;
        }
        for (Resource resourceOne : resources) {
            Tree treeOne = new Tree();

            treeOne.setId(resourceOne.getId());
            treeOne.setText(resourceOne.getName());
            treeOne.setIconCls(resourceOne.getIcon());
            treeOne.setAttributes(resourceOne.getUrl());
            // 查询所有一级树下的菜单
            List<Resource> resourceSon = resourceDao.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

            if (resourceSon != null) {
                List<Tree> tree = new ArrayList<Tree>();
                for (Resource resourceTwo : resourceSon) {
                    Tree treeTwo = new Tree();
                    treeTwo.setId(resourceTwo.getId());
                    treeTwo.setText(resourceTwo.getName());
                    treeTwo.setIconCls(resourceTwo.getIcon());
                    treeTwo.setAttributes(resourceTwo.getUrl());
                    tree.add(treeTwo);
                }
                treeOne.setChildren(tree);
            } else {
                treeOne.setState("closed");
            }
            trees.add(treeOne);
        }
        return trees;
    }

    /**
     * 查找三级树
     * @return
     */
    public List<Tree> findAllTrees() {
        List<Tree> treeOneList = new ArrayList<Tree>();

        // 查询所有的一级树
        List<Resource> resources = resourceDao.findResourceAllByTypeAndPidNull(Config.RESOURCE_MENU);
        if (resources == null) {
            return null;
        }

        for (Resource resourceOne : resources) {
            Tree treeOne = new Tree();

            treeOne.setId(resourceOne.getId());
            treeOne.setText(resourceOne.getName());
            treeOne.setIconCls(resourceOne.getIcon());
            treeOne.setAttributes(resourceOne.getUrl());

            List<Resource> resourceSon = resourceDao.findResourceAllByTypeAndPid(Config.RESOURCE_MENU, resourceOne.getId());

            if (resourceSon == null) {
                treeOne.setState("closed");
            } else {
                List<Tree> treeTwoList = new ArrayList<Tree>();

                for (Resource resourceTwo : resourceSon) {
                    Tree treeTwo = new Tree();

                    treeTwo.setId(resourceTwo.getId());
                    treeTwo.setText(resourceTwo.getName());
                    treeTwo.setIconCls(resourceTwo.getIcon());
                    treeTwo.setAttributes(resourceTwo.getUrl());

                    /***************************************************/
                    List<Resource> resourceSons = resourceDao.findResourceAllByTypeAndPid(Config.RESOURCE_BUTTON, resourceTwo.getId());

                    if (resourceSons == null) {
                        treeTwo.setState("closed");
                    } else {
                        List<Tree> treeThreeList = new ArrayList<Tree>();

                        for (Resource resourceThree : resourceSons) {
                            Tree treeThree = new Tree();

                            treeThree.setId(resourceThree.getId());
                            treeThree.setText(resourceThree.getName());
                            treeThree.setIconCls(resourceThree.getIcon());
                            treeThree.setAttributes(resourceThree.getUrl());

                            treeThreeList.add(treeThree);
                        }
                        treeTwo.setChildren(treeThreeList);
                    }
                    treeTwoList.add(treeTwo);
                }
                treeOne.setChildren(treeTwoList);
            }
            treeOneList.add(treeOne);
        }
        return treeOneList;
    }


    /**
     * 查找所有资源
     * @return
     */
    public List<Resource> findResourceAll() {
        return resourceDao.findResourceAll();
    }

    /**
     * 根据id查询资源
     * @param id
     * @return
     */
    public Resource findResourceById(Long id) {
        return resourceDao.findResourceById(id);
    }

    /**
     * 更新资源
     * @param resource
     */
    public void updateResource(Resource resource) {
        int update = resourceDao.updateResource(resource);
        // 如果更新失败则抛出异常，Controller中则会捕捉到异常，并且返回信息给页面
        if (update != 1) {
            throw new RuntimeException("更新失败！");
        }
    }

    /**
     * 根据id删除资源
     * @param id
     */
    public void deleteResourceById(Long id) {
        int delete = resourceDao.deleteResourceById(id);
        if (delete != 1) {
            throw new RuntimeException("删除失败！");
        }
    }

    /**
     * 添加资源
     * @param resource
     */
    public void addResource(Resource resource) {
        resourceDao.insert(resource);
    }

}