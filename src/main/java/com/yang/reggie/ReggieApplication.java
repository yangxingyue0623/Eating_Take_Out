package com.yang.reggie;
import com.yang.reggie.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Slf4j
@SpringBootApplication
@MapperScan("com.yang.reggie.mapper")
@ServletComponentScan //@ServletComponentScan(basePackages = "com.atguigu.admin")
// :指定原生Servlet组件都放在那里
@EnableTransactionManagement
@EnableCaching
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功");
        System.out.println(Employee.class+"1");

    }
}
