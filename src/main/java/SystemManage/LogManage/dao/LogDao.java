package SystemManage.LogManage.dao;

import SystemManage.Common.until.PageInfo;
import SystemManage.LogManage.entity.Log;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LogDao {

    int insert(Log record);

    Log selectById(Long id);

    List findDataGrid(PageInfo pageInfo);

    int findDataGridCount(PageInfo pageInfo);

    int batchDelete(List ids);

    void delByDate(String date);

    int delLogCount(String date);

}
