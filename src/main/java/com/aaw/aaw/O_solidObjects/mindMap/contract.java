package com.aaw.aaw.O_solidObjects.mindMap;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//合同，本质是需要其他人确认的事件
@Data
@AllArgsConstructor
@NoArgsConstructor
public class contract {
    Integer cid;//合同编号
    Integer aid, bid;//合同对象（节点）id
    Integer type;//合同类型
    int au;
    int bu;//合同方是否确认,默认为合同用户的uid
}
//注册合同类型
//1.节点关联合同
//2.修改密码合同
//3.内容审核合同