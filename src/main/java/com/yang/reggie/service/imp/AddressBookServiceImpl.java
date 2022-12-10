package com.yang.reggie.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yang.reggie.com.BaseContext;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.AddressBook;
import com.yang.reggie.mapper.AddressBookMapper;
import com.yang.reggie.service.AddressBookService;
import org.springframework.stereotype.Service;

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