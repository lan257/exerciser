package com.aaw.aaw.C_Dao;

import com.aaw.aaw.O_solidObjects.chat;
import com.aaw.aaw.O_solidObjects.simpleObjects.msg;
import com.aaw.aaw.O_solidObjects.user;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface chatAccess {
    @Select("select * from chat where (chatId=#{chatId})")
    chat selectChat(int chatId);

    @Insert("insert into chat (chatId, userA, userB, msgData,finMsg) VALUE (#{chatId},#{userA},#{userB},#{msgData},#{finMsg}) ")
    void insertChat(int chatId, int userA, int userB,String msgData, String finMsg);
    @Update("update chat set msgData=#{msgData},finMsg=#{finMsg}where(chatId=#{chatId})")
    void update(String msgData, String finMsg, int chatId);

    @Select("select userA,userB,finMsg from chat where ((userA=#{uid}) or (userB=#{uid}))")
    List<chat> getChatList(int uid);

    @Select("select img,nickname,uid from user where (uid=#{user})")
    user getUserByUid2(int user);
}