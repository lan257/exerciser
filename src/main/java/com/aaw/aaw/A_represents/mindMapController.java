package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.lOperatorMapper;
import com.aaw.aaw.B_Service.mindMapMapper;
import com.aaw.aaw.B_Service.nodeMapper;
import com.aaw.aaw.O_solidObjects.lOperator;
import com.aaw.aaw.O_solidObjects.mindMap.comment;
import com.aaw.aaw.O_solidObjects.mindMap.mindMap;
import com.aaw.aaw.O_solidObjects.mindMap.msgAssembly;
import com.aaw.aaw.O_solidObjects.mindMap.node;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.aaw.aaw.B_Service.commentMapper;
import com.aaw.aaw.B_Service.userLogic;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private commentMapper commentMapper;
    @Autowired
    private userLogic userLogic;


    //创建思维导图并添置根节点
    @PostMapping("/aaw/mindMap")
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
        node.setCreateTime(now());
        nodeMapper.insert(node);
        node.setPreId(node.getNodeId());
        node.setNextId(node.getNodeId());
        mindMap.setMainNode(node.getNodeId());
        mindMap.setNode(node);
        log.info("mindMap.toString=" + mindMap);
        nodeMapper.updateById(node);
        mindMapMapper.updateById(mindMap);
        return new Result(1,"创建成功",mindMap.setTime());
    }

    //修改思维导图。
    @PutMapping("/aaw/mindMap")
    public Result mapUpdate(@RequestBody mindMap mind_map){
        return new Result(1,"修改成功",mindMapMapper.updateById(mind_map));
    }

    //增加收藏数。交付给iOperator处理
    @GetMapping("/aaw/mindMap/love+-/{mid}")
    @Transactional
    public Result love(@PathVariable int mid, HttpServletRequest request) {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        // 加工数据
        lOperator lOperator = new lOperator(mid, 4, 1, jwtInfo.getUid());
        QueryWrapper<lOperator> wrapper = new QueryWrapper<>();
        wrapper.eq("oid", lOperator.getOid())
                .eq("oType", lOperator.getOType())
                .eq("tid", lOperator.getTid())
                .eq("uid", lOperator.getUid());
        long x = lOperatorMapper.selectCount(wrapper);
        int y = x > 0 ? lOperatorMapper.delete(wrapper) : lOperatorMapper.insert(lOperator);
        wrapper = new QueryWrapper<>();
        wrapper.eq("oid", mid)
                .eq("oType", 4)
                .eq("tid", 1);
        long z= lOperatorMapper.selectCount(wrapper);
        UpdateWrapper<mindMap> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("map_id", mid)
                .set("stars", z);
        mindMapMapper.update(updateWrapper);
        return new Result(1, "修改成功", x > 0 ? "取消收藏" : "收藏成功");
    }

    //增加标记数。交付给iOperator处理
    @GetMapping("/aaw/mindMap/mark+-/{mid}")
    @Transactional
    public Result mark(@PathVariable int mid , HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工数据
        lOperator lOperator = new lOperator(mid, 4, 2, jwtInfo.getUid());
        //加工数据
        QueryWrapper<lOperator> wrapper = new QueryWrapper<>();
        wrapper.eq("oid", lOperator.getOid())
                .eq("oType", lOperator.getOType())
                .eq("tid", lOperator.getTid())
                .eq("uid", lOperator.getUid());
        long x = lOperatorMapper.selectCount(wrapper);
        int y = x > 0 ? lOperatorMapper.delete(wrapper) : lOperatorMapper.insert(lOperator);
        return new Result(1, "修改成功", x > 0 ? "取消标记" : "标记成功");
    }

    //查询思维导图（按id查询）
    @GetMapping("/aaw/mindMap/Mid/{mid}")
    public Result selectByMid(@PathVariable int mid, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        mindMap t= mindMapMapper.selectById(mid).setTime();
        t.setStar(lOperatorMapper.selectCount(new QueryWrapper<lOperator>().eq("oid", t.getMapId()).eq("oType", 4).eq("tid", 1).eq("uid", jwtInfo.getUid())) > 0);
        t.setMark(lOperatorMapper.selectCount(new QueryWrapper<lOperator>().eq("oid", t.getMapId()).eq("oType", 4).eq("tid", 2).eq("uid", jwtInfo.getUid())) > 0);
        return new Result(1,"查找成功",t);
    }
    //查询思维导图（按star查询）
    @GetMapping("/aaw/mindMap/stars")
    public Result selectByStars( HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        lOperator lOperator = new lOperator(0,4,1,jwtInfo.getUid());
        QueryWrapper<lOperator> wrapper= new QueryWrapper<lOperator>().eq("oType",lOperator.getOType()
        ).eq("tid",lOperator.getTid()).eq("uid",lOperator.getUid());
        List<lOperator> lOperators = lOperatorMapper.selectList(wrapper);
        List<Long> oids = lOperators.stream().map(lOperator::getOid).collect(Collectors.toList());
        if (oids.isEmpty()) {
            return new Result(1, "查找成功", Collections.emptyList());
        }
        List<mindMap> mindMaps = mindMapMapper.selectByIds(oids);
        return new Result(1, "查找成功", setTime(mindMaps,jwtInfo.getUid()));
    }
    //查询思维导图（按标记查询）
    @GetMapping("/aaw/mindMap/marks")
    public Result selectByMark(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        lOperator lOperator = new lOperator(0,4,2,jwtInfo.getUid());
        QueryWrapper<lOperator> wrapper= new QueryWrapper<lOperator>().eq("oType",lOperator.getOType()
        ).eq("tid",lOperator.getTid()).eq("uid",lOperator.getUid());
        List<lOperator> lOperators = lOperatorMapper.selectList(wrapper);
        List<Long> oids = lOperators.stream().map(lOperator::getOid).collect(Collectors.toList());
        // 检查oids列表是否为空
        if (oids.isEmpty()) {
            return new Result(1, "查找成功", Collections.emptyList());
        }
        List<mindMap> mindMaps = mindMapMapper.selectByIds(oids);
        return new Result(1, "查找成功", setTime(mindMaps,jwtInfo.getUid()));
    }

    //查询思维导图（查询登录用户所有）
    @GetMapping("/aaw/mindMap/mine")
    public Result selectByUser(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        return new Result(1,"查找成功",setTime(mindMapMapper.selectList(new QueryWrapper<mindMap>().eq("uid",jwtInfo.getUid())),jwtInfo.getUid()));
    }
    //查询思维导图（按用户id查询）
    @GetMapping("/aaw/mindMap/Uid/{uid}")
    public Result selectByUid(@PathVariable int uid, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<mindMap> mindMaps = mindMapMapper.selectList(new QueryWrapper<mindMap>().eq("uid",uid));
        return new Result(1,"查找成功",setTime(mindMaps,jwtInfo.getUid()) );
    }
    //查询推荐的思维导图
    @GetMapping("/aaw/mindMap/Rec")
    public Result selectByMid(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<mindMap> mindMaps = mindMapMapper.selectList(null);
        return new Result(1,"查找成功",setTime(mindMaps,jwtInfo.getUid()));
    }
    //查询思维导图（按node查询）
    @GetMapping("/aaw/mindMap/Nid/{nid}")
    public Result selectByNid(@PathVariable int nid , HttpServletRequest request){
        return selectByMid(nodeMapper.selectById(nid).getMapId(),request);
    }
    //特定热门思维导图
    @GetMapping("/aaw/mindMap/Hot")
    public Result selectByHot(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //根据stars排序,取前十个
        List<mindMap> mindMaps = mindMapMapper.selectList(new QueryWrapper<mindMap>().orderByDesc("stars").last("LIMIT 10"));
        return new Result(1,"查找成功",setTime(mindMaps,jwtInfo.getUid()));
    }
    //查询树结构（思维导图id）
    @GetMapping("/aaw/nodeTree/Mid/"+"{mid}")
    public Result getNodeByMid(@PathVariable int mid) {
        mid=mindMapMapper.selectById(mid).getMainNode();
        return getMindMap(mid);
    }



    //强大的查询功能（模糊查询更多数据）
    @GetMapping("/aaw/anything/{title}")
    public Result searchAll(@PathVariable String title ){
        List<mindMap> mindMaps = mindMapMapper.selectList(new QueryWrapper<mindMap>().like("title",title).or().like("content",title));
        List<node> nodes = nodeMapper.selectList(new QueryWrapper<node>().like("title",title).or().like("content",title));
        List<comment> comments = commentMapper.selectList(new QueryWrapper<comment>().like("content",title));
        List<msgAssembly> msgAssemblies = new ArrayList<>();
        for (mindMap t : mindMaps){
            msgAssemblies.add(new msgAssembly(t.getMapId(),"思维导图",t.getTitle(),t.getContent(),userLogic.selectById(t.getUid()).getNickname(),t.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),t.getMapId()));
        }
        for (node t : nodes){
            int uid=mindMapMapper.selectById(t.getMapId()).getUid();
            String nickName = userLogic.selectById(uid).getNickname();
            msgAssemblies.add(new msgAssembly(t.getNodeId(),"节点",t.getTitle(),t.getContent(),nickName,t.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),t.getMapId()));
        }
        for (comment t : comments){
            String nickName = userLogic.selectById(t.getUid()).getNickname();
            msgAssemblies.add(new msgAssembly(t.getCid(), "批注",nickName,t.getContent(),userLogic.selectById(t.getUid()).getNickname(),t.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),t.getMid()));
        }
        return new Result(1,"查找成功",msgAssemblies);
    }

//模糊查询（只有节点）
//    @GetMapping("/aaw/node/search/{title}")
//    public Result searchNode(@PathVariable String title ) {
//        List<node> nodes = nodeMapper.selectList(new QueryWrapper<node>().like("title", title).or().like("card", title));
//        for (node t : nodes) {t.setTime();}
//        return new Result(1, "查找成功", nodes);
//    }

    //查询根节点（节点id）
    public int getMainNode(int nid){
         node node= nodeMapper.selectById(nid);
         mindMap mindMap=mindMapMapper.selectById(node.getMapId());
        return mindMap.getMainNode() ;
    }
    //根据节点信息查询思维导图树结构
    @GetMapping("/aaw/nodeTree/Nid/{nid}")
    public Result getMindMap(@PathVariable int nid){
        nid=getMainNode(nid);
        node rootNode=nodeMapper.selectById(nid);
        node node = buildBinaryTree(rootNode);
        log.info("node.toString=" + node);
        node.convertToMultiTree();
        node.setTime();
        return new Result(1,"返回思维导图",node);
    }
    //删除思维导图。
    @DeleteMapping("/aaw/mindMap/{mindId}")
    public Result mapDel(@PathVariable int mindId){
        nodeMapper.delete(new QueryWrapper<node>().eq("map_id",mindId));
        return new Result(1,"删除成功",mindMapMapper.deleteById(mindId));
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
    public List<mindMap> setTime(List<mindMap> list,int uid){
        for (mindMap t : list) {
            t.setNickName(userLogic.selectById(t.getUid()).getNickname());
            t.setTime();
            t.setStar(lOperatorMapper.selectCount(new QueryWrapper<lOperator>().eq("oid", t.getMapId()).eq("oType", 4).eq("tid", 1).eq("uid", uid)) > 0);
            t.setMark(lOperatorMapper.selectCount(new QueryWrapper<lOperator>().eq("oid", t.getMapId()).eq("oType", 4).eq("tid", 2).eq("uid", uid)) > 0);
        }
        return list;
    }
}
