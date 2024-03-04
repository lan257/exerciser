package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class api {
    int ApiId;//接口id
    String address;//接口地址
    String type;//接口请求类型
    String sort;//接口分类
    String getData;//接收数据
    String reData;//响应数据
    String errorData;//报错信息
    String use;//接口作用
    String other;//接口备注
}
