package com.aaw.aaw.O_solidObjects;

import com.aaw.aaw.O_solidObjects.simpleObjects.reply;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//事件
//love++:1;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class commit {
    boolean like;
    user u;
    int cid;
    int aid;
    int uid;
    String com;
    String img;
    int love;
    String time;
    int replyNum;
    String reply;
    public List<com.aaw.aaw.O_solidObjects.simpleObjects.reply> GReply(){
        return new Gson().fromJson(reply, new TypeToken<List<reply>>(){}.getType());
    }
}
