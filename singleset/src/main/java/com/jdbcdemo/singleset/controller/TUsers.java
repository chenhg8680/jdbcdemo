package com.jdbcdemo.singleset.controller;

import com.jdbcdemo.singleset.repository.TUser;
import com.jdbcdemo.singleset.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class TUsers {
    @Autowired
    private TUserService tUserService;


    @RequestMapping(value="", method = RequestMethod.GET)
    public List<TUser> search() throws Exception {
        HashMap<String,Object> params = new HashMap<>();
        return tUserService.queryAll(params);
    }

    @RequestMapping(value="", method = RequestMethod.POST)
    public Integer add() throws Exception {
        TUser tUser = new TUser();
        tUser.setName("test1111");
        tUser.setStatus(1);

        return tUserService.insert(tUser);
    }
}

