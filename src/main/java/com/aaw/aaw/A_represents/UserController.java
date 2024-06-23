package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.pUserLogic;
import com.aaw.aaw.B_Service.userLogic;
import com.aaw.aaw.H_tool.fileSubmit;
import com.aaw.aaw.H_tool.jwt;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private pUserLogic pUserLogic;
    @Autowired
    private userLogic userLogic;

    String nn="/userImg/7113ada1-aacf-455d-856c-ec9a1480b829.jpg";
    @PostMapping("aaw/login")
    public Result login(@RequestBody user privateUser,HttpServletRequest request) throws NoSuchAlgorithmException {
        String ipAddress=request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress=request.getRemoteAddr();
        }
        log.info("登录请求源"+ipAddress);
        List<Integer> list = pUserLogic.login(privateUser);//调取数据库查询用户
        if (list.isEmpty()) {
            return new Result(0, "登录失败,邮箱或密码错误", "");
        } else {
            //根据list.get(0).getUid读取数据库获取user信息生成jwt令牌返回数据;
            int pUid = list.get(0);
            user u= userLogic.getUser_Uid(pUid);
            if(!Objects.equals(u.getIpAddress(), ipAddress)){log.info( new Result(0, "是否新设备登录？默认登陆", "").toString());}
            userLogic.ipUpdate(u.getUid(),ipAddress);
            jwt k = new jwt();
            String j = k.setJwt(u);//生成令牌
            log.info("生成令牌：" + j);
            if (u.getType() == 0) {
                System.out.println();
                return new Result(1, u.getNickname() +"root登录成功", j);
            } else if (u.getType() == 1) {
                return new Result(-1, u.getNickname() +"vip登录成功", j);
            } else return new Result(2, u.getNickname() +"用户登录成功", j);
        }
    }
    fileSubmit fileSubmit = new fileSubmit();
    //上传图片
    @PostMapping(value = "/aaw/sign")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        nn="n";
        nn= fileSubmit.submit(file,"userImg");
        // 处理文件上传成功逻辑
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }

    @PostMapping("aaw/sign1")
    public Result signIn(@RequestBody user u) throws NoSuchAlgorithmException {
        if (!Objects.equals(nn, "n")){
            log.info(u+"");
        u.setImg(nn);
        pUserLogic.sign(u);
        userLogic.sign(u);
        return new Result(1,"注册成功","") ;}
//        else if (!Objects.equals(nn, "n")){
        return new Result(0,"注册失败,有可能是文件太大了","");
//        return new Result(0,"注册失败,未知原因","");
    }
    @PostMapping("aaw/selectUser")
    public Result selectUser(@RequestBody user u){
        List<user> s =userLogic.selectUser(u);
        return new Result(1,"查询成功",s);
    }
    @PostMapping("/aaw/uid/selectUser")
    public Result selectUserByUid(@RequestBody user u,HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        user s =userLogic.selectUserByUid(u,jwtInfo.getUid());
        return new Result(1,"查询成功",s);
    }
    @GetMapping("aaw/getUserJwt")
    public Result getUserJwt(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        user me= userLogic.selectUser(jwtInfo).get(0);
        String ipAddress=request.getHeader("X-Forwarded-For");
        if (ipAddress == null) {
            ipAddress=request.getRemoteAddr();
        }
        if(!Objects.equals(me.getIpAddress(), ipAddress)){return new Result(0, "有新设备登录，已被顶出", "");}
        return new Result(1,"提取成功",me);
    }
    @PostMapping("/aaw/baUpdate")
    public Result baUpdate(@RequestBody user u, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        userLogic.baUpdate(jwtInfo.getUid(),u.getBuyAddress());
        return new Result(1,"修改成功","");
    }
}
