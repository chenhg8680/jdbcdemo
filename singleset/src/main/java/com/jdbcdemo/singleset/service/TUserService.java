package com.jdbcdemo.singleset.service;

import com.jdbcdemo.singleset.domain.TUserDomain;
import com.jdbcdemo.singleset.repository.TUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class TUserService {

    @Resource
    private TUserDomain tUserDomain;

    public List<TUser> queryAll(HashMap<String, Object> infos) throws Exception{
        return tUserDomain.queryAll(infos);
    }
    public TUser getOne(Integer id) throws  Exception{
        return tUserDomain.getOne(id);
    }
    public int insert(HashMap<String,Object> params) throws  Exception{
        return tUserDomain.insert(params);
    }
    public int update(int id, HashMap<String, Object> condition) throws Exception{
        return tUserDomain.update(id,condition);
    }
    public List<TUser> queryForList(HashMap<String, Object> infos, Integer page, Integer limit) throws Exception{
        return tUserDomain.queryForList(infos,page,limit);
    }
    public int count(HashMap<String, Object> infos) throws Exception{
        return tUserDomain.count(infos);
    }
}
