package com.aaw.aaw.O_solidObjects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class video {
    int vid;
    String vName;
    String vIntro="暂无";//简介
    String vUrl="暂无";
    String vCreate;
    String vChange;
    int uid;
    String img;
    int ins;//权限
}
