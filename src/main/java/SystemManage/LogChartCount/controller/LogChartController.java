package SystemManage.LogChartCount.controller;

import SystemManage.Common.aop.SysLog;
import SystemManage.Common.controller.BaseController;
import SystemManage.Common.entity.Result;
import SystemManage.LogChartCount.service.LogChartService;
import SystemManage.LogManage.entity.LogVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("logChart")
public class LogChartController extends BaseController{

    @Autowired
    private LogChartService logChartService ;

    /**
     * 用户列表
     * @return
     */
    @SysLog(value = "日志图表信息查看")
    @RequiresPermissions("logChart:list")
    @RequestMapping("/manager")
    public String  manage(){
        return "logChart/logChart";
    }

    /**
     * 用户管理列表
     *
     * @param logVo
     * @return
     */
    @RequiresPermissions("logChart:list")
    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public Result data(LogVo logVo) {
//        System.out.printf("得到的值是: "+ logVo.getTime());
//        System.out.println("得到的时间是 : "+ logVo.getCreatedateStart());
        Result result =new Result() ;
        // 查询的参数列表
        Map<String, Object> condition = new HashMap<String, Object>();
        if (logVo.getTime().equals("day")){
            System.out.println("查询日数据");
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM-dd");
            condition.put("startTime" , dateFormat.format(logVo.getCreatedateStart()));
            result.setObj(logChartService.findDayLogCount(condition));
        }else if (logVo.getTime().equals("month")){
            System.out.println("查询月数据");
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY-MM");
            condition.put("startTime" , dateFormat.format(logVo.getCreatedateStart()));
            result.setObj(logChartService.findMonthLogCount(condition));
        }else if (logVo.getTime().equals("year")){
            System.out.println("查询年数据");
            SimpleDateFormat dateFormat=new SimpleDateFormat("YYYY");
            condition.put("startTime" , dateFormat.format(logVo.getCreatedateStart()));
            result.setObj(logChartService.findYearLogCount(condition));
        }
        return result;
    }
}
