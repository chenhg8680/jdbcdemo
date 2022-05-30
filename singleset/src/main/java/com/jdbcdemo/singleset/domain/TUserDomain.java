package com.jdbcdemo.singleset.domain;

import com.jdbcdemo.singleset.repository.TUser;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface TUserDomain {
    List<TUser> queryAll(HashMap<String, Object> infos) throws Exception;
    TUser getOne(Integer id) throws  Exception;
    int insert(TUser tUser) throws  Exception;
    int update(int id, HashMap<String, Object> condition) throws Exception;
    List<TUser> queryForList(HashMap<String, Object> infos, Integer page, Integer limit) throws Exception;
    List<TUser> queryForList(HashMap<String, Object> infos, Integer page, Integer limit, String orderBy, String desc) throws Exception;
    int count(HashMap<String, Object> infos) throws Exception;
}
