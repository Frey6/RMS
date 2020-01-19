package SystemManage.LogManage.service;

import SystemManage.Common.until.PageInfo;
import SystemManage.LogManage.dao.LogDao;
import SystemManage.LogManage.entity.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    @Autowired
    private LogDao logDao;

    public void insertLog(Log sysLog) {
        logDao.insert(sysLog);
    }

    public void findDataGrid(PageInfo pageInfo) {
        pageInfo.setRows(logDao.findDataGrid(pageInfo));
        pageInfo.setTotal(logDao.findDataGridCount(pageInfo));
    }

    public void batchDelete(List ids){
        logDao.batchDelete(ids) ;
    }

    public Log selectById(Long id){
        return logDao.selectById(id) ;
    }

    public int delByDate(String date){
        int count = logDao.delLogCount(date);
        logDao.delByDate(date);
        return count ;
    }

}
