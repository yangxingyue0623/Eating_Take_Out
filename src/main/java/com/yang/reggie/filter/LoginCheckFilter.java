package com.yang.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.yang.reggie.com.BaseContext;
import com.yang.reggie.com.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
