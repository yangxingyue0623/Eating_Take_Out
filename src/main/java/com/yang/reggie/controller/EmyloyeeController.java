package com.yang.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Employee;
import com.yang.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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