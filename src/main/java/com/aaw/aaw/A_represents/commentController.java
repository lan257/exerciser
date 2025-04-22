package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.contractMapper;
import com.aaw.aaw.B_Service.userLogic;
import com.aaw.aaw.O_solidObjects.mindMap.comment;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.aaw.aaw.B_Service.commentMapper;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = {"*"})
@Service
@RestController
@Slf4j
public class commentController {
    @Autowired
    private commentMapper commentMapper;
    @Autowired
    private userLogic userLogic;

    //注册（添加评论）
    @PostMapping("/aaw/comment")
    @Transactional
    public Result commentAdd(@RequestBody comment comment, HttpServletRequest request) {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        comment.setUid(jwtInfo.getUid());
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.insert(comment);
        return new Result(1, "批注成功", commentList(comment.getMid()));
    }
    //获取评论列表
    @GetMapping("/aaw/comment/{mid}")
    public Result commentList(@PathVariable("mid") Integer mid) {
        List<comment> comments = commentMapper.selectList(new QueryWrapper<comment>().eq("mid", mid));
        //设置评论时间
        for (comment c : comments) {
            c.setTime();
            c.setNickName(userLogic.selectById(c.getUid()).getNickname());
        }
        return new Result(1, "获取成功", comments);
    }
    //删除评论
    @DeleteMapping("/aaw/comment/{cid}")
    public Result commentDelete(@PathVariable("cid") Integer cid) {
        commentMapper.deleteById(cid);
        return new Result(1, "删除成功", "删除成功");
    }
}
