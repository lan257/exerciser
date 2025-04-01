package com.aaw.aaw.O_solidObjects.mindMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class mind_map {
    int map_id;//思维导图id
    int uid;//所属用户
    String title;//思维导图名称
    String description;//简介
    boolean is_public;//是否公开
    LocalDateTime creat_time;//创建时间
    int stars;//收藏数
    int mainNode;//根节点
    node node;
}
