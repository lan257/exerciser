package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.dishes;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface dishesAccess {

    @Insert("insert into dishes (name,price,et,uid) value (#{name},#{price},#{et},#{uid})")
    void add(String name, double price, String et, int uid);
    @Select("select * from dishes where (uid=#{uid} and del=0)")
    List<dishes> selectByUid(int uid);
//    @Select("select * from dishes where (isCanBuy=true) order by RAND() LIMIT 5 ")
    @Select("select * from dishes where (isCanBuy=true  and del=0)")
    List<dishes> selectAll();
    @Select("select * from dishes where (did=#{did}  and del=0)")
    dishes selectByDid(int did);
    @Update("update dishes set isCanBuy = not isCanBuy where (did=#{did})")
    void cIs(int did);
    @Update("update dishes set del = not del where (did=#{did})")
    void del(int did);
    @Update("update dishes set num = num+#{num} where (did=#{did})")
    void numAdd(int did, int num);
}
