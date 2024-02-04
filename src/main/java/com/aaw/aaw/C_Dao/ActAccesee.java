package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ActAccesee {
    @Insert("insert into activity (uid, changeTime, content, titleText, titleImg, type) VALUE (#{uid}," +
            "#{changeTime},#{content},#{titleText},#{titleImg},#{type})")
    void aConListSubmit(int uid, String changeTime, String content, String titleText, String titleImg, String type);

    @Select("select aid,uid,titleText,titleImg,com,love from activity order by RAND() limit 10")
    List<activity> getActList();

    @Select("select * from activity where aid=#{aid}")
    activity getAct(int aid);

    @Select("select com from activity where aid=#{aid}")
    activity AddCom(int aid);

    @Update("update activity set com=#{com} where aid=#{aid}")
    void updateCom(int com ,int aid);
}
