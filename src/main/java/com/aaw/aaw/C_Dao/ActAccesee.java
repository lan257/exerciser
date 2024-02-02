package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.activity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActAccesee {
    @Insert("insert into activity (uid, changeTime, content, titleText, titleImg, type) VALUE (#{uid}," +
            "#{changeTime},#{context},#{titleText},#{titleImg},#{type})")
    void aConListSubmit(int uid, String changeTime, String context, String titleText, String titleImg, String type);

    @Select("select uid,titleText,titleImg from activity order by RAND() limit 10")
    List<activity> getActList();
}
