package SystemManage.UserManage.dao;


import SystemManage.UserManage.entity.UserRole;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRoleDao {

    int insert(UserRole userRole);

    int updateByPrimaryKeySelective(UserRole userRole);

    List<Long> findUserRoleIdByUserId(Long userId);

    int deleteById(Long id);

    List<Long> findRoleIdListByUserId(Long userId);

    // 批量删除
    int batchDelete(List userRolesIdList);

    // 批量插入
    int batchInsert(List userRolesList);

}