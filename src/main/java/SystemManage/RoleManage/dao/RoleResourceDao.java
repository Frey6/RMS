package SystemManage.RoleManage.dao;

import SystemManage.RoleManage.entity.RoleResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleResourceDao {

    /**
     * 添加角色资源关联
     *
     * @param roleResource
     * @return
     */
    int insert(RoleResource roleResource);

    /**
     * 批量添加角色资源关联
     * @param roleResource
     * @return
     */
    int batchInsert(List<RoleResource> roleResource);


    /**
     * 根据角色id查询角色资源关联列表
     *
     * @param id
     * @return
     */
    List<RoleResource> findRoleResourceIdListByRoleId(Long id);

    /**
     * 删除角色资源关联关系
     *
     * @param roleResourceId
     * @return
     */
    int deleteById(Long roleResourceId);

    /**
     * 批量删除资源关联管理
     * @param ids
     * @return
     */
    int batchDelete(List ids);


}
