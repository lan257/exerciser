package com.aaw.aaw.C_Dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface nodeAccess {
    //添加在父节点下node节点
    @Insert("insert into node(map_id, card, creat_time, wordSize, underLine, wordStyle, wordColor, delLine) value (#{mapId},#{card},#{now},#{wordSize},#{underLine},#{delLine},#{wordStyle},#{wordColor})")
    void nodeAdd(int mapId, String card, LocalDateTime now, int wordSize, int underLine, int delLine, String wordStyle, String wordColor);

}
