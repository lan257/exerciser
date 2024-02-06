package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.chatLogic;
import com.aaw.aaw.O_solidObjects.chat;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.simpleObjects.chatList;
import com.aaw.aaw.O_solidObjects.user;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class chatController {
    @Autowired
    private chatLogic chatLogic;
    @PostMapping("/aaw/selectChat")
    public Result selectChat(@RequestBody chat c, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        c.setUserA(jwtInfo.getUid());
        c.createCid();
        chat s= chatLogic.selectChat(c);
        log.info(s+"");
        return new Result(1,"欸嘿",s);
    }
    @PostMapping("/aaw/addMsg")
    public Result addMsg(@RequestBody chat c){
        log.info(c.toString());
        chatLogic.updateChat(c);
        chat s= chatLogic.selectChat(c);
        log.info(s+"");

        return new Result(1,"欸嘿",s);
    }
    @PostMapping("/aaw/getChatList")
    public Result getChatList(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<chatList> chatList=chatLogic.getChatList(jwtInfo);
        return new Result(1,"查询成功",chatList);
    }
}
