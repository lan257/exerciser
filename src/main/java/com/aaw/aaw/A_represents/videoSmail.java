package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.VSLogic;
import com.aaw.aaw.O_solidObjects.VSmail;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.aaw.aaw.O_solidObjects.video;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class videoSmail {
    @Autowired
    VSLogic vsl;
    @PostMapping("aaw/AAWVSSubmit")
    public Result AAWVSSubmit(@RequestBody VSmail vs, HttpServletRequest request) throws IOException {
        vs=getVS(vs, request);
        vsl.submit(vs);
        return new Result(1,"上传成功","") ;
    }
    @PostMapping("aaw/AAWVSUpdate")
    public Result AAWVSUpdate(@RequestBody VSmail vs, HttpServletRequest request) throws IOException {
        vs = getVS(vs, request);
        vsl.update(vs);
        return new Result(1,"上传成功","") ;
    }

    @PostMapping("/aaw/getVSmail")
    public  Result getVSmall(@RequestBody VSmail vs){
        List<VSmail> VS =vsl.selectVS(vs);
        log.info(VS.toString());
        return new Result(1,"查询成功",VS);
    }
    @PostMapping("/aaw/")
    private VSmail getVS(@RequestBody VSmail vs, HttpServletRequest request) {
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        if (vs.getUid()!=1){
            vs.setUid(jwtInfo.getUid());}
        if(vs.getUrl().contains("m3u8")){
            vs.setType(3);
        }
        else if(vs.getUrl().contains(".mp4")){vs.setType(1);}
        else {vs.setType(2);}
        return vs;
    }

}
