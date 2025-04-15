package com.aaw.aaw.O_solidObjects.mindMap;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class comment {
    @TableId(value = "cid")
    Integer cid;
    Integer mid;
    int uid;
    String content;
    LocalDateTime createTime;
    @TableField(exist = false)
    String time;
    @TableField(exist = false)
    String nickName;
    public comment setTime() {
        time= createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return this;
    }
}
