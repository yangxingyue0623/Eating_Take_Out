package com.yang.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yang.reggie.com.R;
import com.yang.reggie.entity.AddressBook;


public interface AddressBookService extends IService<AddressBook> {

    R<String> deletad(Long ids);
}
