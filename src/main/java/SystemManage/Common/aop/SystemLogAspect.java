package SystemManage.Common.aop;

import SystemManage.Common.until.MyUtil;
import SystemManage.Common.until.WebUtils;
import SystemManage.LogManage.entity.Log;
import SystemManage.LogManage.service.LogService;
import SystemManage.UserManage.entity.ShiroUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

/**
 * 切点类
 */
@Aspect
@Component
public  class SystemLogAspect {
    //注入Service用于把日志保存数据库
    @Resource
    private LogService logService;
    //本地异常日志记录对象
    private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);

    //Controller层 切点
    @Pointcut("@annotation(SystemManage.Common.aop.SysLog)")
    public  void controllerAspect() { }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
//    ProceedingJoinPoint
    @Before("controllerAspect()")
     public  void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        HttpSession session = request.getSession();
        // 请求用户名 (读取shiro中保存的用户信息)
        ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
        // 请求类
        String strClassName = joinPoint.getTarget().getClass().getName();
        // 请求地址
        String requestURI=request.getRequestURI();
        // 请求参数
        StringBuffer bfParams = new StringBuffer();
        Object[] params = joinPoint.getArgs();
        Enumeration<String> paraNames = null;
        if (params != null && params.length > 0) {
            paraNames = request.getParameterNames();
            String key;
            String value;
            while (paraNames.hasMoreElements()) {
                key = paraNames.nextElement();
                value = request.getParameter(key);
                bfParams.append(key).append("=").append(value).append("&");
            }
            if (StringUtils.isBlank(bfParams)) {
                bfParams.append(request.getQueryString());
            }
        }
        // 请求的IP
        String ip= WebUtils.getRemoteAddr(request);
         try {
            // System.out.println("=====前置通知开始=====");
            // 获取操作名称
            String operation=getControllerMethodDescription(joinPoint);
            // 获取登录用户名
            String loginName=user.loginName;

            Log log=new Log();
            log.setLoginName(loginName);
            log.setOperationName(operation);
            log.setOperationClass(strClassName);
            log.setOperationAddress(requestURI);
            log.setParams(bfParams.toString());
            log.setIp(ip);
            log.setCreateTime(MyUtil.getNowDateStr2());
			//*========保存数据库日志=========*//
            //System.out.println(log);
            // 保存数据库
             System.out.println("执行保存操作前");
             logService.insertLog(log);
             System.out.println("执行保存操作后");
        }  catch (Exception e) {
            //记录本地异常日志
            logger.error("==前置通知异常==");
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
         for (Method method : methods) {
             if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                 if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SysLog.class).value();
                    break;
                }
            }
        }
         return description;
    }
}