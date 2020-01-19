package SystemManage.RoleManage.dao;

import SystemManage.Common.entity.Tree;
import SystemManage.Common.until.PageInfo;
import SystemManage.ResourceManage.entity.Resource;
import SystemManage.RoleManage.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RoleDao {

    /**
     * 查询角色列表
     *
     * @param pageInfo
     * @return
     */
    List findRolePageCondition(PageInfo pageInfo);

    /**
     * 角色统计
     *
     * @param pageInfo
     * @return
     */
    int findRolePageCount(PageInfo pageInfo);

    /**
     * 根据角色查询资源id列表
     *
     * @param id
     * @return
     */
    List<Long> findResourceIdListByRoleId(Long id);

    /**
     * 根据角色id查询资源角色关联id列表
     * @param
     * @return
     */
    List<Long> findRoleResourceIdListByRoleId(Long id);

    /**
     * 添加角色
     *
     * @Param role
     */
    int insert(Role role);

    /**
     * 通过id查找角色
     * @param id
     */
    Role findRoleById(Long id);

    /**
     * 更新角色信息
     * @param role
     */
    int updateRole(Role role);

    /**
     * 删除角色信息
     * @param id
     */
    int deleteRoleById(Long id);

    /**
     * 加载权限树
     */
    List<Role> findRoleAll();

    /**
     * 通过角色 Id 资源路径
     * @param roleId
     * @return
     */
    List<Map<Long, String>> findRoleResourceListByRoleId(Long roleId);

    /**
     * 查询角色下的菜单列表
     *
     * @param i
     * @return
     */
    List<Resource> findResourceIdListByRoleIdAndType(Long i);

}
