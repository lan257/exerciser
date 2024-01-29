package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.videoAccess;
import com.aaw.aaw.O_solidObjects.video;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RestController
public class videoLogic {
    @Autowired
    videoAccess vAccess;


    public void submit(video v) {
        LocalDateTime vChange=LocalDateTime.now();
        vAccess.submit(v.getVName(),v.getVIntro(),v.getVUrl(),vChange,v.getUid(),v.getImg(),v.getIns());
    }

    public List<video> selectUser(video video) {
        if(Objects.equals(video.getVName(), "all")){
            return vAccess.getVideoAll();
        }else {
            return vAccess.getVideo(video.getVid(),video.getVName(),video.getUid());}
    }

    public video videoByVid(int vid) {
        return vAccess.getVideoByVid(vid);
    }

    public void update(video v) {
        LocalDateTime vChange=LocalDateTime.now();
        vAccess.update(v.getVName(),v.getVIntro(),vChange,v.getUid(),v.getImg(),v.getIns(),v.getVid());

    }
}

