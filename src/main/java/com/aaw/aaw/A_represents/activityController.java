package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.ActLogic;
import com.aaw.aaw.H_tool.noFixFileSubmit;
import com.aaw.aaw.O_solidObjects.activity;
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
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class activityController {
    @Autowired
    private ActLogic AL;
    @PostMapping(value = "/aaw/ActImgSubmit")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        new noFixFileSubmit().submit(file,jwtInfo.getUid()+"---","ActImg");
        // 处理文件上传成功逻辑
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
    @PostMapping("/aaw/aConListSubmit")
    public Result aConListSubmit(@RequestBody activity activity, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工activity
        activity.setUid(jwtInfo.getUid());
        log.info(activity+"");
        //上传数据库
        AL.aConListSubmit(activity);
        return new Result(1,"上传成功","");
    }

    @GetMapping("/aaw/getActList")
    public Result getActList(){
        List<activity> activitys=AL.getActList();
        return new Result(1,"获取活动成功",activitys);
    }
    @PostMapping("/aaw/actSelect")
    public Result actSelect(@RequestBody activity act,HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        activity activity=AL.getAct(act,jwtInfo.getUid());
        log.info("返回Android端数据");
        return new Result(1,"获取活动成功",activity);
    }
}
