package com.aaw.aaw.C_Dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface lOperatorAccess {

    @Select("select lid from loperator where (uid=#{uid} and oid=#{oid} and tid=#{tid} and oType=#{oType})")
    int lpis(int oid, int tid, int uid, int oType);
    @Insert("insert into loperator (oid, oType, tid, uid) VALUE (#{oid},#{oType},#{tid},#{uid})")
    void add(int oid, int tid, int uid, int oType);

    @Delete("delete from loperator where lid=#{lid}")
    void del(int lid);
}
