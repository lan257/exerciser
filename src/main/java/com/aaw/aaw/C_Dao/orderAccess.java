package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface orderAccess {
    @Insert("insert into `order` (did, uid, changeTime, address,num) VALUE (#{did},#{uid},#{ct},#{address},#{num})")
    void add(int did, int uid, String ct, String address, int num);
    @Select("select * from `order` where (uid=#{uid} and !del)")
    List<order> selectByUid(int uid);
    @Select("select * from `order`")
    List<order> selectAll();
    @Select("select * from `order` where (oid=#{oid} and !del)")
    order selectByOid(int oid);
    @Update("update `order` set finStatus = finStatus+1 where oid=#{oid}")
    void cIs(int oid);
    @Select("select * from `order` where (uid=#{uid} and !del and finStatus=0)")
    List<order> selectBuyCart(int uid);
    @Update("update `order` set del = not del where (oid=#{oid})")
    void del(int oid);
}
