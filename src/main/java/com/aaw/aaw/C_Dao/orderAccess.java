package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface orderAccess {
    @Insert("insert into `order` (did, uid, changeTime, address) VALUE (#{did},#{uid},#{ct},#{address})")
    void add(int did, int uid, String ct, String address);
    @Select("select * from `order` where (uid=#{uid})")
    List<order> selectByUid(int uid);
    @Select("select * from `order` ORDER BY RAND() LIMIT 5")
    List<order> selectAll();
    @Select("select * from `order` where (oid=#{oid})")
    order selectByOid(int oid);
    @Update("update `order` set isBuy = not isBuy where oid=#{oid}")
    void cIs(int oid);
}
