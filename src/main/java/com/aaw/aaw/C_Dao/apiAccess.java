package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.api;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface apiAccess {

    @Select("select * from api where (ApiId=#{apiId})")
    api getApiById(int apiId);

    @Select("select * from api")
    List<api> getApi();

    @Insert("insert into api (address, type, sort, getData, reData, errorData, `use`, other) VALUE (#{address},#{type},#{sort},#{getData},#{reData},#{errorData},#{use},#{other})")
    void addApi(String address, String type, String sort, String getData, String reData, String errorData, String use, String other);

    @Update("update api set address=#{address},type=#{type},sort=#{sore},getData=#{getData},reData=#{reData},errorData=#{errorData},`use`=#{use},other=#{other} where(ApiId=#{apiId}) ")
    void updateApi(int apiId, String address, String type, String sort, String getData, String reData, String errorData, String use, String other);

    @Delete("delete from api where(ApiId= #{apiId})")
    void del(int apiId);
}
