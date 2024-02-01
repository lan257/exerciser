package com.aaw.aaw.C_Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ActAccesee {
    @Insert("insert into activity (uid, changeTime, content, titleText, titleImg, type) VALUE (#{uid}," +
            "#{changeTime},#{context},#{titleText},#{titleImg},#{type})")
    void aConListSubmit(int uid, String changeTime, String context, String titleText, String titleImg, String type);
}
