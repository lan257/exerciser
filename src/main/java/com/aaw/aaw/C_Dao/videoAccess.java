package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.video;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface videoAccess {
    @Insert("insert into video (vName, vIntro, vUrl,vChange,uid, img, ins) value (#{vName},#{vIntro},#{vUrl},#{vChange},#{uid},#{img},#{ins})")
    void submit(String vName, String vIntro, String vUrl, LocalDateTime vChange, int uid, String img, int ins);
@Select("select * from video ")
    List<video> getVideoAll();
    @Select("SELECT * FROM video WHERE (vid = #{vid} OR #{vid} = 0) AND (video.vName REGEXP #{vName}) AND (uid = #{uid} or #{uid} = 1)")
    List<video> getVideo(int vid, String vName, int uid);

    @Select("select * from video where (vid=#{vid})")
    video getVideoByVid(int vid);
@Update("update video set vName=#{vName},vIntro=#{vIntro},vChange=#{vChange},uid=#{uid},img=#{img},ins=#{ins}  where (vid=#{vid})")
    void update(String vName, String vIntro, LocalDateTime vChange, int uid, String img, int ins,int vid);
}
