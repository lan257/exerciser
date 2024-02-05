package com.aaw.aaw.O_solidObjects;

import com.aaw.aaw.O_solidObjects.simpleObjects.aCon;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class activity {
    boolean loveIs;
    user u;
    int aid;
    int uid;
    String changeTime;//DateTime转化成String
    String createTime;//读取DateTime
    String content;//aCon类的列表
    String titleText;
    String titleImg;//一张图片的aCon
    String type;//标签
    int com;
    int love;
    public aCon GTitleImg(){ return new Gson().fromJson(titleImg, aCon.class);}
    public void STitleImg(aCon aCon){titleImg=new Gson().toJson(aCon);}
    public void addCom(){com++;}
    public void addLove(){love++;}
    //注入修改时间
    public void SChangeTime(){
        changeTime= LocalDateTime.now().toString();
    }
    //设置活动内容
    public void SContent(List<aCon> aConList){
        content=new Gson().toJson(aConList);
    }
    //获得活动内容
    public List<aCon> GContent(){
        return new Gson().fromJson(content, new TypeToken<List<aCon>>(){}.getType());
    }
}
