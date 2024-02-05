package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.lOperatorLogic;
import com.aaw.aaw.O_solidObjects.commit;
import com.aaw.aaw.O_solidObjects.lOperator;
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
public class lOperatorController {
    @Autowired
    private lOperatorLogic LL;
    @PostMapping("/aaw/Love++")
    public Result comLoveAdd(@RequestBody lOperator lp, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工数据
        lp.setUid(jwtInfo.getUid());
        //数据库处理
        LL.comLoveAdd(lp);
        return new Result(1,"点赞成功","");
    }
    @PostMapping("/aaw/Love--")
    public Result comLoveDel(@RequestBody lOperator lp, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工数据
        lp.setUid(jwtInfo.getUid());
        //数据库处理
        LL.comLoveDel(lp);
        return new Result(1,"取消成功","");
    }

}
