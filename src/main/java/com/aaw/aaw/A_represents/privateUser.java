package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_logic.userLogic;
import com.aaw.aaw.H_tool.jwt;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
public class privateUser {
    @Autowired
    private userLogic userLogic;
    @PostMapping("aaw/login")
    public Result login(@RequestBody privateUser privateUser ) {

        List<privateUser> list = userLogic.login(privateUser);//调取数据库查询用户
        if (list.isEmpty()) {
            return new Result(0, "登录失败,邮箱或密码错误", "邮箱或密码错误");
        } else {
            //根据list.get(0).getUid读取数据库获取user信息生成jwt令牌返回数据;
//            privateUser u = list.get(0);
//            jwt k = new jwt();
//            String j = k.setJwt(u);//生成令牌
//            log.info("令牌：" + j);
//            if (list.get(0).getUserType() == 0) {
//                return new Result(1, list.get(0).getNikeName() +"root登录成功", j);
//            } else if (list.get(0).getUserType() == 1) {
//                return new Result(-1, list.get(0).getNikeName() +"vip登录成功", j);
//            } else return new Result(2, list.get(0).getNikeName() +"用户登录成功", j);
        }
        return null;
    }

}
