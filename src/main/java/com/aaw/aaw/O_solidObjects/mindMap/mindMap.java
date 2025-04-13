package com.aaw.aaw.O_solidObjects.mindMap;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class mindMap {
    @TableId(type = IdType.AUTO)
    Integer mapId;//思维导图id
    int uid;//所属用户
    String title;//思维导图名称
    String description;//简介
    boolean isPublic;//是否公开
    LocalDateTime createTime;//创建时间
    int stars;//收藏数
    int mainNode;//根节点
    @TableField(exist = false)
    node node;
}
