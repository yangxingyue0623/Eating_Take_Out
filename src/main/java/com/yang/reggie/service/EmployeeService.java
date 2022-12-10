package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {
    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> updateemployee(HttpServletRequest request, Employee employee);

    R<String> saveemployee(HttpServletRequest request, Employee employee);

    R<Page> pageemployees(int page, int pageSize, String name);
}
