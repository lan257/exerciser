package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ActAccess {
    @Insert("insert into activity (uid, changeTime, content, titleText, titleImg, type) VALUE (#{uid}," +
            "#{changeTime},#{content},#{titleText},#{titleImg},#{type})")
    void aConListSubmit(int uid, String changeTime, String content, String titleText, String titleImg, String type);

    @Select("select aid,uid,titleText,titleImg,com,love from activity where (examine) order by RAND() limit 10")
    List<activity> getActList();

    @Select("select * from activity  where(aid=#{aid} and examine) ")
    activity getAct(int aid);
    @Select("select uid from activity where (aid=#{aid} and examine)")
    int getActUid(int aid);
    @Select("select com from activity where(aid=#{aid} and examine) ")
    activity AddCom(int aid);

    @Update("update activity set com=#{com} where aid=#{aid}")
    void updateCom(int com ,int aid);

//    @Select("select love from activity where aid=#{oid}")
//    int getLove(int oid);
//
//    @Update("update activity set love=#{i} where aid=#{oid}")
//    void addLove(int i, int oid);
    @Update("UPDATE activity SET love = (SELECT COUNT(*) FROM loperator WHERE (loperator.oid = #{oid} and loperator.tid=1 and loperator.oType=2) )WHERE activity.aid= #{oid}")
    void updateLove(int oid);
    @Select("select aid,uid,titleText,titleImg,com,love from activity where(uid=#{uid} and examine)  order by changeTime desc")
    List<activity> getActListByUserSent(int uid);
    @Select("SELECT aid,activity.uid,titleText,titleImg,com,love from activity  JOIN loperator ON activity.Aid = loperator.oid WHERE (examine and loperator.uid = #{uid} and oType=2 and tid=1)")
    List<activity> getActListByUserLove(int uid);
    @Select("select aid,uid,titleText,titleImg,com,love from activity where(uid=#{uid} and examine=0)  order by changeTime desc")
    List<activity> getActListByExamain(int uid);
}
