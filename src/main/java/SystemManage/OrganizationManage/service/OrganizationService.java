package SystemManage.OrganizationManage.service;

import SystemManage.Common.entity.Tree;
import SystemManage.Common.until.PageInfo;
import SystemManage.OrganizationManage.dao.OrganizationDao;
import SystemManage.OrganizationManage.entity.Organization;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationDao organizationDao ;

    /**
     * @description
     * 查找用户管理中组织机构的资源树
     * 第一步: 先加载父资源
     * 第二步: 通过父资源的 id 查询子资源
     * 加入到 实体层中
     * @return
     */
    public List<Tree> findTree(){
        List<Tree> trees = new ArrayList<Tree>();
        // 查找父资源的信息 ;
        List<Organization> organizationFather = organizationDao.findOrganizationAllByPidNull();
        if (organizationFather != null){
            for (Organization organizationOne : organizationFather){
                Tree treeOne = new Tree();
                treeOne.setId(organizationOne.getId());
                treeOne.setText(organizationOne.getName());
                treeOne.setIconCls(organizationOne.getIcon());

                List<Organization> organizationSon = organizationDao.findOrganizationAllByPid(organizationOne.getId());
                if (organizationSon != null){
                    List<Tree> tree = new ArrayList<Tree>();
                    for (Organization organizationTwo : organizationSon ){
                        Tree treeTwo = new Tree();
                        treeTwo.setId(organizationTwo.getId());
                        treeTwo.setText(organizationTwo.getName());
                        treeTwo.setIconCls(organizationTwo.getIcon());
                        tree.add(treeTwo);
                    }
                    treeOne.setChildren(tree);
                } else {
                    treeOne.setState("closed");
                }
                trees.add(treeOne);
            }
        }
        return trees ;
    }

    public List<Organization> findTreeGrid() {
        return organizationDao.findOrganizationAll();
    }

    public void addOrganization(Organization organization) {
        organizationDao.insert(organization);
    }

    public Organization findOrganizationById(Long id) {
        return organizationDao.findOrganizationById(id);
    }

    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    public void deleteOrganizationById(Long id) {
        organizationDao.deleteOrganizationById(id);
    }







}