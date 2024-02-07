package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.VSmail;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VSAccess {

    @Insert("insert into videosmail (name, uid, `change`, also, url, type, vid, ins) value (#{name},#{uid},#{change},#{also},#{url},#{type},#{vid},#{ins})")
    void submit(String name, int uid, LocalDateTime change, String also, String url, int type, int vid, int ins);

    @Update("update videosmail set name=#{name},uid=#{uid},change=#{change},also=#{also},url=#{url},type=#{type},vid=#{vid},ins=#{ins}  where (sid=#{sid})")
    void update(String name, int uid, LocalDateTime change, String also, String url, int type, int vid, int ins, int sid);

    @Select("select * from videosmail where (vid=#{vid} and examine) order by name")
    List<VSmail> selectVS(int vid);
}
