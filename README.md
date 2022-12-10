Eating外卖

[toc]

# 项目的最终部署

项目的部署架构

![image-20210901221425159](https://img-blog.csdnimg.cn/img_convert/18cb4a8eab7562dc09d2f89735202999.png)

> 我用了虚拟机A（192.168.8.131）虚拟机B（192.168.8.130）
>
> 虚拟机A （nginx前端部署，主库）
>
> 虚拟机B（后端项目部署，从库，redis缓存）



# Github:[源码地址](https://github.com/yangxingyue0623/Eating_Take_Out)

# gitee :[源码地址](https://gitee.com/yangxingyue0623/reggie_take_out.git)

## 1.项目介绍

> - 本项目Eatting外卖是专门为餐饮企业（餐厅、饭店）定制的一款软件产品，包括 系统管理后台 和 移动端应用 两部分。
>
> - 系统管理后台主要提供给餐饮企业内部员工使用，可以对餐厅的分类、菜品、套餐、订单、员工等进行管理维护。
>
> - 移动端应用主要提供给消费者使用，可以在线浏览菜品、添加购物车、下单等。

### 1.1 管理端

> 餐饮企业内部员工使用。 主要功能有:

| 模块      | 描述                                                         |
| --------- | :----------------------------------------------------------- |
| 登录/退出 | 内部员工必须登录后,才可以访问系统管理后台                    |
| 员工管理  | 管理员可以在系统后台对员工信息进行管理，包含查询、新增、编辑、禁用等功能 |
| 分类管理  | 主要对当前餐厅经营的 菜品分类 或 套餐分类 进行管理维护， 包含查询、新增、修改、删除等功能 |
| 菜品管理  | 主要维护各个分类下的菜品信息，包含查询、新增、修改、删除、启售、停售等功能 |
| 套餐管理  | 主要维护当前餐厅中的套餐信息，包含查询、新增、修改、删除、启售、停售等功能 |
| 订单明细  | 主要维护用户在移动端下的订单信息，包含查询、取消、派送、完成，以及订单报表下载等功能 |

### 1.2 移动端

> 移动端应用主要提供给消费者使用。主要功能有:

| 模块        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| 登录/退出   | 在移动端, 用户也需要登录后使用APP进行点餐                    |
| 个人信息    | 在个人中心页面中会展示当前用户的基本信息, 用户可以管理收货地址, 也可以查询历史订单数据 |
| 点餐-购物车 | 用户选中的菜品就会加入用户的购物车, 主要包含 查询购物车、加入购物车、删除购物车、清空购物车等功能 |
| 订单支付    | 用户选完菜品/套餐后, 可以对购物车菜品进行结算支付, 这时就需要进行订单的支付 |
| 点餐-菜单   | 在点餐界面需要展示出菜品分类/套餐分类, 并根据当前选择的分类加载其中的菜品信息, 供用户查询选择 |

## 2.项目技术栈

> 关于本项目的技术选型, 我们将会从 用户层、网关层、应用层、数据层 这几个方面进行介绍，而对于我们服务端开发工程师来说，在项目开发过程中，我们主要关注应用层及数据层技术的应用。

### 2.1用户层H5,Vue.js,ElementUI

> 本项目中在构建系统管理后台的前端页面，我们会用到H5、Vue.js、ElementUI等技术。而在构建移动端应用时，我们会使用到微信小程序。

### 2.2网关层 Nginx(部署静态资源,反向代理和负载均衡)

> Nginx是一个服务器，主要用来作为Http服务器，部署静态资源，访问性能高。在Nginx中还有两个比较重要的作用： 反向代理和负载均衡， 在进行项目部署时，要实现Tomcat的负载均衡，就可以通过Nginx来实现。

###  2.3.应用层 springboot+springmvc+springsession+lomback+swagger

- SpringBoot： 快速构建Spring项目, 采用 "约定优于配置" 的思想, 简化Spring项目的配置开发。
- Spring: 统一管理项目中的各种资源(bean), 在web开发的各层中都会用到。
- SpringMVC：SpringMVC是spring框架的一个模块，springmvc和spring无需通过中间整合层进行整合，可以无缝集成。
- SpringSession: 主要解决在集群环境下的Session共享问题。
- lombok：能以简单的注解形式来简化java代码，提高开发人员的开发效率。例如开发中经常需要写的javabean，都需要花时间去添加相应的getter/setter，也许还要去写构造器、equals等方法。
- Swagger： 可以自动的帮助开发人员生成接口文档，并对接口进行测试。

### 2.4数据层 mysql+mybatisplus+redis+springcacahe

- MySQL： 关系型数据库, 本项目的核心业务数据都会采用MySQL进行存储。

- MybatisPlus： 本项目持久层将会使用MybatisPlus来简化开发, 基本的单表增删改查直接调用框架提供的方法即可。

- Redis： 基于key-value格式存储的内存数据库, 访问速度快, 经常使用它做缓存(降低数据库访问压力, 提供访问效率), 在后面的性能优化中会使用。
- springcache:基于注解实现项目的缓存

### 2.5工具 Git+Maven+Junit+Postman+Linux

- Git: 版本控制工具, 在团队协作中, 使用该工具对项目中的代码进行管理。

- Maven: 项目构建工具。

- Junit：单元测试工具，开发人员功能实现完毕后，需要通过junit对功能进行单元测试。
- postman :用于前后端接口进行接口测试。
- Linux:利用sh脚本进行项目的自动部署

### 2.6用户角色

> 在瑞吉外卖这个项目中，存在以下三种用户，这三种用户对应三个角色： 后台系统管理员、后台系统普通员工、C端(移动端)用户。

| 角色             | 权限操作                                                     |
| ---------------- | ------------------------------------------------------------ |
| 后台系统管理员   | 登录后台管理系统，拥有后台系统中的所有操作权限               |
| 后台系统普通员工 | 登录后台管理系统，对菜品、套餐、订单等进行管理 (不包含员工管理) |
| C端用户          | 登录移动端应用，可以浏览菜品、添加购物车、设置地址、在线下单等 |

## 3.项目的依赖

### 3.1版本

- springboot 2.7.5
- maven 3.6
- idea 2020.2
- VMware Workstation Pro 15 
- xshell 7
- nginx 1.20.1
- node 12v mong 4.17

### 3.2pom.xml文件

```xml
    </dependency>
        <!-- springcache-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- web -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <scope>compile</scope>
        </dependency>
        <!--lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.24</version>
        </dependency>
        <!--fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.60</version>
        </dependency>
        <!--commons-lang工具类 -->
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
            <version>2.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>1.1.22</version>
        </dependency>
<!--        添加一个devtools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
<!--        添加myp-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>
        <!--阿里云短信服务-->
        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-core</artifactId>
            <version>4.5.16</version>
        </dependency>
        <!-- 邮件服务,用来做验证码的发送验证-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <!-- Thymeleaf 模版，用于发送模版邮件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        <dependency>
            <groupId>com.aliyun</groupId>
            <artifactId>aliyun-java-sdk-dysmsapi</artifactId>
            <version>2.1.0</version>
        </dependency>
<!--        读写分离依赖-->
<!--        <dependency>-->
<!--            <groupId>org.apache.shardingsphere</groupId>-->
<!--            <artifactId>sharding-jdbc-spring-boot-starter</artifactId>-->
<!--            <version>4.0.0-RC1</version>-->
<!--        </dependency>-->
        <!--swwager依赖-->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>2.0.4</version>
        </dependency>

        <dependency>
            <groupId>cn.hutool</groupId>
            <artifactId>hutool-all</artifactId>
            <version>5.7.18</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
            <resources>
                <resource>
                    <!-- xml放在java目录下-->
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.xml</include>
                    </includes>
                </resource>
                <!--指定资源的位置（xml放在resources下，可以不用指定）-->
                <resource>
                    <directory>src/main/resources</directory>
                </resource>
            </resources>
    </build>
</project>

   

```

### 3.3 application.yaml

```yaml
server:
  port: 8080
spring:
  application:
    name: reggie_take_out
    #应用的名称默认该项目名
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      #jdbc:mysql://192.168.8.1:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      #Url要改成自己的jdbc:mysql://localhost:3306/reggie?serverTimezone=UTC
      url: jdbc:mysql://localhost:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: root
      password: 123456
  redis:
    host: 192.168.8.130
    port: 6379
    password: xxxxxx
  cache:
    redis:
      time-to-live: 1800000 #设置缓存有效期

    lettuce:
      pool:
        max-active: 10 #最大连接数量
        max-idle: 10 #阻塞最大等待时间
        min-idle: 1
        time-between-eviction-runs: 10s
    database: 1 #操作的数据库1，这里默认是0,我的0做的其他项目缓存


  # 邮箱配置
  mail:
    host: smtp.qq.com  # 发送邮件的服务器地址
    username: xxxxxxx@qq.com # 开启 IMAP/SMTP服务 的qq邮箱的账号
    password: xxxxxxxxx # 开启 IMAP/SMTP服务 获得的授权码,而不是qq邮箱的登录密码
    default-encoding: UTF-8

mybatis-plus:
  configuration:
    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射
    #比如表user_name > 对应表userName
    map-underscore-to-camel-case: true
     #日志输出
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    #    mapper-locations: classpath:mybatis/mapper/*.xml
    db-config:
      id-type: ASSIGN_ID #该配置完成后，每个模型类的主键ID策略都将成为assign_id

#设置一个外部配置源，输出的地址
reggie:
  path: D:/img/
#  path: /usr/local/img/
```

```yaml
server:
  port: 8081
spring:
  application:
    name: reggie_take_out
    #应用的名称默认该项目名
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      #jdbc:mysql://192.168.8.1:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      #Url要改成自己的jdbc:mysql://localhost:3306/reggie?serverTimezone=UTC
      url: jdbc:mysql://localhost:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: root
      password: 123456
  redis:
    host: 192.168.8.130
    port: 6379
    password: yangroot
  cache:
    redis:
      time-to-live: 1800000 #设置缓存有效期

    lettuce:
      pool:
        max-active: 10 #最大连接数量
        max-idle: 10 #阻塞最大等待时间
        min-idle: 1
        time-between-eviction-runs: 10s
    database: 1 #操作的数据库1，这里默认是0,我的0做的其他项目缓存


  # 邮箱配置
  mail:
    host: smtp.qq.com  # 发送邮件的服务器地址
    username: 1573478674@qq.com # 开启 IMAP/SMTP服务 的qq邮箱的账号
    password: aibzoigxgvfphjjg # 开启 IMAP/SMTP服务 获得的授权码,而不是qq邮箱的登录密码
    default-encoding: UTF-8

mybatis-plus:
  configuration:
    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射
    #比如表user_name > 对应表userName
    map-underscore-to-camel-case: true
    #日志输出
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    #    mapper-locations: classpath:mybatis/mapper/*.xml
    db-config:
      id-type: ASSIGN_ID #该配置完成后，每个模型类的主键ID策略都将成为assign_id

#设置一个外部配置源，输出的地址
reggie:
  path: D:/img/
#  path: /usr/local/img/
```

## 4.代码实现

### 4.1启动类

```java
package com.yang.reggie;
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
@ServletComponentScan //@ServletComponentScan(basePackages = "com.admin")
// :指定原生Servlet组件都放在那里
@EnableCaching
@EnableTransactionManagement
public class XingyueApplication {
    public static void main(String[] args) {
        SpringApplication.run(XingyueApplication.class,args);
        log.info("项目启动成功");
    }
}
```

### 4.2 filter

```java

@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
//拦截所有
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher pathMatcher=new AntPathMatcher();
    //路径匹配器，通配符
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response=(HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        //1.获得本次访问的uri
        String requestURI = request.getRequestURI();
        log.info("拦截请求为：{}",requestURI);
        //2.定义不需要过滤的。注意/backend/**不能解决index.html所以需要路径通配符
        String [] urls=new String[]{
             "/employee/login" ,
                "/employee/logout" ,
                "/backend/**" ,
                "/front/**" ,
                "/common/**",
                "/user/sendMsg", //移动端发送短信
                "/user/login" , // 移动端登陆
                "/doc.html",
                "/webjars/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        //3.判断本次请求是否拦截
        boolean check = check(urls, requestURI);
        if (check){
            log.info("本次请求不处理");
            filterChain.doFilter(request,response);
            return;//不拦截
        }
        //4-1判断登录状态，如果已经登录直接放行
        if(request.getSession().getAttribute("employee")!=null){
           Long empId =(Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            log.info("登录用户id为: {}",request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;//不拦截
        }
        //4-2判断登录状态，如果已经登录直接放行
        if(request.getSession().getAttribute("user")!=null){
            Long userId =(Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            log.info("登录用户id为: {}",request.getSession().getAttribute("user"));
            filterChain.doFilter(request,response);
            return;//不拦截
        }


        //5.如果没有登录，需要结合前端看。
        //通过输出流的方式向前端响应数据
        //(写json数据给前端）
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        log.info("用户未登录");
       return;
    }

    /**
     * 路径匹配判断放行
     * @param urls
     * @param requstURI
     * @return
     */
    public boolean check(String[] urls,String requstURI){
        for (String url : urls) {
            boolean match = pathMatcher.match(url, requstURI);
            //后者是否和前者匹配
            if (match){
                return true;
            }
        }
        return false;
    }
}
```

### 4.3 common包

#### 4.3.1  Base Context基于用户的线程封装类

```java
ackage com.yang.reggie.com;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
```

#### 4.3.2自定义业务异常类 Custom Exception

```java

 
/*
* 自定义业务异常
* 继承了运行时异常
* 1.查询当前分类是否关联了套餐或者菜品，如果已经关联不允许删除，抛出这个业务异常
*   该异常将在全局异常处理器中被捕获，并获取它携带的的信息
* */
public class CustomException extends RuntimeException{
    public CustomException(String message){
        super(message);
    }
}
```

#### 4.3.3全局异常处理类 GlobalException Handler

```java
/*
* ControllerAdvice这个代表拦截所有带RestController和Controller的类
* ResponseBody将结果封装成json数据并返回
* */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
 
    /**
     * 异常处理方法
     * 目的：禁止重复添加具有唯一约束字段的数据(如：相同username的员工用户，相同sort的菜品等)
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        //如果异常信息包括这个关键字，说明了已经违反了username的唯一约束
        //IDEA抛出异常信息例子：Duplicate entry 'zhangsan' for key 'employee.idx_username'
        if(ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已存在";
            return R.error(msg);
        }
        //如果产生其他异常信息，则输出"未知错误"
        return R.error("未知错误");
    }
 
    /**
     * 异常处理方法
     * 这是自己写的异常，捕获这个异常，并收到它携带的信息
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}

```

#### 4.3.4 自定义结果返回类R

> 设计表现层返回结果的模型类，用于后端与前端进行数据格式统一，也称为**前后端数据协议** 

```java
@Data
public class R<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
```

#### 4.3.5自定义元数据对象处理器类MyMetaObjectHandler

```java

/**
 * 自定义元素据对象处理器
 */
@Component
@Slf4j
public class MyMetaObjecthandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充【insert]");
        log.info(metaObject.toString());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
       metaObject.setValue("createUser",BaseContext.getCurrentId());
     metaObject.setValue("updateUser",BaseContext.getCurrentId());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充【update]");
        log.info(metaObject.toString());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
    }
}
```

### 4.4config包

#### 4.4.1对象映射器JacksonObjectMapper

```java
/**
 * 对象映射器:基于jackson将Java对象转为json，或者将json转为Java对象
 * 将JSON解析为Java对象的过程称为 [JSON反序列化Java对象]
 * 从Java对象生成JSON的过程称为 [序列化Java对象到JSON]
 ** 解决问题：1.雪花算法生成的id过长，js处理时丢失精度，导致id和数据库中的不匹配，从而更新失败问题
 * * 2.改变时间的格式等
 */
public class JacksonObjectMapper extends ObjectMapper {

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public JacksonObjectMapper() {
        super();
        //收到未知属性时不报异常
        this.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

        //反序列化时，属性不存在的兼容处理
        this.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);


        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)))

                .addSerializer(BigInteger.class, ToStringSerializer.instance)
                .addSerializer(Long.class, ToStringSerializer.instance)
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT)))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT)));

        //注册功能模块 例如，可以添加自定义序列化器和反序列化器
        this.registerModule(simpleModule);
    }
}

```

#### 4.4.2配置Mybatis-plus的分页插件MybatisPlusConfig

```java
@Configuration
public class MybatisPliusConfig {
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
```

#### 4.4.3静态资源映射WebMvcConfig

```java
@Component
@Slf4j
@EnableSwagger2
@EnableKnife4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /**
     * 设置静态资源映射
     * 设置静态资源映射
     * 使启动项目后可以访问到HTML等静态资源
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("/backend/**")
                .addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**")
                .addResourceLocations("classpath:/front/");
        //当访问的url含/backend/开头的时候去classpath:/backend/找
        log.info("进行静态资源映射");
    }

    @Override //扩展mvc框架的转换器
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
       log.info("扩展消息转换器");
        MappingJackson2HttpMessageConverter messageConverter
                =new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用json将java对象转成json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器追加到mvc框架的转换器容器集合中，主要要放在前面所以加0
        converters.add(0,messageConverter);
    }


    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yang.reggie.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("星月外卖")
                .version("1.0")
                .description("星月外卖接口文档")
                .build();
    }

}
```

#### 4.4.4RedisConfig

```java
@Configuration
public class RedisConfig extends CachingConfigurerSupport {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String,Object>template=new RedisTemplate<>();
        //关联
        template.setConnectionFactory(factory);
        //设置key的序列化器
        template.setKeySerializer(new StringRedisSerializer());
        //设置value的序列化器
         template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }
}
```

### 4.5Utils包

#### 4.5.1RedisConstans

```java
/**
 * @author yangxingyue0623
 * @date 2022/12/7 23:12
 */
public class RedisConstans {
    public static final String LOGIN_CODE_KEY = "login:code:";
}

```

#### 4.5.2 SMSUtils

```java
/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param){
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
		IAcsClient client = new DefaultAcsClient(profile);

		SendSmsRequest request = new SendSmsRequest();
		request.setSysRegionId("cn-hangzhou");
		request.setPhoneNumbers(phoneNumbers);
		request.setSignName(signName);
		request.setTemplateCode(templateCode);
		request.setTemplateParam("{\"code\":\""+param+"\"}");
		try {
			SendSmsResponse response = client.getAcsResponse(request);
			System.out.println("短信发送成功");
		}catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
```

#### 4.5.3 ValidateCodeUtils

```java

/**
 * 随机生成验证码工具类
 */
public class ValidateCodeUtils {
    /**
     * 随机生成验证码
     * @param length 长度为4位或者6位
     * @return
     */
    public static Integer generateValidateCode(int length){
        Integer code =null;
        if(length == 4){
            code = new Random().nextInt(9999);//生成随机数，最大为9999
            if(code < 1000){
                code = code + 1000;//保证随机数为4位数字
            }
        }else if(length == 6){
            code = new Random().nextInt(999999);//生成随机数，最大为999999
            if(code < 100000){
                code = code + 100000;//保证随机数为6位数字
            }
        }else{
            throw new RuntimeException("只能生成4位或6位数字验证码");
        }
        return code;
    }

    /**
     * 随机生成指定长度字符串验证码
     * @param length 长度
     * @return
     */
    public static String generateValidateCode4String(int length){
        Random rdm = new Random();
        String hash1 = Integer.toHexString(rdm.nextInt());
        String capstr = hash1.substring(0, length);
        return capstr;
    }
}

```

## 5.entity包,DTO包

```java
/**
 菜品
 */
@Data
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;


    //菜品名称
    private String name;


    //菜品分类id
    private Long categoryId;


    //菜品价格
    private BigDecimal price;


    //商品码
    private String code;


    //图片
    private String image;


    //描述信息
    private String description;


    //0 停售 1 起售
    private Integer status;


    //顺序
    private Integer sort;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;


    @TableField(fill = FieldFill.INSERT)
    private Long createUser;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;


    //是否删除
    @TableLogic
    private Integer isDeleted;

}
其余略
```

> 此时传输的参数，需要封装一个新的对象进行接收Data Transfer Object 数据传输对象，一般用于展示层与层之间的数据传输
>
> 前端回传的json数据需要RequestBody反序列化到我们定义的实体类中

| 实体模型 | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| DTO      | Data Transfer Object(数据传输对象)，一般用于展示层与服务层之间的数据传输。 |
| Entity   | 最常用实体类，基本和数据表一一对应，一个实体类对应一张表。   |
| VO       | Value Object(值对象), 主要用于封装前端页面展示的数据对象，用一个VO对象来封装整个页面展示所需要的对象数据 |
| PO       | Persistant Object(持久层对象), 是ORM(Objevt Relational Mapping)框架中Entity，PO属性和数据库中表的字段形成一一对应关系 |
```java
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}

/**
 * @author yangxingyue0623
 * @date 2022/12/7 18:14
 */
@Data
public class OrderDto extends Orders {
    private List<OrderDetail> orderDetails;
}

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}

```

## 6.controller,Mapper,Service

- 1.首先在Application.class开启注解@MapperScan("com.yang.reggie.mapper")
- 结构类似，省略

```java
# controller
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
}
# Mapper
@Mapper
public interface UserMapper extends BaseMapper<User>{
}
# service包下
public interface UserService extends IService<User> {

}
#service层
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> 
    implements UserService {
}
```

## 7.移动端：验证码发送登录/redis缓存/邮件发送/退出

#### 1.依赖注入

```java
        <!-- 邮件服务,用来做验证码的发送验证-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-mail</artifactId>
        </dependency>
        <!-- Thymeleaf 模版，用于发送模版邮件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
  <!-- redis -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

#### 2.利用QQ邮箱开启Foxmail功能实现邮件的代发

![img](https://img-blog.csdnimg.cn/4e9dea07ebbe49f48cf0846c25172e54.png)

#### 3.配置yaml的邮箱

```yaml
# 邮箱配置
  mail:
    host: smtp.qq.com  # 发送邮件的服务器地址
    username: 1573478674@qq.com # 开启 IMAP/SMTP服务 的qq邮箱的账号
    password: aibzoigxgvfphjjg # 开启 IMAP/SMTP服务 获得的授权码,而不是qq邮箱的登录密码
    default-encoding: UTF-8
 #redis配置
  redis:
    host: 192.168.8.130
    port: 6379
    password: yangroot
```

#### 4 修改前端的代码

```java
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=no,minimal-ui">
    <title>菩提阁</title>
    <link rel="icon" href="./../images/favico.ico">
    <!--不同屏幕尺寸根字体设置-->
    <script src="./../js/base.js"></script>
    <!--element-ui的样式-->
    <link rel="stylesheet" href="../../backend/plugins/element-ui/index.css"/>
    <!--引入vant样式-->
    <link rel="stylesheet" href="../styles/vant.min.css"/>
    <!-- 引入样式  -->
    <link rel="stylesheet" href="../styles/index.css"/>
    <!--本页面内容的样式-->
    <link rel="stylesheet" href="./../styles/login.css"/>
</head>
<body>
<div id="login" v-loading="loading">
    <div class="divHead">登录</div>
    <div class="divContainer">
        <el-input placeholder=" 请输入邮箱" v-model="form.phone" maxlength='20'/>
        </el-input>
        <div class="divSplit"></div>
        <el-input placeholder=" 请输入验证码" v-model="form.code" maxlength='20'/>
        </el-input>
        <span v-if="second > 0">{{second}}s</span>
        <span v-if="second <= 0" @click='getCode'>获取验证码</span>
    </div>
    <div class="divMsg" v-if="msgFlag">邮箱格式不正确，请重新输入</div>
    <el-button type="primary" :class="{btnSubmit:1===1,btnNoPhone:!form.phone,btnPhone:form.phone}" @click="btnLogin">
        登录
    </el-button>
</div>
<!-- 开发环境版本，包含了有帮助的命令行警告 -->
<script src="../../backend/plugins/vue/vue.js"></script>
<!-- 引入组件库 -->
<script src="../../backend/plugins/element-ui/index.js"></script>
<!-- 引入vant样式 -->
<script src="./../js/vant.min.js"></script>
<!-- 引入axios -->
<script src="../../backend/plugins/axios/axios.min.js"></script>
<script src="./../js/request.js"></script>
<script src="./../api/login.js"></script>
</body>
<script>
    new Vue({
        el: "#login",
        data() {
            return {
                form: {
                    phone: '',
                    code: ''
                },
                // 倒计时
                second: -1,
                // 判断邮箱格式
                msgFlag: false,
                // 是否登陆中
                loading: false,
                // 是否可以发送验证码
                sending: true,
                // 倒计时定时任务引用 关闭登录层清除定时任务
                clearSmsTime: null
            }
        },
        computed: {},
        created() {
        },
        mounted() {
        },
        methods: {
            getCode() {
                this.form.code = ''
                // 手机号验证
                // const regex = /^(13[0-9]{9})|(15[0-9]{9})|(17[0-9]{9})|(18[0-9]{9})|(19[0-9]{9})$/;
                // 改为邮箱验证
                const regex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
                if (regex.test(this.form.phone)) {
                    this.msgFlag = false
                    //this.form.code = (Math.random()*1000000).toFixed(0)
                    sendMsgApi({phone: this.form.phone})
                    // 不可重复发送验证码
                    this.sending = false
                    // 倒计时效果
                    this.timeDown()
                } else {
                    this.msgFlag = true
                }
            },
            // 倒计时
            timeDown() {
                if (this.clearSmsTime) {
                    clearInterval(this.clearSmsTime)
                }
                this.second = 60
                this.clearSmsTime = setInterval(() => {
                    --this.second
                    if (this.second < 1) {
                        clearInterval(this.clearSmsTime)
                        // 可以重新发送验证码
                        this.sending = true
                        this.second = 0
                    }
                }, 1000)
            },
            async btnLogin() {
                if (this.form.phone && this.form.code) {
                    this.loading = true
                    const res = await loginApi(this.form)
                    this.loading = false
                    if (res.code === 1) {
                        sessionStorage.setItem("userPhone", this.form.phone)
                        window.requestAnimationFrame(() => {
                            window.location.href = '/front/index.html'
                        })
                    } else {
                        this.$notify({type: 'warning', message: res.msg});
                    }
                } else {
                    this.$notify({type: 'warning', message: '请输入邮箱账号'});
                }
            }
        }
    })
</script>
</html>
```

#### 5.usercontroller的sendMsg/login/loginout

```java
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        return userService.sendMsg(user,session);
    }
    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
       return userService.login(map,session);
    }
    /**
     * 退出登录
     * @param request
     * @return
     */
    @PostMapping("/loginout")
    public R<String> loginout(HttpServletRequest request){
        //清理session中的用户id
        request.getSession().removeAttribute("user");
        return R.success("退出成功");
    }
}

```

#### 6.代码实现

> 1.发送验证码 
>
> 利用邮件发送验证码，并且把验证码保存在redis里面
>
> 2.登录
>
> 把发送的验证码和redis中的验证码进行对比
>
> 如果存在且相等查询用户是否存在，不存在就创建
>
> 并且把用户存入session中
>
> 最后删除redis中的验证码
>
> 3.退出
>
> 根据session获得user，然后request.getSession().removeAttribute("user");
>
> 

```java
import static com.yang.reggie.utils.RedisConstans.LOGIN_CODE_KEY;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private RedisTemplate redisTemplate;
    //把yml配置的邮箱号赋值到from

    @Value("${spring.mail.username}")
    private String from;
    //发送邮件需要的对象
    @Resource
    private JavaMailSender javaMailSender;
    //邮件发送人

    public void sendMsg1(String to, String subject, String text) {
        //发送简单邮件，简单邮件不包括附件等别的
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        javaMailSender.send(message);
    }


    @Override
    public R<String> sendMsg(User user, HttpSession session) {

        //1.获取邮箱号
       String email = user.getPhone();
        String subject = "Eatting外卖";
        //2.StringUtils.isNotEmpty字符串非空判断
       if (StringUtils.isNotEmpty(email)) {
            //3.发送验证码
            String code = RandomUtil.randomNumbers(6);
           ;
           String text = "【Eatting外卖】您好，您的登录验证码为：" + code + "，请尽快登录，如非本人操作，请忽略此邮件。";
           log.info("验证码为：" + code);
           //4.发送邮件
           this.sendMsg1(email, subject, text);
           //将验证码保存到session当中
           //将邮箱作为key，将code最为value保存到session中,，因此邮箱和验证码可以一一对
          // 5.保存生成的验证码到redis,5分钟失效
           redisTemplate.opsForValue().set(LOGIN_CODE_KEY + email, code, 5, TimeUnit.MINUTES);
           return R.success("验证码发送成功");
       }
        return R.error("验证码发送异常，请重新发送");
    }

    @Override
    public R<User> login(Map map, HttpSession session) {
 

       log.info(map.toString());
       //1.获取手机号（邮箱）
       String phone = map.get("phone").toString();
       //2.获取验证码.
       String code = map.get("code").toString();
      //3.从redis中获取保存的验证码
        Object codeInSession =redisTemplate.opsForValue().get(LOGIN_CODE_KEY+phone) ;
      //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
   if(codeInSession != null && codeInSession.equals(code)){
            //4.如果能够比对成功，说明登录成功
           LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
          queryWrapper.eq(User::getPhone,phone);

           User user = this.getOne(queryWrapper);
            if(user == null){
               //5.判断当前手机号对应的用户是否为新用户，如果是新用户就自动完成注册
               user = new User();
                user.setPhone(phone);
               user.setStatus(1);
                this.save(user);
            }
            //6.登录成功之后把user写入session
           session.setAttribute("user",user.getId());
           //7.如果用户登录成功就可以删除redis中的验证码
            redisTemplate.delete(LOGIN_CODE_KEY+phone);
          return R.success(user);
        }
       return R.error("登录失败");

    }
    


}
```

## 8.后台登录和退出，员工的新增和修改(禁用启用)

#### 1.employeecontroller

```java
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmyloyeeController {
    @Autowired
    private EmployeeService employeeService;
    /**
     * 员工登录
     * @param request
     * @param employee
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        return employeeService.login(request,employee);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //②清理session中的用户id
          request.getSession().removeAttribute("employee");
        //③返回结果（前端页面会进行跳转到登录页面）
        return R.success("退出成功");
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){//返回json格式用@RequestBodyLog
      return employeeService.saveemployee(request,employee);

    }
    //返回需要recods和total所以可以用myp提供的page

    /**
     * 员工信息的分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page>  page(int page,int pageSize, String name){
        return employeeService.pageemployees(page,pageSize,name);
    }

    /**
     * 根据id修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,
                            @RequestBody Employee employee){
        //传过来的数据格式是json所以用requestbody反序列化
     return employeeService.updateemployee(request,employee);
    }

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        log.info("根据id查询信息");
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("没有对应的员工信息");
    }
    
}

```

#### 2.代码实现

```java

@Service
public class EmployeeServiceImp extends
        ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        /**
         * 1、将页面提交的密码password进行md5加密处理
         * 2、根据页面提交的用户名username查询数据库
         * 3、如果没有查询到则返回登录失败结果
         * 4、密码比对，如果不一致则返回登录失败结果
         * 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
         * 6、登录成功，将员工id存入Session并返回登录成功结果
         */
//         * 1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//         * 2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = this.getOne(queryWrapper);
        //之前表里对UserName作为unique约束
//         * 3、如果没有查询到则返回登录失败结果
        if (emp==null){
            return  R.error("登录失败");
        }
//         * 4、密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)){
            return  R.error("登录失败");
        }
//         * 5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus()==0){
            return  R.error("账号已经禁用");
        }
//        * 6、登录成功，将员工id存入Session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);

    }

    @Override
    public R<String> saveemployee(HttpServletRequest request, Employee employee) {
        //设置一个初始密码md5加密

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //获得当前登录用户的id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

       this.save(employee);

        return R.success("新增员工成功");
    }

    @Override
    public R<Page> pageemployees(int page, int pageSize, String name) {

        //1.创造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //2.创造条件构造器
        LambdaQueryWrapper<Employee> qeryWrapper = new LambdaQueryWrapper();
        //添加一个条件查询
        qeryWrapper.like(StringUtils.hasText(name),Employee::getName,name);
        //添加排序条件
        qeryWrapper.orderByDesc(Employee::getCreateTime);
        //3.执行查询
        this.page(pageInfo,qeryWrapper);

        return R.success(pageInfo);
    }

    @Override
    public R<String> updateemployee(HttpServletRequest request, Employee employee) {
        Long empId = (Long)request.getSession().getAttribute("employee");
        //注意empid是自己登录的账号
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        this.updateById(employee);
        return R.success("员工信息修改成功");
    }
}
```

#### 3.配置消息转换器

```text
更新失败，仔细观察id后，我们会发现后台的SQL语句使用的id和数据库中的id是不一样的！
原因是：mybatis-plus对id使用了雪花算法，所以存入数据库中的id是19为长度，但是前端的js只能保证数据的前16位的数据的精度，对我们id后面三位数据进行了四舍五入，所以就出现了精度丢失；就会出现前度传过来的id和数据里面的id不匹配，就没办法正确的修改到我们想要的数据；
当然另一种解决bug的方法是:关闭mybatis-plus的雪花算法来处理ID，我们使用自增ID的策略来往数据库添加id就行；
使用自定义消息转换器
代码bug修复：
思路：既然js对long型的数据会进行精度丢失，那么我们就对数据进行转型，我们可以在服务端（Java端）给页面响应json格式的数据时进行处理，将long型的数据统一转换为string字符串；

#即为前面配置的4.4.1对象转换器
#以及加入配置中的4.4.3静态资源映射
```

## 9.菜品分类管理（新增删除修改）

#### 1.cateCategoryController

```java
/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);

        //分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);

//        categoryService.removeById(id);
        categoryService.remove(id);

        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //1条件构造器
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.添加条件
        categoryLambdaQueryWrapper.eq(category.getType() !=null
                ,Category::getType,category.getType());
        //3.可以做一些排序条件
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).
                orderByDesc(Category::getCreateTime);
        List<Category> list = categoryService.list(categoryLambdaQueryWrapper);

        return R.success(list);
    }
}

```

#### 2.删除套餐的思路(涉及两张表)

> 删除套餐还包括菜品表，所有还要查询关联的菜品，存在不能删除



#### 3.代码实现

```java
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>
        implements CategoryService {
    //先注入菜品和套餐的service
    @Autowired
    DishService dishService;
    @Autowired
    SetmealService setmealService;
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        //然后去除
        //1.构造查询器
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.添加查询条件，根据条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count1 = dishService.count(dishLambdaQueryWrapper);
        if(count1 > 0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //1.构造查询器
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //2.添加查询条件，根据条件
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 =setmealService.count(setmealLambdaQueryWrapper);
        if(count2 > 0){
            //已经关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        super.removeById(id);
    }
}

```

## 10.菜品管理（redis缓存+事务）

#### 1.文件的上传和下载

##### 1.1配置application.xml

```java
#设置一个外部配置源，输出的地址
reggie:
   path: /usr/local/img/
       
#还可以在yml设置上传信息
  spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 100MB
```

##### 1.2代码

```java

/**
 * 文件上传和下载
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) {
        //file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会删除
        log.info(file.toString());
        //获得原始的文件名
        String originalFilename = file.getOriginalFilename();

        //动态拿后缀
        String sufix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //或者用uuid防止名称重复
        String fileName = UUID.randomUUID().toString() + sufix;
        //创建一个目录
        File dir = new File(basePath);
        //判断当前目录是否存在
        if (!dir.exists()) {
            //不存在就创建
            dir.mkdirs();
        }


        try {
            file.transferTo(new File(basePath+new File(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return R.success(fileName);

//        if(!headerImg.isEmpty()){
//            //保存到文件服务器，OSS服务器
//            String originalFilename = headerImg.getOriginalFilename();
//            headerImg.transferTo(new File("H:\\cache\\"+originalFilename));
//        }
//
//        if(photos.length > 0){
//            for (MultipartFile photo : photos) {
//                if(!photo.isEmpty()){
//                    String originalFilename = photo.getOriginalFilename();
//                    photo.transferTo(new File("H:\\cache\\"+originalFilename));
//                }
//            }
//        }

    }

    /**
     * 文件下载
     *
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
            try {
                //输入流，通过输入流读取文件内容
                FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));

                //输出流，通过输出流将文件写回浏览器
                ServletOutputStream outputStream = response.getOutputStream();

                response.setContentType("image/jpeg");
                //代表的是图片文件
                int len = 0;
                byte[] bytes = new byte[1024];
                while ((len=fileInputStream.read(bytes)) != -1){
                    outputStream.write(bytes, 0, len);
                    outputStream.flush();
                }


            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch(Exception e) {
                e.printStackTrace();
            }

    }
}

```

#### 1.dishcontroller

```java

/**
 * 菜品管理
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
   @PostMapping
   public R<String> save( @RequestBody DishDto dishDto){
       log.info(dishDto.toString());
      dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
   }

    /**
     * 菜品信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        return dishService.pagedish(page,pageSize,name);

    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);

    }
    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update( @RequestBody DishDto dishDto){
        //设置dish和口味表
        log.info(dishDto.toString());
        dishService.updateWithFlavor(dishDto);
//        //清理所有的菜品缓存
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        //精确清理
        String key="dish_"+dishDto.getCategoryId()+"_";
        redisTemplate.delete(key);

        return R.success("新增菜品成功");
    }

    /**
     * 利用dish接收更通用
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        return dishService.listdish(dish);
    }

    /**
     * 对菜品批量或者是单个 进行停售或者是起售
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> status(@PathVariable("status") Integer status,@RequestParam List<Long> ids) {
        return dishService.status(status, ids);

    }
    /**
     * 删除菜品
     */
    @DeleteMapping
   public R<String> deletedish(@RequestParam("ids") List<Long> ids){

        return dishService.deletedish(ids);
    }


}
```

#### 2.代码思路

> 1.前端传过来的具体参数，我们需要相应参数类型来接收因为这里传过来的参数比较复杂。所以这里有两种方式进行封装，第一：创建与这些数据对应的实体类（dto)第二使用map来接收；
>
> 2.新增套餐
>
> > 1.用dto进行接收并且进行处理
> >
> > 2.@Transactional *//涉及到对多张表的数据进行操作,需要加事务，需要事务生效,需要在启动类加上事务注解生效*,并且在application启动类上开启@EnableTransactionManagement
> >
> > 3.用stream流进行处理口味，然后批量保存
>
> 3.停售启售修该等代码如下（比较简单不写思路）
>
> 4.删除单个和批量删除
>
> > 1.判断要删除的菜品在不在售卖的套餐中，如果在那不能删除
> >
> > 2.要先判断要删除的菜品是否在售卖，如果在售卖也不能删除
>
> 5.利用redis缓存展示菜品
>
> >1.传过来是dish，先在redis中查询缓存
> >
> >2.根据ids查询数据库，将dishDto通过BeanUtils.copyProperties(item,dishDto);复制dish基本信息
> >
> >3.最后查询出来的信息缓存到redis中
>
> 6.利用redis进行缓存pagedish参数
>
> > 1.利用redis和 BeanUtils.copyProperties（）
> >
> > 2. //copyProperties 拷贝属性，元，不拷集合（集合是显示在页面的，我们要处理）  BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
> >
> > 3.忽略records之后再进行处理

#### 3.代码实现

```java
@Service
@Slf4j
@Transactional
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish>
        implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private SetmealService setmealService;
    @Autowired
    @Lazy
    private CategoryService categoryService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 新增菜品，同时保存对应的口味（两张表）
     * @param dishDto
     */
    @Transactional //两张表事务控制
    public void saveWithFlavor (DishDto dishDto){
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);
        //保存菜品口味数据到菜品口味表，引入口味service
        //批量保存口味（但是存在问题）
        //因为保存的时候 private List<DishFlavor> flavors = new ArrayList<>();
        //封装的是.getFlavors含只有name和value(口味名称和数据），然而无菜品id)
        //1.所以在前面获得dishId
        Long dishId = dishDto.getId();//对应的菜品id
        //2.先对菜品口味进行处理
        List<DishFlavor> flavors = dishDto.getFlavors();
        //3.遍历集合，将dishId给flavor赋值
        flavors=flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
            }
        ).collect(Collectors.toList());
        //item是遍历出来的值，收集起来变成list ,赋值给原来的

        dishFlavorService.saveBatch(dishDto.getFlavors());

    }

    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //1.先查询基本信息
        Dish dish = this.getById(id);
        //2.查询口味信息，从dish_flavor
        //3.返回的sdto，所以需要拷贝
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //条件构造器
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        //条件查询,根据菜品的id与口味表的id对应
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {

        //更新dish表基本信息，清理当前菜品对应的口味，再添加当前的口味数据
        this.updateById(dishDto);
        //构造条件
        LambdaQueryWrapper<DishFlavor> queryWrapper = new
                LambdaQueryWrapper();
        //查询条件
      queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        //插入
        List<DishFlavor> flavors = dishDto.getFlavors();
        dishFlavorService.saveBatch(flavors);
        //注意此时disFlaor的dishid没有值，所以需要传入
        //3.遍历集合，将dishId给flavor赋值
        flavors=flavors.stream().map((item) -> {
                    item.setDishId(dishDto.getId());
                    return item;
                }
        ).collect(Collectors.toList());


    }

    @Override
    public R<String> status(Integer status, List<Long> ids) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(ids !=null,Dish::getId,ids);
        //根据数据进行批量查询
        List<Dish> list = this.list(queryWrapper);

        for (Dish dish : list) {
            if (dish != null){
                dish.setStatus(status);
               this.updateById(dish);
            }
        }
        return R.success("售卖状态修改成功");
    }

    /**
     *套餐批量删除和单个删除
     * @param ids
     */
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {

        //构造条件查询器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //先查询该菜品是否在售卖，如果是则抛出业务异常
        queryWrapper.in(ids!=null,Dish::getId,ids);
        List<Dish> list = this.list(queryWrapper);
        for (Dish dish : list) {
            Integer status = dish.getStatus();
            //如果不是在售卖,则可以删除
            if (status == 0){
                this.removeById(dish.getId());
            }else {
                //此时应该回滚,因为可能前面的删除了，但是后面的是正在售卖
                throw new CustomException("删除菜品中有正在售卖菜品,无法全部删除");
            }
        }

    }

    /**
     * 删除菜品
     *  1.判断要删除的菜品在不在售卖的套餐中，如果在那不能删除
     *  * 2.要先判断要删除的菜品是否在售卖，如果在售卖也不能删除
     * @param ids
     * @return
     */

    @Override
    public R<String> deletedish(List<Long> ids) {

        //1.找到菜品是否关联套餐

        //1.1不关联的直接删除菜品，删除口味
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SetmealDish::getDishId,ids);
        List<SetmealDish> setmealDishes = setmealDishService.list(queryWrapper);
        if (setmealDishes.size()==0){
            //不关联菜品，直接删除
            for (Long id:ids) {
                this.deleteByIds(ids);
            }
            //删除关联口味
            LambdaQueryWrapper<DishFlavor> dishFlavorWrapper = new LambdaQueryWrapper<>();
            dishFlavorWrapper.in(DishFlavor::getDishId,ids);
            dishFlavorService.remove(dishFlavorWrapper);
            return R.success("菜品删除成功");

        }
        //2.关联套餐
        //2.1套餐售
        //如果菜品有关联套餐，并且该套餐正在售卖，那么不能删除
        //得到与删除菜品关联的套餐id
        ArrayList<Long> Setmeal_idList = new ArrayList<>();
        for (SetmealDish setmealDish :setmealDishes ) {
            Long setmealId = setmealDish.getSetmealId();
            Setmeal_idList.add(setmealId);
        }

        //查询出与删除菜品相关联的套餐
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,Setmeal_idList);
        List<Setmeal> setmealList = setmealService.list(setmealLambdaQueryWrapper);
        //对拿到的所有套餐进行遍历，然后拿到套餐的售卖状态，如果有套餐正在售卖那么删除失败
        for (Setmeal setmeal : setmealList) {
            Integer status = setmeal.getStatus();
            if (status == 1){
                return R.error("删除的菜品中有关联在售套餐,删除失败！");
            }
        }
        //2.2套餐不售

        //要删除的菜品关联的套餐没有在售，可以删除
        //这下面的代码并不一定会执行,因为如果前面的for循环中出现status == 1,那么下面的代码就不会再执行
       this.deletedish(ids);
        LambdaQueryWrapper<DishFlavor> queryWrapperdishf = new LambdaQueryWrapper<>();
        queryWrapperdishf.in(DishFlavor::getDishId,ids);
        dishFlavorService.remove(queryWrapperdishf);
        return R.success("菜品删除成功");

    }

    @Override
    public R<List<DishDto>> listdish(Dish dish) {
        List<DishDto> dishDtoList=null;
        String key="dish_"+dish.getCategoryId()+"_"+dish.getStatus();
        //1.从redis中获得缓存数据
        dishDtoList = (List<DishDto>)  redisTemplate.opsForValue().get(key);
        //2.判断是否存在缓存
        if (dishDtoList !=null){
            return R.success(dishDtoList);
        }

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = this.list(queryWrapper);

       dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
       //3.保存查询信息到缓存
        redisTemplate.opsForValue().set(key,dishDtoList,60, TimeUnit.MINUTES);

        return R.success(dishDtoList);
    }

    @Override
    public R<Page> pagedish(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //此时也不能直接保存dishdto，因为没有值
        //所以将dish执行qw的值后拷贝到DishDto之中

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        this.page(pageInfo,queryWrapper);

        //对象拷贝
        //copyProperties 拷贝属性，元，不拷集合（集合是显示在页面的，我们要处理）
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        //以下是处理list
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list= records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            String categoryname = category.getName();
            dishDto.setCategoryName(categoryname);
            return dishDto;

        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

}

```

## 11.套餐管理（sringcache)

#### 1 .spring cache的配置

```java
# 1.导入依赖
    
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-cache</artifactId>
 </dependency>

 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-data-redis</artifactId>
 </dependency>
    
#2. yaml配置
spring : 
     cache:
        redis:
             time-to-live: 1800000 #设置缓存有效期


#3.项目上加入注解@EnableCaching
#4.在controller方法上加入cacheabl,方法@cacheEvict，进行缓存操作

```

#### 2.SetmealController

```java
/**
 * 套餐管理
 */

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息：{}",setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
      return setmealService.pagesetmeal(page,pageSize,name);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache",allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.removeWithDish(ids);

        return R.success("套餐数据删除成功");
    }


    @GetMapping("/list")
    @CacheEvict(value="setmealCache",key="#setmeal.categoryId+'_'+#setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        log.info("setmeal:{}", setmeal);
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(setmeal.getName()), Setmeal::getName, setmeal.getName());
        queryWrapper.eq(null != setmeal.getCategoryId(), Setmeal::getCategoryId, setmeal.getCategoryId());
        queryWrapper.eq(null != setmeal.getStatus(), Setmeal::getStatus, setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list=setmealService.list(queryWrapper);

        return R.success(list);
    }
    /**
     单个/批量修改套餐状态
     */
    @PostMapping("/status/{status}")
    public R<String> steamlstatuschange(@PathVariable("status") Integer status,@RequestParam("ids") List<Long> ids){

        setmealService.statuschange(status,ids);
        return R.success("修改套餐状态成功");
    }
    @GetMapping("/{id}")
    public R<SetmealDto> listid(@PathVariable("id") Long id) {
       SetmealDto setmealDto= setmealService.getDate(id);
        return R.success(setmealDto);

    }

}

```

#### 3.思路

> 修改删除停售启售

#### 4.代码实现

```java

@Service
@Slf4j

public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,
        Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
     @Autowired
      @lazy
    private CategoryService categoryService;

    /**
     * 新增套餐，同时需要保存套餐和菜品发关系
     * @param setmealDto
     */
    @Transactional
    public void saveWithDish(SetmealDto setmealDto){
        //保存stteaml基本信息，操作steaml，执行inset操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //注意此时的setmealDishes的属性setMealId没有值，dishId存在值
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，执行sttmeal dish的inset;
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除菜单也需要从删除关系表
     * 还要先判断状态
     * @param ids
     */

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in (1,2,3) and status = 1
        //查询套餐状态，确定是否可用删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        int count = this.count(queryWrapper);
        if(count > 0){
            //如果不能删除，抛出一个业务异常
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        //delete from setmeal_dish where setmeal_id in (1,2,3)
        //找到套餐关系表的id然后删除
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        //删除关系表中的数据----setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);

    }

    @Override
    public void statuschange(Integer status, List<Long> ids) {
        //1.根据ids找到对应的套餐
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ids !=null,Setmeal::getId,ids);
        List<Setmeal> list = this.list(queryWrapper);
        for (Setmeal setmeal:list) {
            if (setmeal!=null){
               setmeal.setStatus(status);
               this.updateById(setmeal);
            }
        }

    }

    @Override
    public SetmealDto getDate(Long id) {
        //1.根据传给的套餐id进行数据的回显
        Setmeal setmeal = this.getById(id);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtil.copyProperties(setmeal,setmealDto);
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    public R<Page> pagesetmeal(int page, int pageSize, String name) {
        //分页构造器对象
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据name进行like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);
        //添加排序条件，根据更新时间降序排列
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        this.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            //对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            //分类id
            Long categoryId = item.getCategoryId();
            //根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                //分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }
}

```

## 12.移动端地址

#### 1.AddressBookController

```java
/**
 * 地址簿管理
 */
@Slf4j
@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    private AddressBookService addressBookService;

    /**
     * 新增
     */
    @PostMapping
    public R<AddressBook> save(@RequestBody AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);
        addressBookService.save(addressBook);
        return R.success(addressBook);
    }

    /**
     * 设置默认地址
     */
    @PutMapping("default")
    public R<AddressBook> setDefault(@RequestBody AddressBook addressBook) {
        log.info("addressBook:{}", addressBook);
        LambdaUpdateWrapper<AddressBook> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        wrapper.set(AddressBook::getIsDefault, 0);
        //SQL:update address_book set is_default = 0 where user_id = ?
        addressBookService.update(wrapper);

        addressBook.setIsDefault(1);
        //SQL:update address_book set is_default = 1 where id = ?
        addressBookService.updateById(addressBook);
        return R.success(addressBook);
    }

    /**
     * 根据id查询地址
     */
    @GetMapping("/{id}")
    public R get(@PathVariable Long id) {
        AddressBook addressBook = addressBookService.getById(id);
        if (addressBook != null) {
            return R.success(addressBook);
        } else {
            return R.error("没有找到该对象");
        }
    }

    /**
     * 查询默认地址
     */
    @GetMapping("default")
    public R<AddressBook> getDefault() {
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getUserId, BaseContext.getCurrentId());
        queryWrapper.eq(AddressBook::getIsDefault, 1);

        //SQL:select * from address_book where user_id = ? and is_default = 1
        AddressBook addressBook = addressBookService.getOne(queryWrapper);

        if (null == addressBook) {
            return R.error("没有找到该对象");
        } else {
            return R.success(addressBook);
        }
    }

    /**
     * 查询指定用户的全部地址
     */
    @GetMapping("/list")
    public R<List<AddressBook>> list(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        log.info("addressBook:{}", addressBook);

        //条件构造器
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(null != addressBook.getUserId(), AddressBook::getUserId, addressBook.getUserId());
        queryWrapper.orderByDesc(AddressBook::getUpdateTime);

        //SQL:select * from address_book where user_id = ? order by update_time desc
        return R.success(addressBookService.list(queryWrapper));
    }

    /**
     * 删除地址
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam("ids") Long ids){
        return addressBookService.deletad(ids);
    }

    /**
     * 修改地址
     * @param addressBook
     * @return
     */
    @PutMapping
    public R<String> edit(@RequestBody AddressBook addressBook){
        if (addressBook ==null){
            return R.error("请求异常");
        }
        addressBookService.updateById(addressBook);
        return R.success("地址修改成功");

    }


}

```

#### 2.AddressBookServiceImpl

```java
@Service
public class AddressBookServiceImpl extends ServiceImpl
        <AddressBookMapper, AddressBook> implements AddressBookService {

    @Override
    public R<String> deletad(Long ids) {
        //1.得到ids，删除地址
        if (ids == null){
            return R.error("请求异常");
        }
        LambdaQueryWrapper<AddressBook> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AddressBook::getId,ids).eq(AddressBook::getUserId, BaseContext.getCurrentId());
        this.remove(queryWrapper);
        return R.success("删除地址成功");


    }
}

```

## 13.购物车开发

#### 1.ShoppingCartController

```java

/**
 * 购物车
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("购物车数据:{}",shoppingCart);

        return shoppingCartService.addcart(shoppingCart);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车...");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        //SQL:delete from shopping_cart where user_id = ?
        shoppingCartService.clean();


        return R.success("清空购物车成功");
    }



    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){

        return shoppingCartService.sub(shoppingCart);

    }

}
```

#### 2.代码实现

```java
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
    
    @Override
    public R<ShoppingCart> sub(ShoppingCart shoppingCart) {
        //找到购物车的id,根据购物车找到
        Long shoppingCartId = shoppingCart.getId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        if (shoppingCart.getDishId() !=null) {

            //查询条件
            queryWrapper.eq(ShoppingCart::getDishId, shoppingCart.getDishId())
                    .eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            //这里必须要加两个条件，否则会出现用户互相修改对方与自己购物车中相同套餐或者是菜品的数量
            ShoppingCart cart1 = this.getOne(queryWrapper);
            cart1.setNumber(cart1.getNumber()-1);
            Integer number = cart1.getNumber();
            if (number >0){
                //更新
                this.updateById(cart1);
            }else if (number==0){
                this.removeById(cart1.getId());
            }else {
                R.error("操作异常");
            }
            return R.success(cart1);

        }

        Long setmealId = shoppingCart.getSetmealId();
        if (setmealId != null){
            //代表是套餐数量减少
            queryWrapper.eq(ShoppingCart::getSetmealId,setmealId).eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
            ShoppingCart cart2 = this.getOne(queryWrapper);
            cart2.setNumber(cart2.getNumber()-1);
            Integer LatestNumber = cart2.getNumber();
            if (LatestNumber > 0){
                //对数据进行更新操作
                this.updateById(cart2);
            }else if(LatestNumber == 0){
                //如果购物车的套餐数量减为0，那么就把套餐从购物车删除
                this.removeById(cart2.getId());
            }else if (LatestNumber < 0){
                return R.error("操作异常");
            }
            return R.success(cart2);
        }
        //如果两个大if判断都进不去
        return R.error("操作异常");

    }

    /**
     * 清空购物车
     */
    @Override
    public void clean() {


        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        this.remove(queryWrapper);
    }

    @Override
    public R<ShoppingCart> addcart(ShoppingCart shoppingCart) {


        //设置用户id，指定当前是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId != null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else{
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        //查询当前菜品或者套餐是否在购物车中
        //SQL:select * from shopping_cart where user_id = ? and dish_id/setmeal_id = ?
        ShoppingCart cartServiceOne = this.getOne(queryWrapper);

        if(cartServiceOne != null){
            //如果已经存在，就在原来数量基础上加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            this.updateById(cartServiceOne);
        }else{
            //如果不存在，则添加到购物车，数量默认就是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }
}

```

## 14.订单明细（自写）

> 数据库中的consignee是可以为null的，所以在后台代码中帮订单添加该属性的时候要判断是否null！然后就是去修改前端代码就行:
> 把72行的userName改成consignee就行

#### 1. OrderController

```java
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    /**
     * 查询订单
     * @param page
     * @param pageSize
     * @param number
     * @param beginTime
     * @param endTime
     * @return
     */
    @GetMapping("/page")
    public R<Page>  orderPage(int page,int pageSize,String number,String beginTime,String endTime ){
        //分页构造器对象
       Page<Orders> pageInfo = new Page<>(page, pageSize);
        //构造条件查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //查询条件
        queryWrapper.like(number!=null,Orders::getNumber,number)
                .gt( StringUtils.isNotBlank(beginTime),Orders::getOrderTime,beginTime)
                .lt( StringUtils.isNotBlank(endTime),Orders::getOrderTime,endTime);
        orderService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    /**
     * 订单派送状态的更改
     * @param orders
     * @return
     */
    @PutMapping
    public R<Orders> dispatch(@RequestBody Orders orders){
        //构造查询对象
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //构造查询条件
        queryWrapper.eq(orders.getId()!=null,Orders::getId,orders.getId());
        Orders orderone = orderService.getOne(queryWrapper);
        orderone.setStatus(orders.getStatus());
        orderService.updateById(orderone);

        return R.success(orderone);

    }

    @GetMapping("/userPage")
    public R<Page> userpage(int page,int pageSize){
        return orderService.userPage(page,pageSize);
    }
    //客户端点击再来一单
    /**
     * 前端点击再来一单是直接跳转到购物车的，所以为了避免数据有问题，再跳转之前我们需要把购物车的数据给清除
     * ①通过orderId获取订单明细
     * ②把订单明细的数据的数据塞到购物车表中，不过在此之前要先把购物车表中的数据给清除(清除的是当前登录用户的购物车表中的数据)，
     * 不然就会导致再来一单的数据有问题；
     * (这样可能会影响用户体验，但是对于外卖来说，用户体验的影响不是很大，电商项目就不能这么干了)
     */
    @PostMapping("/again")
    public R<String> againSubmit(@RequestBody Map<String,String> map){
        return orderService.again(map);
    }
    
}


```

#### 2.代码思路

> 1.用户下单的两个工具 /IdWorker是mybatis-plus提供的一个ID生成工具，可以生成一个全局唯一的长整形ID
>      //AtomicInteger是一个Java concurrent包提供的一个原子类，通过这个类可以对Integer进行一些原子操作。//原子整型数，保证线程安全
>
> 2.

#### 3.代码实现

```java
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    public void submit(Orders orders) {
        //获得当前用户id
        Long userId = BaseContext.getCurrentId();

        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(wrapper);

        if(shoppingCarts == null || shoppingCarts.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userId);

        //查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址信息有误，不能下单");
        }

        long orderId = IdWorker.getId();//订单号
            //IdWorker是mybatis-plus提供的一个ID生成工具，可以生成一个全局唯一的长整形ID
            //AtomicInteger是一个Java concurrent包提供的一个原子类，通过这个类可以对Integer进行一些原子操作。
    //原子整型数，保证线程安全

        AtomicInteger amount = new AtomicInteger(0);

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            //这个是累加金额操作，累加每个菜品或者套餐乘于相应的份数
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());
        //    //组装订单数据

        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        //总金额，math的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。
        orders.setUserId(userId);
        //    //注意：orders里面的number是订单号的意思，不是数量
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        //向订单表插入数据，一条数据
        this.save(orders);

        //向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车数据
        shoppingCartService.remove(wrapper);
    }

    @Override
    public R<Page> userPage(int page, int pageSize) {

            //分页构造器对象
            Page<Orders> pageInfo = new Page<>(page,pageSize);
            Page<OrderDto> pageDto = new Page<>(page,pageSize);
            //构造条件查询对象
            LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Orders::getUserId, BaseContext.getCurrentId());
            //这里是直接把当前用户分页的全部结果查询出来，要添加用户id作为查询条件，否则会出现用户可以查询到其他用户的订单情况
            //添加排序条件，根据更新时间降序排列
            queryWrapper.orderByDesc(Orders::getOrderTime);
           this.page(pageInfo, queryWrapper);

            //通过OrderId查询对应的OrderDetail
            LambdaQueryWrapper<OrderDetail> queryWrapper2 = new LambdaQueryWrapper<>();

            //对OrderDto进行需要的属性赋值
            List<Orders> records = pageInfo.getRecords();
            List<OrderDto> orderDtoList = records.stream().map((item) ->{
                OrderDto orderDto = new OrderDto();
                //此时的orderDto对象里面orderDetails属性还是空 下面准备为它赋值
                Long orderId = item.getId();//获取订单id
                List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailListByOrderId(orderId);
                BeanUtils.copyProperties(item,orderDto);
                //对orderDto进行OrderDetails属性的赋值
                orderDto.setOrderDetails(orderDetailList);
                log.info(orderDto.toString());
                return orderDto;
            }).collect(Collectors.toList());

            //使用dto的分页
            BeanUtils.copyProperties(pageInfo,pageDto,"records");

            pageDto.setRecords(orderDtoList);
            return R.success(pageDto);



    }

    @Override
    public R<String> again(Map<String, String> map) {
        String ids = map.get("id");

        long id = Long.parseLong(ids);

        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,id);
        //获取该订单对应的所有的订单明细表
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);

        //通过用户id把原来的购物车给清空，这里的clean方法是视频中讲过的,建议抽取到service中,那么这里就可以直接调用了
        shoppingCartService.clean();

        //获取用户id
        Long userId = BaseContext.getCurrentId();
        List<ShoppingCart> shoppingCartList = orderDetailList.stream().map((item) -> {
            //把从order表中和order_details表中获取到的数据赋值给这个购物车对象
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUserId(userId);
            shoppingCart.setImage(item.getImage());
            Long dishId = item.getDishId();
            Long setmealId = item.getSetmealId();
            if (dishId != null) {
                //如果是菜品那就添加菜品的查询条件
                shoppingCart.setDishId(dishId);
            } else {
                //添加到购物车的是套餐
                shoppingCart.setSetmealId(setmealId);
            }
            shoppingCart.setName(item.getName());
            shoppingCart.setDishFlavor(item.getDishFlavor());
            shoppingCart.setNumber(item.getNumber());
            shoppingCart.setAmount(item.getAmount());
            shoppingCart.setCreateTime(LocalDateTime.now());
            return shoppingCart;
        }).collect(Collectors.toList());

        //把携带数据的购物车批量插入购物车表  这个批量保存的方法要使用熟练！！！
        shoppingCartService.saveBatch(shoppingCartList);

        return R.success("操作成功");

    }
    
}
```

#### 4orderdetials的一个抽离方法

```java
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    private OrderDetailService orderDetailService;
    @Override
    public List<OrderDetail> getOrderDetailListByOrderId(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper);
        return orderDetailList;
    }
    //抽离的一个方法，通过订单id查询订单明细，得到一个订单明细的集合
    //这里抽离出来是为了避免在stream中遍历的时候直接使用构造条件来查询导致eq叠加，从而导致后面查询的数据都是null
    
}
```







# SWagger

### 1 介绍

官网：[API Documentation & Design Tools for Teams | Swagger](https://swagger.io/)

> Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。功能主要包含以下几点:
>
> A. 使得前后端分离开发更加方便，有利于团队协作
>
> B. 接口文档在线自动生成，降低后端开发人员编写接口文档的负担
>
> C. 接口功能测试
>

### 2 使用方式

### 1). 导入knife4j的maven坐标

```java
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>3.0.2</version>
</dependency>
(我用的版本优点问题，我调低了2.04)
```

### 2). 导入knife4j相关配置类

> 这里我们就不需要再创建一个新的配置类了，我们直接在WebMvcConfig配置类中声明即可。
>
> A. 在该配置类中加上两个注解 @EnableSwagger2 @EnableKnife4j ,开启Swagger和Knife4j的功能。
>
> B. 在配置类中声明一个Docket类型的bean, 通过该bean来指定生成文档的信息。

```java
@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
	
    /**
     * 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
	
    /**
     * 扩展mvc框架的消息转换器
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        //创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器，底层使用Jackson将Java对象转为json
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将上面的消息转换器对象追加到mvc框架的转换器集合中
        converters.add(0,messageConverter);
    }
	
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.itheima.reggie.controller"))
                .paths(PathSelectors.any())
                .build();
    }
	
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Eatting外卖")
                .version("1.0")
                .description("Eatting外卖接口文档")
                .build();
    }
}

```

> 注意： Docket声明时，指定的有一个包扫描的路径，该路径指定的是Controller所在包的路径。因为Swagger在生成接口文档时，就是根据这里指定的包路径，自动的扫描该包下的@Controller， @RestController， @RequestMapping等SpringMVC的注解，依据这些注解来生成对应的接口文档。
> ————————————————

### 3). 设置静态资源映射

> 由于Swagger生成的在线文档中，涉及到很多静态资源，这些静态资源需要添加静态资源映射，否则接口文档页面无法访问。因此需要在 WebMvcConfig类中的addResourceHandlers方法中增加如下配置。

```
registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
```

### 4). 在LoginCheckFilter中设置不需要处理的请求路径

> 需要将Swagger及Knife4j相关的静态资源直接放行，无需登录即可访问，否则我们就需要登录之后，才可以访问接口文档的页面。

> 在原有的不需要处理的请求路径中，再增加如下链接：

```java
"/doc.html",
"/webjars/**",
"/swagger-resources",
"/v2/api-docs"
```

### 3 查看接口文档

> 经过上面的集成配置之后，我们的项目集成Swagger及Knife4j就已经完成了，接下来我们可以重新启动项目，访问接口文档，访问链接为： http://localhost:8080/doc.html

### 4 常用注解

> 注解	位置	说明
> @Api	类	加载Controller类上,表示对类的说明
> @ApiModel	类(通常是实体类)	描述实体类的作用
> @ApiModelProperty	属性	描述实体类的属性
> @ApiOperation	方法	说明方法的用途、作用
> @ApiImplicitParams	方法	表示一组参数说明
> @ApiImplicitParam	方法	用在@ApiImplicitParams注解中，指定一个请求参数的各个方面的属性

### 1). 实体类

> 可以通过 @ApiModel , @ApiModelProperty 来描述实体类及属性

```java
@Data
@ApiModel("套餐")
public class Setmeal implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("主键")
    private Long id;
   
    //分类id
    @ApiModelProperty("分类id")
    private Long categoryId;
  
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}

```

### 2). 响应实体R

```java
@Data
@ApiModel("返回结果")
public class R<T> implements Serializable{
@ApiModelProperty("编码")
private Integer code; //编码：1成功，0和其它数字为失败

//省略静态方法 ....
}
```


###  3). Controller类及其中的方法

> 描述Controller、方法及其方法参数，可以通过注解： @Api， @APIOperation， @ApiImplicitParams, @ApiImplicitParam

```java
@RestController
@RequestMapping("/setmeal")
@Slf4j
@Api(tags = "套餐相关接口")
public class SetmealController {
@PostMapping
@CacheEvict(value = "setmealCache",allEntries = true)
@ApiOperation(value = "新增套餐接口")
public R<String> save(@RequestBody SetmealDto setmealDto){
}

@GetMapping("/page")
@ApiOperation(value = "套餐分页查询接口")
@ApiImplicitParams({
        @ApiImplicitParam(name = "page",value = "页码",required = true),
        @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
        @ApiImplicitParam(name = "name",value = "套餐名称",required = false)
})

```



# 优化1:Linux部署

## 1.手动部署步骤

```java
#1.将打包的reggout-0.0.1-SNAPSHOT.jar部署到linux虚拟机中
#2.用nohup 命令进行手动部署
    
nohug 命令用于不挂段的运行指定命令，退出终端不会影响程序的运行
语法说明 nohup Command[Arg...][&]   
    Command要执行的命令
 Arg一些参数，可以指定输出文件
 & 让命令在后台运行   
 举例：    
# nohup java -jar reggout-0.0.1-SNAPSHOT.jar &>hello.log &
&>hello.log将日志写入hello.log
#这个时候想要停止就必须要杀进程
    
 # ps -ef 找到所有的进程
 [root@yangvm01 app]# ps -ef | grep java
root       4005   2988  3 01:38 pts/1    00:00:06 java -jar reggout-0.0.1-SNAPSHOT.jar

[root@yangvm01 app]# kill -9 4005  杀进程
```

## 2.shell自动部署

> #1.linux安装git
>
>     ```shell
>     yum git list
>     yum install git
>     ```
>
>  #2.linux安装maven
>
> ```shell
> 
> # 1.解压maven   
> tar -zxvf apache-maven-3.5.4-bin.tar.gz
> #2.在/etc/profile写入
> #2.1打开
> vim /etc/profile
>  #2.2写入
> export MAVEN_HOME=/usr/local/apache-maven-3.6.1
> export PATH=$JAVA_HOME/bin:$MAVEN_HOME/bin:$PATH
>  #3.退出进行加载
> source /etc/profile
>  #4.查看配置
> mvn -version
>     
> [root@localhost local]# mkdir repo
> [root@localhost local]# cd apache-maven-3.5.4/
> [root@localhost apache-maven-3.5.4]# cd conf/
> [root@localhost conf]# vim settings.xml 
> #5.打开mvaen的seting.xml配置 
> 
> 在settings标签下加上这句话设置仓库位置
> <localRepository>/usr/local/repo</localRepository>
> 
> # mkdir repo  pwd的位置 /usr/local/repo
> # vim 写入
>      <localRepository>/usr/local/repo</localRepository>
>  #5.将shell脚本复制到    
> #将shell脚本复制到linux bootStart.sh
> ```
>
> #3.编写shell脚本，拉去代码，编译，打包，启动
>
>  #4.为用户授予执行shelll脚本的命令
>  #5.执行shell脚本

## 1.具体步骤

> 1). 在服务器B(192.168.138.101)中安装jdk、git、maven、MySQL，使用git clone命令将git远程仓库的代码克隆下来

> A. 确认JDK环境
>
> java -version
>
> B. 确认Git环境
>
> C. 确认Maven环境
>
> mvn -v
>
> D. 将我们开发完成的代码推送至远程仓库,并在服务器B中克隆下来
>
> #创建java代码存放目录
> mkdir -p /usr/local/javaapp
>
> #切换目录
> cd /usr/local/javaapp
>
> #克隆代码 , 需要使用自己的远程仓库
> git clone  xxxx
>
> 2). 将资料中提供的reggieStart.sh文件上传到服务器B，通过chmod命令设置执行权限
>
> chmod 777 reggieStart.sh
>
> 3). 执行reggieStart.sh脚本文件，自动部署项目
>
> ```shell
> #!/bin/sh
> echo =================================
> echo  自动化部署脚本启动
> echo =================================
> 
> echo 停止原来运行中的工程
> APP_NAME=reggie_take_out
> 
> tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
> if [ ${tpid} ]; then
> echo 'Stop Process...'
> kill -15 $tpid
> fi
> sleep 2
> tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
> if [ ${tpid} ]; then
> echo 'Kill Process!'
> kill -9 $tpid
> else
> echo 'Stop Success!'
> fi
> 
> echo 准备从Git仓库拉取最新代码
> cd /usr/local/app/reggie_take_out
> 
> echo 开始从Git仓库拉取最新代码
> git pull
> echo 代码拉取完成
> 
> echo 开始打包
> output=`mvn clean package -Dmaven.test.skip=true`
> 
> cd target
> 
> echo 启动项目
> nohup java -jar reggie_take_out-1.0-SNAPSHOT.jar &> reggie_take_out.log &
> echo 项目启动完成
> ```
>
> > tail -f reggg.log动态查看文件内容
>
> 4). 访问系统测试
>
> http://192.168.138.101/ (自己虚拟机的地址)
>
> 执行完shell脚本之后，我们可以通过 ps -ef|grep java 指令，查看服务是否启动。

# 优化2:数据库的读写分离

## 1.原理

> ## 1.1MySQL主从复制（二进制日志）介绍
>
> > MySQL主从复制是一个异步的复制过程，底层是基于Mysql数据库自带的二进制日志功能。
> > 就是一台或多台MysQL数据库(slave，即从库)从另一台NySQL数据库（master，即主库）进行日志的复制然后再解析日志并应用到自身，最终实现从库的数据和主库的数据保持一致。MySQL主从复制是NySQL数据库自带功能，无需借助第三方工具。
>
> > MySQL复制过程分成三步:
> >
> > master将改变记录到二进制日志（binary log)
> > slave将master的binary log拷贝到它的中继日志（relay log）
> > slave重做中继日志中的事件，将改变应用到自己的数据库中

## 2.引入依赖

#### 1.pom依赖

```java
<dependency>
	<groupId>org.apache.shardingsphere</groupId>
	<artifactId> sharding-jdbc-spring-boot-starter</artifactId>
	<version>4.0.0-RC1</version>
</dependency>
```

#### 2.yaml配置

```yaml
server:
  port: 8080
mybatis-plus:
  configuration:
    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      id-type: ASSIGN_ID

spring:
  shardingsphere:
    datasource:
      names:
        master,slave
      # 主数据源
      master:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://192.168.xx.xx:3306/rw?characterEncoding=utf-8
        username: root
        password: 123456
      # 从数据源
      slave:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://192.168.xxx.xxx:3306/rw?characterEncoding=utf-8
        username: root
        password: 123456
    masterslave:
      # 读写分离配置
      load-balance-algorithm-type: round_robin
      # 最终的数据源名称
      name: dataSource
      # 主库数据源名称
      master-data-source-name: master
      # 从库数据源名称列表，多个逗号分隔
      slave-data-source-names: slave
    props:
      sql:
        show: true #开启SQL显示，默认false
  main:
    allow-bean-definition-overriding: true
#在配置文件中配置允许bean定义覆盖配置项
```

# 优化3:nginx前后端分离

###  1.Nginx 配置

>  官网 http://nginx.org/ 版本建议：1.20.1

````
命令拉取安装版本（推荐）
因为Nginx是用C写的，所以要提前拉取一部分安装包
安装过程:
1、安装依赖包yum -y install gcc pcre-devel zlib-devel openssl openssl-devel
2、下载Nginx安装包wget https://nginx.org/download/nginx-1.16.1.tar.gz
3、解压tar -zxvf nginx-1.16.1.tar.gz
4、cd nginx-1.16.1
5、./configure --prefix=/usr/local/nginx
6、make && make install


# vim /etc/profile
    #添加nginx的环境边框
PATH=/usr/local/nginx/sbin:$JAVA_HOME/bin:$PATH
# source /etc/profile
# 现在可以
    nginx -s reload
````

### 2.nginx命令

启动之前先测试一下

```
./nginx -v 查看版本
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/4629e866ca0645f5a1532f0599d9ad87.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/44a52af0649e49c8a805d4c7b0245259.png)
![在这里插入图片描述](https://img-blog.csdnimg.cn/60441840faa042bb9ecf0b263d7485d6.png)

> 这样也可以启动
>
> /usr/local/nginx/sbin/nginx

> 此时意思是会被反向代理到 proxy_pass http://192.168.8.130:8080;

```java
#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
        listen       80;
        server_name  localhost;

        #charset koi8-r;

        #access_log  logs/host.access.log  main;

        location / {
            root   html/dist;
            index  index.html index.htm;
        }

       #反向代理

       location ^~ /api/ {
	    rewrite ^/api/(.*)$ /$1 break;
	    proxy_pass http://192.168.8.130:8080;
	}


        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }

        # proxy the PHP scripts to Apache listening on 127.0.0.1:80
        #
        #location ~ \.php$ {
        #    proxy_pass   http://127.0.0.1;
        #}

        # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
        #
        #location ~ \.php$ {
        #    root           html;
        #    fastcgi_pass   127.0.0.1:9000;
        #    fastcgi_index  index.php;
        #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
        #    include        fastcgi_params;
        #}

        # deny access to .htaccess files, if Apache's document root
        # concurs with nginx's one
        #
        #location ~ /\.ht {
        #    deny  all;
        #}
    }


    # another virtual host using mix of IP-, name-, and port-based configuration
    #
    #server {
    #    listen       8000;
    #    listen       somename:8080;
    #    server_name  somename  alias  another.alias;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}


    # HTTPS server
    #
    #server {
    #    listen       443 ssl;
    #    server_name  localhost;

    #    ssl_certificate      cert.pem;
    #    ssl_certificate_key  cert.key;

    #    ssl_session_cache    shared:SSL:1m;
    #    ssl_session_timeout  5m;

    #    ssl_ciphers  HIGH:!aNULL:!MD5;
    #    ssl_prefer_server_ciphers  on;

    #    location / {
    #        root   html;
    #        index  index.html index.htm;
    #    }
    #}

}

```

## yaml配置

```java
server:
  port: 8080
spring:
  application:
    name: reggie_take_out
    #应用的名称默认该项目名
  datasource:
    druid:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc:mysql://192.168.8.1:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      #Url要改成自己的jdbc:mysql://localhost:3306/reggie?serverTimezone=UTC
#      url: jdbc:mysql://localhost:3306/reggie?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&useSSL=false&allowPublicKeyRetrieval=true
      username: root
      password: 123456
  redis:
    host: 192.168.8.130
    port: 6379
    password: yangroot
  cache:
    redis:
      time-to-live: 1800000 #设置缓存有效期

    lettuce:
      pool:
        max-active: 10 #最大连接数量
        max-idle: 10 #阻塞最大等待时间
        min-idle: 1
        time-between-eviction-runs: 10s
    database: 1 #操作的数据库1，这里默认是0,我的0做的其他项目缓存


  # 邮箱配置
  mail:
    host: smtp.qq.com  # 发送邮件的服务器地址
    username: 1573478674@qq.com # 开启 IMAP/SMTP服务 的qq邮箱的账号
    password: aibzoigxgvfphjjg # 开启 IMAP/SMTP服务 获得的授权码,而不是qq邮箱的登录密码
    default-encoding: UTF-8

mybatis-plus:
  configuration:
    #在映射实体或者属性时，将数据库中表名和字段名中的下划线去掉，按照驼峰命名法映射
    #比如表user_name > 对应表userName
    map-underscore-to-camel-case: true
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    #    mapper-locations: classpath:mybatis/mapper/*.xml
    db-config:
      id-type: ASSIGN_ID

#设置一个外部配置源，输出的地址
reggie:
  path: /usr/local/img/
```

```java
#!/bin/sh
echo =================================
echo  自动化部署脚本启动
echo =================================

echo 停止原来运行中的工程
APP_NAME=reggie_take_out

tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
echo 'Stop Process...'
kill -15 $tpid
fi
sleep 2
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
echo 'Kill Process!'
kill -9 $tpid
else
echo 'Stop Success!'
fi

echo 准备从Git仓库拉取最新代码
cd /usr/local/app/reggie_take_out

echo 开始从Git仓库拉取最新代码
git pull
echo 代码拉取完成

echo 开始打包
output=`mvn clean package -Dmaven.test.skip=true`

cd target

echo 启动项目
nohup java -jar reggie_take_out 1.0-SNAPSHOT.jar &> reggie_take_out.log &
echo 项目启动完成
```

```java

```





