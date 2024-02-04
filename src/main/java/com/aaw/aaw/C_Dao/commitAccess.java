package com.aaw.aaw.C_Dao;
import com.aaw.aaw.O_solidObjects.simpleObjects.commit;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface commitAccess {

    @Insert("insert into commit (uid, aid, com, img) VALUE (#{uid},#{aid},#{com},#{img})")
    void addCom(int uid, int aid, String img, String com);

    @Select("select * from commit where aid=#{aid} ORDER BY love limit 10")
    List<commit> getByLove(int aid);
}
