package com.aaw.aaw.O_solidObjects.mindMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class node {
    int node_id;
    int map_id;//包含该节点的思维导图id
    String card;// 卡片信息
    String sonNode;//子节点id
    LocalDateTime creat_time;
    int wordSize,underLine,delLine;
    String wordStyle,wordColor;
    List<node> nodeListSon;//子节点
    int[] nodeRelevance;//关联节点
    String nodeRSql;//关联节点同步sql
    void nodeRSql1(){
        nodeRSql= Arrays.toString(nodeRelevance);
    }
    void nodeRelevance1(){
        //将数组转字符串转回来
        String[] parts=nodeRSql.substring(1,nodeRSql.length()-1).split(",");
        for (int i = 0; i < parts.length; i++) {
            nodeRelevance[i]=Integer.parseInt(parts[i].trim());
        }
    }
}
