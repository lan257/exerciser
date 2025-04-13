package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.lOperatorMapper;
import com.aaw.aaw.B_Service.mindMapMapper;
import com.aaw.aaw.B_Service.nodeMapper;
import com.aaw.aaw.O_solidObjects.lOperator;
import com.aaw.aaw.O_solidObjects.mindMap.mindMap;
import com.aaw.aaw.O_solidObjects.mindMap.node;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import static java.time.LocalDateTime.now;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
@Service
public class mindMapController {
    @Autowired
    private mindMapMapper mindMapMapper;
    @Autowired
    private nodeMapper nodeMapper;
    @Autowired
    private lOperatorMapper lOperatorMapper;
    //创建思维导图并添置根节点
    @PostMapping("/aaw/mindMapAdd")
    @Transactional
    public Result mapAdd(@RequestBody mindMap mindMap, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        mindMap.setUid(jwtInfo.getUid());
        mindMap.setMainNode(1);
        mindMap.setCreateTime(now());
        mindMap.setStars(0);
        mindMapMapper.insert(mindMap);
        node node=new node();
        node.setTitle(mindMap.getTitle());
        node.setMapId(mindMap.getMapId());
        node.setCreatTime(now());
        nodeMapper.insert(node);
        node.setPreId(node.getNodeId());
        node.setNextId(node.getNodeId());
        mindMap.setMainNode(node.getNodeId());
        mindMap.setNode(node);
        log.info("mindMap.toString=" + mindMap);
        nodeMapper.updateById(node);
        mindMapMapper.updateById(mindMap);
        return new Result(1,"创建成功",mindMap);
    }
    //删除思维导图。
    @PostMapping("/aaw/mindMapDel")
    public Result mapDel(int mindId){
        return new Result(1,"删除成功",mindMapMapper.deleteById(mindId));
    }
    //修改思维导图。
    @PostMapping("/aaw/mindMapUpdate")
    public Result mapUpdate(@RequestBody mindMap mind_map){
        return new Result(1,"修改成功",mindMapMapper.updateById(mind_map));
    }
    //增加收藏数。交付给iOperator处理
    @PostMapping("/aaw/mindMap/love+-")
    @Transactional
    public Result love(int mid , HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工数据
        lOperator lOperator = new lOperator(mid,4,1,jwtInfo.getUid());
        QueryChainWrapper<lOperator> wrapper= new QueryChainWrapper<>(lOperatorMapper).ge("oid",lOperator.getOid()).ge("oType",lOperator.getOType()
        ).ge("tid",lOperator.getTid()).eq("uid",lOperator.getUid());
        int x = lOperatorMapper.selectOne(wrapper).getUid();
        x = x>0?lOperatorMapper.deleteById(x):lOperatorMapper.insert(lOperator);
        return new Result(1,"修改成功",x);
    }
    //查询思维导图（按id查询）
    @GetMapping("/aaw/mindMap/selectByMid/{mid}")
    public Result selectByMid(@PathVariable int mid){
        return new Result(1,"查找成功",mindMapMapper.selectById(mid));
    }
    //查询思维导图（查询登录用户所有）
    @GetMapping("/aaw/mindMap/selectByUser")
    public Result selectByUser(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        return new Result(1,"查找成功",mindMapMapper.selectList(new QueryWrapper<mindMap>().eq("uid",jwtInfo.getUid())));
    }
    //查询思维导图（按用户id查询）
    @GetMapping("/aaw/mindMap/selectByUid/{uid}")
    public Result selectByUid(@PathVariable int uid){
        return new Result(1,"查找成功",mindMapMapper.selectList(new QueryWrapper<mindMap>().eq("uid",uid)));
    }
    @GetMapping("/aaw/mindMap/selectByMid")
    public Result selectByMid(){
        return new Result(1,"查找成功",mindMapMapper.selectList(null));
    }
    //查询思维导图（按node查询）
    @GetMapping("/aaw/mindMap/selectByNid/{nid}")
    public Result selectByNid(@PathVariable int nid){
        return selectByMid(nodeMapper.selectById(nid).getMapId());
    }
    //特定查询思维导图

    //查询树结构（思维导图id）
    @GetMapping("/aaw/node/getByMid/"+"{mid}")
    public Result getNodeByMid(@PathVariable int mid) {
        mid=mindMapMapper.selectById(mid).getMainNode();
        return getMindMap(mid);
    }

    //查询根节点（节点id）
    public int getMainNode(int nid){
         node node= nodeMapper.selectById(nid);
         mindMap mindMap=mindMapMapper.selectById(node.getMapId());
        return mindMap.getMainNode() ;
    }
    //根据节点信息查询思维导图树结构（Logic层另单独放置按id查询）
    @GetMapping("/aaw/node/mindMap")
    public Result getMindMap(int nid){
        nid=getMainNode(nid);
        node rootNode=nodeMapper.selectById(nid);
        node node = buildBinaryTree(rootNode);
        log.info("node.toString=" + node);
        node.convertToMultiTree();
        return new Result(1,"返回思维导图",node);
    }
    public node buildBinaryTree(node rootNode) {
        if (rootNode == null) {
            return null;
        }
        // 递归构建左子树
        if (rootNode.getLeftValue() != 0) {
            node leftChild = nodeMapper.selectById(rootNode.getLeftValue());
            rootNode.setL(buildBinaryTree(leftChild));
        }
        // 递归构建右子树
        if (rootNode.getRightValue() != 0) {
            node rightChild = nodeMapper.selectById(rootNode.getRightValue());
            rootNode.setR(buildBinaryTree(rightChild)) ;
        }
        return rootNode;
    }
}
