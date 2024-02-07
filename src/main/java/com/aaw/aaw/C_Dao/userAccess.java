package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface userAccess {

    @Select("select uid from privateuser where (email=#{email} and password=#{password} and examine )")
    List<Integer> login(String email, String password);
    @Select("SELECT * FROM user WHERE ((uid = #{uid} OR #{uid} = 0) AND (nickname REGEXP #{nickname}) AND (type = #{type} OR #{type} = 2)and examine)ORDER BY RAND() LIMIT 5")
    List<user> getUser(int uid, String nickname, int type);
    @Select("select * from user where (uid=#{pUid})")
    user getUser_Uid(int pUid);
    @Insert("insert into privateuser (email, password) VALUE (#{email},#{password})")
    void pusign(String email, String password);
    @Insert("insert into user (uid,nickname,`change`,img) VALUE (#{uid}, #{nickname},#{change},#{img})")
    void usign(int uid, String nickname, String email,String img, String change);
//    @Select("select * from user")
    @Select("select * from user where (examine) ORDER BY RAND() LIMIT 5")
    List<user> getUserall();
    @Select("select * from user where(uid=#{uid})")
    user getUserByUid(int uid);

    @Select("select nickname from user where(uid=#{uid})")
    user getNickname(int uid);
    @Select("select nickname,img,uid from user where(uid=#{uid} and examine)")
    user getMore(int uid);
    @Update("UPDATE user SET fan = (SELECT COUNT(*) FROM loperator WHERE (loperator.oid = #{oid} and oType=1 and tid=1) )WHERE user.uid= #{oid}")
    void updateLove(int oid);
    @Update("UPDATE user SET love = ( SELECT SUM(love) FROM activity WHERE uid = #{uid} ) WHERE uid = #{uid}")
    void update(int uid);
}
