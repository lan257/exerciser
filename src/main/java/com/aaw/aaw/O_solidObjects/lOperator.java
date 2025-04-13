package com.aaw.aaw.O_solidObjects;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class lOperator {
    @TableId(type = IdType.AUTO)
    Integer lid;
    int oid,oType,tid,uid;

    public lOperator(int oid, int oType, int tid, int uid) {
        this.oid = oid;
        this.oType = oType;
        this.tid = tid;
        this.uid = uid;
    }
}
