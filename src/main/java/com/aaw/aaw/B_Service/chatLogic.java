package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.chatAccess;
import com.aaw.aaw.O_solidObjects.chat;
import com.aaw.aaw.O_solidObjects.simpleObjects.chatList;
import com.aaw.aaw.O_solidObjects.simpleObjects.msg;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RestController
public class chatLogic implements Logic{
    @Autowired
    private chatAccess CA;
    public chat selectChat(chat c) {
       chat chat = CA.selectChat(c.getChatId());

       if (chat==null){
           String time= LocalDateTime.now().toString();
           msg msg=new msg(c.getUserA(),"你好，我们现在可以开始私聊了",time);
           c.SETFinMsg(msg);
           LocalDateTime CChange=LocalDateTime.now();
           c.addMsgData();
           CA.insertChat(c.getChatId(),c.getUserA(),c.getUserB(),c.getMsgData(),c.getFinMsg(),CChange.toString());
           chat = CA.selectChat(c.getChatId());
       }
           log.info(chat+"");
           return chat;
    }

    public void updateChat(chat c) {
        log.info(c.toString());
        CA.update(c.getMsgData(),c.getFinMsg(),c.getChatId());
    }

    public List<chatList> getChatList(user jwtInfo) {
        List<chat> chatList= CA.getChatList(jwtInfo.getUid());
        List<chatList> chatListList=new ArrayList<>();
        for (chat c:chatList
             ) {
            user u=CA.getUserByUid2(c.getUserB()!=jwtInfo.getUid()?c.getUserB():c.getUserA());
            chatListList.add(new chatList(c.getData(),u));
        }
        return chatListList;
    }

}
