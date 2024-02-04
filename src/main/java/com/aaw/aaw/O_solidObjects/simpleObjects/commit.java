package com.aaw.aaw.O_solidObjects.simpleObjects;

import com.aaw.aaw.O_solidObjects.user;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commit {
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
    public List<reply> GReply(){
        return new Gson().fromJson(reply, new TypeToken<List<reply>>(){}.getType());
    }
}
