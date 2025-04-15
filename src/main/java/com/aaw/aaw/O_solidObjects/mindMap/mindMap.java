package com.aaw.aaw.O_solidObjects.mindMap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class mindMap {
    @TableId(type = IdType.AUTO)
    Integer mapId;//思维导图id
    int uid;//所属用户
    String title;//思维导图名称
    String content;//简介
    boolean isPublic;//是否公开
    LocalDateTime createTime;//创建时间
    int stars;//收藏数
    int mainNode;//根节点
    @TableField(exist = false)
    node node;
    @TableField(exist = false)
    boolean isStar,isMark;//是否收藏,是否标记

    @TableField(exist = false)
    String nickName;//用户昵称

    @TableField(exist = false)
    String time;
    public mindMap setTime() {
        time= createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return this;
    }
}
