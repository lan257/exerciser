package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.user;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface userAccess {

    @Select("select uid from privateuser where (email=#{email}&&password=#{password})")
    List<Integer> login(String email, String password);

    @Select("select * from user where (uid=#{pUid})")
    user getUser_Uid(int pUid);
}
