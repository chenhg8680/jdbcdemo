package com.jdbcdemo.singleset.domain.impl;

import com.jdbcdemo.singleset.domain.TUserDomain;
import com.jdbcdemo.singleset.repository.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TUserDomainImpl implements TUserDomain {
    public static final String DB_NAME = "t_user";

    private final static String[] EDIT_FIELD = {"name","status"};

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insert(HashMap<String, Object> params) throws Exception {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);

        List<String> fieldList = new ArrayList<>();
        List<String> parameterList = new ArrayList<>();
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        for(String f:EDIT_FIELD){
            if(params.containsKey(f)){
                fieldList.add(f);
                parameterList.add(":"+f);
                parameters.addValue(f,params.get(f));
            }
        }

        String sql = String.format("INSERT INTO %s (%s) value (%s)",DB_NAME,String.join(",",fieldList),String.join(",",parameterList));

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update( sql, parameters, keyHolder, new String[] {"id"});
        return keyHolder.getKey().intValue();
    }


    @Override
    public TUser getOne(Integer id) throws Exception {
        String sql = String.format("SELECT * FROM %s WHERE %s=%s limit 1",DB_NAME,"id",":id");
        try{
            Map<String, Object> params = Collections.singletonMap("id", id);
            NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
            return jdbc.queryForObject(sql, params, new BeanPropertyRowMapper<TUser>(TUser.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }


    @Override
    public int update(int id, HashMap<String, Object> condition) throws Exception {
        if (id <= 0){
            return 0;
        }

        if(condition.isEmpty()){
            return 0;
        }

        HashMap<String, Object> params = new HashMap<>();
        List<String> updateField = new ArrayList<>();

        String sql = "UPDATE " + DB_NAME + " SET ";

        for(String field:EDIT_FIELD){
            if(condition.containsKey(field)){
                updateField.add(String.format("%s=:%s",field,field));
                params.put(field,condition.get(field));
            }
        }

        if (params.isEmpty()){
            return 0;
        }

        sql += String.join(",",updateField);
        sql += " where id = :id ";

        params.put("id",id);

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        return jdbc.update(sql,params);
    }

    @Override
    public List<TUser> queryForList(HashMap<String, Object> infos, Integer page, Integer limit) throws Exception {
        return queryForList(infos, page, limit, "created_at", "desc");
    }

    @Override
    public List<TUser> queryForList(HashMap<String, Object> condition, Integer page, Integer limit, String orderBy, String desc) throws Exception {
        String sql = "SELECT * FROM " + DB_NAME;

        String where = _where(condition);

        sql = sql+where;

        if(orderBy != null) {
            sql += String.format(" order by %s %s ",orderBy,desc);
        }

        if(page != 0) {
            Integer start = (page - 1) * limit;
            sql += " limit "+start+","+limit;
        }

        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        return jdbc.query(sql, condition, new BeanPropertyRowMapper<TUser>(TUser.class));
    }

    @Override
    public int count(HashMap<String, Object> condition) throws Exception {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
        String sql = "select count(id) from " + DB_NAME;
        String where = _where(condition);
        sql = sql+where;

        return jdbc.queryForObject(sql,condition,Integer.class);
    }

    @Override
    public List<TUser> queryAll(HashMap<String, Object> condition) throws Exception {
        NamedParameterJdbcTemplate jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);

        String sql = "SELECT * FROM " + DB_NAME;

        String where = _where(condition);

        sql = sql+where;

        return jdbc.query(sql, condition, new BeanPropertyRowMapper<TUser>(TUser.class));
    }

    private String _where(HashMap<String, Object> condition){

        String where = " where 1=1 ";
        if(!condition.isEmpty()){
            for(Map.Entry<String, Object> entry : condition.entrySet()){
                String key = entry.getKey();

                if(key.equals("name")){
                    where +=  " and " + key + " like '%' :name '%'";
                }else if(entry.getValue() instanceof List){
                    where +=  " and " + key + " in (:"+key+") ";
                }else{
                    where +=  " and " + key + " = :"+key+" ";
                }
            }
        }

        return where;
    }
}
