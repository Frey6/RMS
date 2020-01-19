package SystemManage.OrganizationManage.dao;

import SystemManage.Common.entity.Tree;
import SystemManage.OrganizationManage.entity.Organization;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OrganizationDao {

    /**
     * 查询一级部门
     *
     * @return
     */
    List<Organization> findOrganizationAllByPidNull() ;

    /**
     * 查询部门子集
     *
     * @param pid
     * @return
     */
    List<Organization> findOrganizationAllByPid(Long pid);

    /**
     * 删除部门
     *
     * @param id
     * @return
     */
    int deleteOrganizationById(Long id);

    /**
     * 添加部门
     *
     * @param organization
     * @return
     */
    int insert(Organization organization);

    /**
     * 更新部门
     *
     * @param organization
     * @return
     */
    int updateOrganization(Organization organization);

    /**
     * 查询所有部门集合
     *
     * @return
     */
    List<Organization> findOrganizationAll();

    /**
     * 根据id查询部门
     *
     * @param id
     * @return
     */
    Organization findOrganizationById(Long id);

}
