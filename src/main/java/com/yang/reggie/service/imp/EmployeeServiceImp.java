package com.yang.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Employee;
import com.yang.reggie.mapper.EmployeeMapper;
import com.yang.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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