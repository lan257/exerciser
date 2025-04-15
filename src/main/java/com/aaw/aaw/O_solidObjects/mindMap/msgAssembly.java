package com.aaw.aaw.O_solidObjects.mindMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class msgAssembly {
    Integer id;//id
    String type;//类型
    String title;//标题
    String content;//内容
    String nickName;//用户
    String time;//时间
    int MapId;//思维导图id
}
