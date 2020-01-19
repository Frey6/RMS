package SystemManage.Common.datasource;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * author yongfeng.L
 *
 * description:
 *    本类中写出了两个实现多数据源的方法
 *   通俗一点的来讲
 *   1. 第一种方法 通过定义切点的方式实现,
 *      好处在于不是每个方法都会拦截, 只有你加了注解(@dataSource)的才会拦截
 *   2. 第二种方法是在applicationContext-mybatis中配置拦截, 它会拦截每个service中方法
 *
 *   两种方法都能实现相同的功能
 *   之所以选择第二种方法方法是因为 事务与多数据源之间的关系
 *      多数据源的切换要在事务起作用之前完成
 *   多数据源的切换最好在service中完成 因为service才是用来做业务的
 *
 *   如果看不懂的 请略过这段话  因为我也觉得讲的不会很好 (附上一个笑哭的表情)
 */

//@Aspect
@Component
public class DataSourceAspect implements MethodBeforeAdvice, AfterReturningAdvice {
	//配置接入点
//    @Pointcut("@annotation(SystemManage.Common.datasource.DataSource)")
//    private void controllerAspect(){}   //定义一个切入点


//    @Before("controllerAspect()")
//    public void dataSwitch(JoinPoint joinPoint){
//        Signature signature = joinPoint.getSignature();
//        MethodSignature methodSignature =(MethodSignature) signature;
//        Method method = methodSignature.getMethod();
//        DataSource data = null;
//        String dataSource = "";
//        if(method != null){
//            data = method.getAnnotation(DataSource.class);
//            if(data != null){
//                dataSource = data.dataSource();
//                if(dataSource != null){
//                    DynamicDataSource.setDataSourceKey(dataSource);
//                }
//            }
//        }
//    }

    // 清理数据源
//    @After("controllerAspect()")
//    public void clearDataSource(){
//        DynamicDataSource.clearDataSource();
//    }

    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        DynamicDataSource.clearDataSource();
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
//        System.out.println("执行");
        DataSource data = method.getAnnotation(DataSource.class);
        if(data != null){
            String dataSource = data.dataSource();
            if(dataSource != null){
                DynamicDataSource.setDataSourceKey(dataSource);
            }
        }
    }
}
