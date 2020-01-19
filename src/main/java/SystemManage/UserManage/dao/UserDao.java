package SystemManage.UserManage.dao;

import SystemManage.UserManage.entity.UserVo;
import SystemManage.Common.until.PageInfo;
import SystemManage.UserManage.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {

    /**
     * 根据用户id查询用户
     * @param id
     * @return
     */
    User findUserById(Long id);

    /**
     * 用户列表
     */
    List findUserPageCondition(PageInfo pageInfo);

    /**
     * 统计用户
     */
    int findUserPageCount(PageInfo pageInfo);

    /**
     * 根据用户查找 Id
     */
    UserVo findUserVoById(Long id);

    /**
     * 修改用户
     *
     * @param user
     * @return
     */
    int updateUser(User user);

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    User findUserByLoginName(String username);

    /**
     * 通过 id 删除用户
     */
    int deleteUserById(Long id) ;

    /**
     * 添加用户
     */
    int insert(User user) ;

    /**
     * 修改用户密码
     * @param userId
     * @param pwd
     */
    void updateUserPwdById(@Param("userId") Long userId, @Param("pwd") String pwd) ;

    /**
     * 编辑个人资料
     * @param user
     * @return
     */
    int updatePersonFile(User user);
}
