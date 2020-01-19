package com.yongfeng.L;

import SystemManage.Common.until.DateUtil;
import SystemManage.Common.until.GlobalUtil;
import SystemManage.Common.until.MyUtil;
import SystemManage.ResourceManage.service.ResourceService;
import SystemManage.UserManage.controller.UserController;
import SystemManage.UserManage.entity.User;
import SystemManage.UserManage.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:spring/applicationContext-*.xml"})
@WebAppConfiguration("src/main/resources")
// 如果这里加了事务那执行测试方法的时候
//             DataSourceTransactionManager(数据源事务管理器) 会先绑定默认的数据源,
//  那数据源的切换就没用了   所以这里要注释
//@Transactional
public class MD5 {

    @Autowired
    private UserService userService ;

    @Autowired
    private ResourceService resourceService ;

    @Test
    public void index1(){
        Md5Hash md5_1 = new Md5Hash("123");
        System.out.println(md5_1);
        Md5Hash md5_2 = new Md5Hash("123", "admin");
        System.out.println(md5_2);
        Md5Hash md5_3 = new Md5Hash("123", "admin", 1024);
        System.out.println(md5_3);
    }


    // 花里胡哨
    @Test
    public void index2(){
        System.out.println("得到的UUID是: " + MyUtil.getStrUUID());
    }

    // 获取当前时间
    @Test
    public void index3(){
        System.out.println( "当前时间为: " + MyUtil.getNowDateStr2() ) ;
    }

    // 10天前的时间为
    @Test
    public void index4(){
        int logDays = Integer.valueOf(GlobalUtil.getValue("log.days"));
        Date date = DateUtil.getDate(DateUtil.getDate(), -logDays);
        System.out.println("10天前的时间为 : " + date );
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(date);
        System.out.println("转换之后的时间为 : " + str );

    }

    UserController s  ;

    // 测试多数据源   测试方法:  在service相应方法中加上 @dataSource 注解
    @Test
    public void index5(){
        Long value = new Long(14);
        User user = userService.findUserById( value );
//        User user1 = userService.findUserByLoginName("test");
        System.out.println("得到的值是 : "+ user);
    }
    
}
