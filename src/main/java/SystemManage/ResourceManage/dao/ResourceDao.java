package SystemManage.ResourceManage.dao;

import SystemManage.ResourceManage.entity.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResourceDao {

    /**
     * 查询一级资源
     *
     * @param resourceMenu
     * @return
     */
    List<Resource> findResourceAllByTypeAndPidNull(Integer resourceMenu);

    /**
     * 查询菜单资源
     *
     * @param resourceType
     * @param pid
     * @return
     */
    List<Resource> findResourceAllByTypeAndPid(@Param("resourceType") Integer resourceType, @Param("pid") Long pid);

    /**
     * 查找所有资源
     * @return
     */
    List<Resource> findResourceAll();

    /**
     * 根据id查询资源
     *
     * @param id
     * @return
     */
    Resource findResourceById(Long id);

    /**
     * 更新资源
     * @param resource
     */
    int updateResource(Resource resource);

    /**
     * 根据 id 删除资源
     */
    int deleteResourceById(Long id);

    /**
     * 添加资源
     * @param resource
     */
    void insert(Resource resource);

}
