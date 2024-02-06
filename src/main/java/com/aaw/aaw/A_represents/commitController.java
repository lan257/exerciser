package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.commitLogic;
import com.aaw.aaw.H_tool.noFixFileSubmit;
import com.aaw.aaw.O_solidObjects.activity;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.commit;
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
public class commitController {
    @Autowired
    private commitLogic CL;
    @PostMapping(value = "/aaw/sentComImg")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        new noFixFileSubmit().submit(file,jwtInfo.getUid()+"---","comImg");
        // 处理文件上传成功逻辑
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
    @PostMapping("/aaw/sentCom")
    public Result aConListSubmit(@RequestBody commit commit, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        //加工activity
        commit.setUid(jwtInfo.getUid());
        //上传数据库
        CL.aConListSubmit(commit);

        return new Result(1,"上传成功","");
    }
    @PostMapping("/aaw/getComList")
    public Result getComByLove(@RequestBody activity activity, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<commit> commitList=CL.getBy(activity,jwtInfo.getUid());
        return new Result(1,"获取成功",commitList);
    }
}
