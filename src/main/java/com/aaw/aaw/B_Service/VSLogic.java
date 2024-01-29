package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.VSAccess;
import com.aaw.aaw.O_solidObjects.VSmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RestController
public class VSLogic implements Logic{
    @Autowired
    VSAccess vsa;

    public void submit(VSmail vs) {
        LocalDateTime change=LocalDateTime.now();
        vsa.submit(vs.getName(),vs.getUid(),change,vs.getAlso(),vs.getUrl(),vs.getType(),vs.getVid(),vs.getIns());
    }

    public void update(VSmail vs) {
        LocalDateTime change=LocalDateTime.now();
        vsa.update(vs.getName(),vs.getUid(),change,vs.getAlso(),vs.getUrl(),vs.getType(),vs.getVid(),vs.getIns(),vs.getSid());
    }

    public List<VSmail> selectVS(VSmail vs) {
        return vsa.selectVS(vs.getVid());
    }
}
