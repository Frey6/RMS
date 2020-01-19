package SystemManage.LogChartCount.dao;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface LogChartDao {

    int findLogSum(Map values);
    /**
     * 查询 "天" 日志数量 (即当天每小时的日志数量)
     * @param values
     * @return
     */
    List<Map<String,Object>> findDayLogCount(Map values);

    /**
     * 查询 "月" 日志数量 (即当月每天的日志数量)
     * @param values
     * @return
     */
    List<Map<String,Object>> findMonthLogCount(Map values);

    /**
     * 查询 "年" 日志数量 (即当年每月的日志数量)
     * @param values
     * @return
     */
    List<Map<String,Object>> findYearLogCount(Map values);

}
