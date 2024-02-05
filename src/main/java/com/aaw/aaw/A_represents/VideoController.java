package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.videoLogic;
import com.aaw.aaw.H_tool.fileSubmit;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.aaw.aaw.O_solidObjects.video;
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
public class VideoController {
    @Autowired
    videoLogic vLogic;
    String nn;
    fileSubmit fileSubmit = new fileSubmit();
    @PostMapping(value = "/aaw/vImgSubmit")
    public ResponseEntity<String> vImgSubmit(@RequestParam("file") MultipartFile file) throws IOException {

        nn= fileSubmit.submit(file,"videoImg");
        // 处理文件上传成功逻辑
        return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
    }
    @PostMapping("aaw/vSubmit")
    public Result signIn(@RequestBody video v, HttpServletRequest request) {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        if (nn!=null){v.setImg(nn);}else {v.setImg("/img/illust_106257046_20230403_090615.jpg");}
            if (v.getUid()!=0){v.setUid(jwtInfo.getUid());}
            else {
                v.setUid(1);
            }
            vLogic.submit(v);
            return new Result(1,"上传成功","") ;
    }
    @PostMapping("aaw/vUpdate")
    public Result vUpdate(@RequestBody video v, HttpServletRequest request) {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        if (nn!=null){v.setImg(nn);}
        if (v.getUid()!=0){v.setUid(jwtInfo.getUid());}
        else {
            v.setUid(1);
        }
        vLogic.update(v);
        return new Result(1,"上传成功","") ;
    }
    @PostMapping("aaw/selectVideo")
    public Result selectUser(@RequestBody video video){
        List<video> v =vLogic.selectUser(video);
        log.info(v.toString());
        return new Result(1,"查询成功",v);
    }
    @PostMapping("aaw/videoShow")
    public Result videoByVid(@RequestBody video video){
        video v=vLogic.videoByVid(video.getVid());
        log.info(v.toString());
        return new Result(1,"查询成功",v);
    }
}
