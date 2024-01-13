package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.pUserLogic;
import com.aaw.aaw.B_Service.userLogic;
import com.aaw.aaw.H_tool.jwt;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.simpleObjects.privateUser;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private pUserLogic pUserLogic;
    @Autowired
    private userLogic userLogic;
    @PostMapping("aaw/login")
    public Result login(@RequestBody privateUser privateUser ) {

        List<Integer> list = pUserLogic.login(privateUser);//调取数据库查询用户
        if (list.isEmpty()) {
            return new Result(0, "登录失败,邮箱或密码错误", "邮箱或密码错误");
        } else {
            //根据list.get(0).getUid读取数据库获取user信息生成jwt令牌返回数据;
            int pUid = list.get(0);
            user u= userLogic.getUser_Uid(pUid);
            jwt k = new jwt();
            String j = k.setJwt(u);//生成令牌
            log.info("生成令牌：" + j);
            if (u.getType() == 0) {
                return new Result(1, u.getNikename() +"root登录成功", j);
            } else if (u.getType() == 1) {
                return new Result(-1, u.getNikename() +"vip登录成功", j);
            } else return new Result(2, u.getNikename() +"用户登录成功", j);
        }
    }


}
