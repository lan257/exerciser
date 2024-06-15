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
    void add(int name, int price, String et, int uid);
    @Select("select * from dishes where (uid=#{uid})")
    List<dishes> selectByUid(int uid);
    @Select("select * from dishes ORDER BY RAND() LIMIT 5")
    List<dishes> selectAll();
    @Select("select * from dishes where (did=#{did})")
    dishes selectByDid(int did);
    @Update("update dishes set isCanBuy = not isCanBuy where did=#{did}")
    void cIs(int did);
}
