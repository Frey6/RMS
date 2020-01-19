package SystemManage.LogChartCount.service;

import SystemManage.LogChartCount.dao.LogChartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogChartService {

    @Autowired
    private LogChartDao logChartDao;

    /**
     * 查询每天的日志数量
     *
     * @return
     */
    public Map findDayLogCount(Map condition){

        List<Map<String,Object>> dayData =  logChartDao.findDayLogCount(condition);
        int daySum = logChartDao.findLogSum(condition);
        Map map = new HashMap();
        // 每小时日志数量
        int[] ydata1 = new int[24];
        // 每小时日志数量所占改天日志总数量比例
        float[] ydata2 = new float[24];

        DecimalFormat df = new DecimalFormat("0.00");
        for (int i=0 ; i<dayData.size(); i++){
            ydata1[(int)dayData.get(i).get("hour")] =
                    Integer.parseInt(String.valueOf(dayData.get(i).get("count"))) ;
            //这步首先将Object转化成String类型 然后将String转换为Float,然后相除后 通过DecimalFormat保留两位小数
            //因为它是返回的String 所以再转回 float类型
            ydata2[(int)dayData.get(i).get("hour")] =
                        Float.parseFloat(df.format(Float.parseFloat(String.valueOf(dayData.get(i).get("count"))) / daySum * 100));
        }
        String[] xdata={"00","01","02","03","04","05","06","07","08",
                "09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
        map.put("xdata" , xdata);
        map.put("ydata1" , ydata1);
        map.put("ydata2" , ydata2);
        return map;
    }

    /**
     * 查询每月的日志数量
     *
     * @return
     */
    public Map findMonthLogCount(Map condition){

        int DaysOfMonth = getDaysOfCurMonth(String.valueOf(condition.get("startTime")));
        List<Map<String,Object>> monthData =  logChartDao.findMonthLogCount(condition);
        int daySum = logChartDao.findLogSum(condition);
        Map map = new HashMap();
        // 每天日志数量
        int[] ydata1 = new int[DaysOfMonth];
        // 每天日志数量所占改月日志总数量比例
        float[] ydata2 = new float[DaysOfMonth];

        DecimalFormat df = new DecimalFormat("0.00");
        for (int i=0 ; i<monthData.size(); i++){
            ydata1[(int)monthData.get(i).get("day")-1] =
                    Integer.parseInt(String.valueOf(monthData.get(i).get("count"))) ;
            //这步首先将Object转化成String类型 然后将String转换为Float,然后相除后 通过DecimalFormat保留两位小数
            //因为它是返回的String 所以再转回 float类型
            ydata2[(int)monthData.get(i).get("day")-1] =
                    Float.parseFloat(df.format(Float.parseFloat(String.valueOf(monthData.get(i).get("count"))) / daySum * 100));
        }

        String[] dates = String.valueOf(condition.get("startTime")).split("-");
        String month = dates[1];

        String[] xdata = new String[DaysOfMonth];
        for (int i = 0; i < xdata.length; i++) {
            if (i < 9) xdata[i] = month+"-"+"0"+(i+1) ;
            else xdata[i] = month+"-"+(i+1) ;
        }
//        System.out.println("得到的值是: "+ xdata.toString());
        map.put("xdata" , xdata);
        map.put("ydata1" , ydata1);
        map.put("ydata2" , ydata2);
        return map;
    }

    /**
     * 查询每年的日志数量
     *
     * @return
     */
    public Map findYearLogCount(Map condition){

        List<Map<String,Object>> yearData =  logChartDao.findYearLogCount(condition);
        int daySum = logChartDao.findLogSum(condition);
        Map map = new HashMap();
        // 每月日志数量
        int[] ydata1 = new int[12];
        // 每月日志数量所占改年日志总数量比例
        float[] ydata2 = new float[12];

        DecimalFormat df = new DecimalFormat("0.00");
        for (int i=0 ; i<yearData.size(); i++){
            ydata1[(int)yearData.get(i).get("month")-1] =
                    Integer.parseInt(String.valueOf(yearData.get(i).get("count"))) ;
            //这步首先将Object转化成String类型 然后将String转换为Float,然后相除后 通过DecimalFormat保留两位小数
            //因为它是返回的String 所以再转回 float类型
            ydata2[(int)yearData.get(i).get("month")-1] =
                    Float.parseFloat(df.format(Float.parseFloat(String.valueOf(yearData.get(i).get("count"))) / daySum * 100));
        }
        String[] xdata={"01","02","03","04","05","06","07","08",
                "09","10","11","12"};
        map.put("xdata" , xdata);
        map.put("ydata1" , ydata1);
        map.put("ydata2" , ydata2);
        return map;
    }



    /**
     * 工具类 util
     * 根据指定的年月 返回指定月份（yyyy-MM）有多少天。
     *
     * @param time
     *            yyyy-MM
     * @return 天数，指定月份的天数。
     */
    public static int getDaysOfCurMonth(final String time) {
        if (time.length() != 7) {
            throw new NullPointerException("参数的格式必须是yyyy-MM");
        }
        String[] timeArray = time.split("-");
        int curYear = new Integer(timeArray[0]).intValue(); // 当前年份
        int curMonth = new Integer(timeArray[1]).intValue();// 当前月份
        if (curMonth > 12) {
            throw new NullPointerException("参数的格式必须是yyyy-MM，而且月份必须小于等于12。");
        }
        int mArray[] = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
        // 判断闰年的情况 ，2月份有29天；
        if ((curYear % 400 == 0) || ((curYear % 100 != 0) && (curYear % 4 == 0))) {
            mArray[1] = 29;
        }
        if (curMonth == 12) {
            return mArray[0];
        }
        return mArray[curMonth - 1];
        // 如果要返回下个月的天数，注意处理月份12的情况，防止数组越界；
        // 如果要返回上个月的天数，注意处理月份1的情况，防止数组越界；
    }
}
