package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccesee;
import com.aaw.aaw.C_Dao.commitAccess;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.activity;
import com.aaw.aaw.O_solidObjects.simpleObjects.commit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class commitLogic implements Logic {
    @Autowired
    private commitAccess CA;
    @Autowired
    private userAccess UA;
    @Autowired
    private ActAccesee AA;

    public void aConListSubmit(commit commit) {
        CA.addCom(commit.getUid(),commit.getAid(),commit.getImg(),commit.getCom());
        activity a= AA.AddCom(commit.getAid());
        a.addCom();
        AA.updateCom(a.getCom(),commit.getAid());
    }

    public List<commit> getByLove(int aid) {
        List<commit> commitList= CA.getByLove(aid);
        for (commit c:commitList
             ) {
            c.setU(UA.getMore(c.getUid()));
        }
        return commitList;
    }
}