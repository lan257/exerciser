package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.mindMapMapper;
import com.aaw.aaw.B_Service.nodeMapper;
import com.aaw.aaw.B_Service.userLogic;
import com.aaw.aaw.O_solidObjects.mindMap.msgAssembly;
import com.aaw.aaw.O_solidObjects.mindMap.node;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class nodeController {
    @Autowired
    private nodeMapper nodeMapper;
    @Autowired
    private mindMapMapper mindMapMapper ;
    @Autowired
    private userLogic userLogic;
    //根据父节点添加子节点
    /*
    * 检查父节点A左节点是否有值，如果无，直接父节点A.left=该节点id，
    * 如果有，查询该父节点A的左节点B的右节点是否有值，如果无，则B.right=id
    * 如果有，则继续查询B的右节点C的右节点，直到无，N.right=id
    */
    @Transactional
    @PostMapping("/aaw/node")
    public Result nodeAdd(@RequestBody node node){

        // 查询父节点
        node parent = nodeMapper.selectById(node.getX());
        node.setMapId(parent.getMapId());
        nodeMapper.insert(node);
        node.setPreId(node.getNodeId());
        node.setNextId(node.getNodeId());
        // 根据父节点添加子节点
        if(node.getX() == 0){
            // 如果X为0，表示该节点为根节点，直接返回成功
            return new Result(1, "添加成功", nodeMapper.updateById(node));
        }
        if(parent.getLeftValue() == 0){
            // 如果父节点没有左子节点
            parent.setLeftValue(node.getNodeId());
            nodeMapper.updateById(parent);
        } else {
            // 从父节点的左子节点开始遍历右链
            node current = nodeMapper.selectById(parent.getLeftValue());

            // 沿着右节点查找直到末端（注释中的B->C->N右节点遍历）
            while (current.getRightValue() != 0) {
                current = nodeMapper.selectById(current.getRightValue());
            }
            // 将新节点挂到当前节点的右子树
            current.setRightValue(node.getNodeId());
            // 只需要更新当前节点和新节点
            nodeMapper.updateById(current);
        }
        nodeMapper.updateById(node);
        return new Result(1, "添加成功", 1);

    }




    /*优化方向：
        1.返回该思维导图的最新数据；
        2.数据修改直接在前端修改，后端不需要返回数据
        */


    //增添关联性

    /*
     * 作为一棵树，这里在存储时选择转化成二叉树（孩子兄弟法）
     *
     * 展示时需要方法将该二叉树转化成树
     * 前端转化或者后端都可以
     *
     * 为满足节点关联的需求，采取双向循环链表，这里表现为link,
     * 最初link直接指向自己的id
     * 在节点A关联其他节点B时，将
     * B->pro->next=A->next,A->next=B,A->next->pro=B->pro,B->pro=A,
     *
     * */

    //关联节点,合同需要确认

        @Transactional
        public Result nodeConnection(Integer aid, Integer bid) {
            if(Objects.equals(aid, bid)){
                return new Result(0, "异常", "不能关联自己");
            }
            // 检查节点A是否存在
            node nodeA = nodeMapper.selectById(aid);
            if (nodeA == null) {
                return new Result(0, "异常", "节点A不存在");
            }

            // 检查节点B是否存在
            node nodeB = nodeMapper.selectById(bid);
            if (nodeB == null) {
                return new Result(0, "异常", "节点B不存在");
            }

            //检查是否已经关联
            List<msgAssembly> msgAssemblies = (List<msgAssembly>) nodeConnectionList(aid).getData();
            for (msgAssembly msgAssembly : msgAssemblies) {
                if (Objects.equals(msgAssembly.getId(), bid)) {
                    return new Result(0, "异常", "节点B已经关联过节点A");
                }
            }
            // 检查节点A的下一个节点是否存在
            node nodeANext = nodeMapper.selectById(nodeA.getNextId());
            if (nodeANext == null) {
                return new Result(0, "异常", "节点A的下一个节点不存在");
            }

            // 检查节点B的前一个节点是否存在
            node nodeBPro = nodeMapper.selectById(nodeB.getPreId());
            if (nodeBPro == null) {
                return new Result(0, "异常", "节点B的前一个节点不存在");
            }

            // 开始事务管理
            try {
                // 调整节点的关联关系
                nodeBPro.setNextId(nodeA.getNextId());
                nodeANext.setPreId(nodeBPro.getNodeId());
                nodeA.setNextId(nodeB.getNodeId());
                nodeB.setPreId(nodeA.getNodeId());

                // 更新数据库
                nodeMapper.updateById(nodeA);
                nodeMapper.updateById(nodeB);
                nodeMapper.updateById(nodeANext);
                nodeMapper.updateById(nodeBPro);

                return new Result(1, "关联成功", "关联成功");
            } catch (Exception e) {
                // 处理异常情况
                return new Result(0, "关联失败", e.getMessage());
            }
        /*优化方向：
        1.返回该思维导图的最新数据；
        2.数据修改直接在前端修改，后端不需要返回数据
        */
        }

        //查询节点关联列表
        @GetMapping("/aaw/node/connection/{nid}")
        public Result nodeConnectionList(@PathVariable int nid) {
            List<msgAssembly> msgAssemblies = new ArrayList<>();

            // 查询节点
            node node = nodeMapper.selectById(nid);
            if (node == null) {
                return new Result(0, "节点不存在", "节点不存在");
            }
            while(node.getNextId()!=nid){
                node=nodeMapper.selectById(node.getNextId());
                if (node == null) {
                    return new Result(0, "出现未知bug,关联节点不存在", "节点不存在");
                }
                node t=node;
                int uid=mindMapMapper.selectById(t.getMapId()).getUid();
                String nickName = userLogic.selectById(uid).getNickname();
                msgAssemblies.add(new msgAssembly(t.getNodeId(),"节点",t.getTitle(),t.getContent(),nickName,t.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),t.getMapId()));

            }
            return new Result(1, "查询成功", msgAssemblies);
        }
        //取消某节点关联，即查询链表并在链表中删除该节点
        public Result nodeConnectionDel(@PathVariable int nid) {
            // 查询节点
            node node = nodeMapper.selectById(nid);
            if (node == null) {
                return new Result(0, "节点不存在", "节点不存在");
            }
            // 开始事务管理
            try {
                // 调整节点的关联关系
                node nodeNext = nodeMapper.selectById(node.getNextId());
                node nodePre = nodeMapper.selectById(node.getPreId());
                nodeNext.setPreId(nodePre.getNodeId());
                nodePre.setNextId(nodeNext.getNodeId());
                node.setNextId(node.getNodeId());
                node.setPreId(node.getNodeId());
                // 更新数据库
                nodeMapper.updateById(nodeNext);
                nodeMapper.updateById(nodePre);
                nodeMapper.updateById(node);
                return new Result(1, "取消关联成功", "取消关联成功");
            } catch (Exception e) {
                // 处理异常情况
                return new Result(0, "取消关联失败", e.getMessage());
        }
        }


    //删除节点
    @DeleteMapping("/aaw/node/{nid}")
    public Result nodeDel(@PathVariable int nid){
        // 查询节点
        node node=nodeMapper.selectById(nid);
        //将数据库中right_value列为nid的节点的right_value值设置为node.getRightValue()
        UpdateWrapper<node> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("right_value", nid) // 根据right_value进行匹配
                .set("right_value", node.getRightValue()); // 设置要更新的right_value字段
        nodeMapper.update(null, updateWrapper);
        updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("left_value", nid)
                .set("left_value",node.getRightValue());
        nodeMapper.update(null, updateWrapper);
        //将node的左子树全部删除
        if (node.getLeftValue()!=0){
        deleteNode(node.getLeftValue());}
        nodeConnectionDel(nid);//节点关联取消
        nodeMapper.deleteById(nid);
        return new Result(1,"删除成功","删除成功");
    }
    //修改节点
    @PutMapping("/aaw/node")
    public Result nodeDel(@RequestBody node node){
        UpdateWrapper<node> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("node_id", node.getNodeId()) // 根据ID进行匹配
                .set("content", node.getContent()) // 设置要更新的card字段
                .set("title", node.getTitle()); // 设置要更新的title字段
        nodeMapper.update(null, updateWrapper);
        return new Result(1,"修改成功","修改成功");
    }
    //将node的左子树和右子树全部删除
    public void deleteNode(int nid){
        node node=nodeMapper.selectById(nid);
        if(node.getLeftValue()!=0){
            deleteNode(node.getLeftValue());

        }
        if(node.getRightValue()!=0){
            deleteNode(node.getRightValue());
        }
        nodeMapper.deleteById(nid);
    }


}
