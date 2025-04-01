package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.mindMapLogic;
import com.aaw.aaw.O_solidObjects.mindMap.mind_map;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class mindMapController {
    @Autowired
    private mindMapLogic mindMapLogic;
    //创建思维导图并添置根节点
    @PostMapping("/aaw/mindMapAdd")
    public Result mapAdd(@RequestBody mind_map mindMap, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        mindMap.setMap_id(jwtInfo.getUid());
        mindMap.setMap_id(mindMapLogic.mapAdd(mindMap));
        return new Result(1,"创建成功",mindMap.getMap_id());
    }
    //删除思维导图。
    @PostMapping("/aaw/mindMapDel")
    public Result mapDel(@RequestBody mind_map mindMap){
        return new Result(1,"删除成功",mindMapLogic.mapDel(mindMap));
    }
    //修改思维导图。

    //增加收藏数。交付给iOperator处理

    //查询思维导图（logic单独设置按id查询）

    //特定查询思维导图
}
