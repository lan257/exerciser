package com.aaw.aaw.C_Dao;
import com.aaw.aaw.O_solidObjects.commit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface commitAccess {

    @Insert("insert into commit (uid, aid, com, img) VALUE (#{uid},#{aid},#{com},#{img})")
    void addCom(int uid, int aid, String img, String com);

    @Select("select * from commit where (aid=#{aid} and examine) ORDER BY love desc limit 10")
    List<commit> getByLove(int aid);

    //    @Select("select love from commit where cid=#{cid}")
//    int getLove(int cid);
//
//    @Update("update commit set love=#{i} where cid=#{cid}")
//    void addLove(int i,int cid);
    @Update("UPDATE commit SET love = (SELECT COUNT(*) FROM loperator WHERE (loperator.oid = #{oid} and tid=1 and oType=3) )WHERE  (commit.cid= #{oid} and examine)")
    void updateLove(int oid);
    @Select("select * from commit where (aid=#{aid} and examine) ORDER BY time desc limit 10")
    List<commit> getByTime(int aid);
    @Select("select * from commit where (uid=#{uid} and aid=#{aid} and examine) ORDER BY time desc ")
    List<commit> getOnlyUser(int aid, int uid);
}
