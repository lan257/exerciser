package com.aaw.aaw.C_Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface mindMapAccess {
    @Insert("insert into mind_map (uid, title, description, is_public, create_time,mainNode) value (#{uid},#{title},#{description},#{Public},#{now},#{mainNode})")
    int mapAdd(int uid, String title, String description, boolean Public, LocalDateTime now, int mainNode);

    @Delete("delete from mind_map where map_id=#{mapId}")
    void mapDel(int mapId);
}
