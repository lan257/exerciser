package com.aaw.aaw.O_solidObjects;

import com.aaw.aaw.O_solidObjects.simpleObjects.msg;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class chat {
    int chatId;
    int userA;
    int userB;
    String msgData;//聊天记录
    String finMsg;//最后一次聊天记录

    public void SETFinMsg(msg finMsg) {
        this.finMsg = new Gson().toJson(finMsg);
    }

    public msg getData() {
        return new Gson().fromJson(finMsg, new TypeToken<msg>(){}.getType());
    }

    public chat(int userA, int userB, String msgData) {
        this.userA = userA;
        this.userB = userB;
        this.msgData = msgData;
        createCid();
    }
    public void createCid(){
        chatId=userA>userB ? Objects.hash(userA,userB):Objects.hash(userB,userA);
    }
    public void  addMsgData(){
        toMsgData(addMsg(getMsg()));
    }
    List<msg> getMsg(){
        if (msgData!=null  && !msgData.isEmpty()){
            return new Gson().fromJson(msgData, new TypeToken<List<msg>>(){}.getType());
        }
        return new ArrayList<>();
    }
    List<msg> addMsg(List<msg> msgList){
        msgList.add(getData());
        return msgList;
    }
    void toMsgData(List<msg> msgList){
        msgData=new Gson().toJson(msgList);
    }
}
