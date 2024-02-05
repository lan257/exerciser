package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface userAccess {

    @Select("select uid from privateuser where (email=#{email} and password=#{password})")
    List<Integer> login(String email, String password);
    @Select("SELECT * FROM user WHERE (uid = #{uid} OR #{uid} = 0) AND (nickname REGEXP #{nickname}) AND (type = #{type} OR #{type} = 2)ORDER BY RAND() LIMIT 5")
    List<user> getUser(int uid, String nickname, int type);
    @Select("select * from user where (uid=#{pUid})")
    user getUser_Uid(int pUid);
//    @Select("select * from user where (nickname=#{nickname})")
//    List<user> getUser(int uid, String nickname, int type);
    @Insert("insert into privateuser (email, password) VALUE (#{email},#{password})")
    void pusign(String email, String password);
    @Insert("insert into user (uid,nickname,`change`,img) VALUE (#{uid}, #{nickname},#{change},#{img})")
    void usign(int uid, String nickname, String email,String img, String change);
//    @Select("select * from user")
    @Select("select * from user ORDER BY RAND() LIMIT 5")
    List<user> getUserall();
    @Select("select * from user where(uid=#{uid})")
    user getUserByUid(int uid);

    @Select("select nickname from user where(uid=#{uid})")
    user getNickname(int uid);
    @Select("select nickname,img from user where(uid=#{uid})")
    user getMore(int uid);
}
