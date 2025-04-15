package com.aaw.aaw.O_solidObjects.mindMap;

import com.aaw.aaw.B_Service.nodeMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("node")
public class node {
    @TableId(type = IdType.AUTO, value = "node_id")
    Integer nodeId;
    int mapId;//包含该节点的思维导图id
    String title;//节点标题
    String content;// 卡片信息
    LocalDateTime createTime;
    int wordSize,underLine,delLine;
    String wordStyle,wordColor;
    int leftValue,rightValue;//左，右节点id
    int preId,nextId;//双向关联节点链

    //--------------以上元素一一对应数据库内的元素
    //以下元素为转换成二叉树时需要的元素
    @TableField(exist = false)
    String time;
    public node setTime() {
        time= createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return this;
    }
    @TableField(exist = false)
    int x;
    @TableField(exist = false)
    node L,R;//左右节点
    @TableField(exist = false)
    List<node> children;
    // 转换方法
    public void convertToMultiTree() {
        this.children = new ArrayList<>();

        // 临时保存右兄弟指针防止被递归清理
        node currentChild = this.L;
        while (currentChild != null) {
            // 先保存下一个兄弟节点
            node nextSibling = currentChild.R;
            // 递归转换（此时R指针可能被修改）
            currentChild.convertToMultiTree();
            this.children.add(currentChild);
            // 使用预先保存的兄弟指针继续遍历
            currentChild = nextSibling;
        }
        // 最终清理当前节点的二叉树指针
        this.L = null;
        this.R = null;
    }


}
/*
 * 作为一棵树，这里在存储时选择转化成二叉树（孩子兄弟法）
 *
 * 展示时需要方法将该二叉树转化成树
 * 前端转化或者后端都可以
 *
 * 为满足节点关联的需求，采取双向循环链表，这里表现为link,
 * 最初link直接指向自己的id
 * 在节点A关联其他节点B时，将
 * B->pro->next=A->next,A->next=B,A->next->pro=B->pro,B->pro=A,
 *
 * */