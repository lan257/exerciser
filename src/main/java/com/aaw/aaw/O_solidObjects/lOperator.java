package com.aaw.aaw.O_solidObjects;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor

@TableName("loperator")
public class lOperator {
    @TableId(type = IdType.AUTO)
    Integer lid;
    @TableField("oType")
    int oType;
    int oid,tid,uid;

    public lOperator(int oid, int oType, int tid, int uid) {
        this.oid = oid;
        this.oType = oType;
        this.tid = tid;
        this.uid = uid;
    }

    public Long getOid(lOperator lOperator) {
        return (long) lOperator.oid;
    }
}
