package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.nodeLogic;
import com.aaw.aaw.O_solidObjects.mindMap.node;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class nodeController {
    @Autowired
    private nodeLogic nodeLogic;

    //根据父节点添加子节点
    @PostMapping("/aaw/nodeAdd")
    public Result nodeAdd(@RequestBody node node){
        nodeLogic.nodeAdd(node);
        return new Result(1,"添加成功","添加成功");
        /*优化方向：
        1.返回该思维导图的最新数据；
        2.数据修改直接在前端修改，后端不需要返回数据
        */
    }
    //增添关联性
    @PostMapping("/aaw/nodeConnection")
    public Result nodeConnection(@RequestBody node node,int n1){
        nodeLogic.nodeAdd(node);
        return new Result(1,"添加成功","添加成功");
        /*优化方向：
        1.返回该思维导图的最新数据；
        2.数据修改直接在前端修改，后端不需要返回数据
        */
    }
    //删除节点
    //修改节点
    //根据节点信息查询思维导图（Logic层另单独放置按id查询）
}
