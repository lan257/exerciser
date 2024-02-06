package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccess;
import com.aaw.aaw.C_Dao.commitAccess;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.activity;
import com.aaw.aaw.O_solidObjects.commit;
import com.aaw.aaw.O_solidObjects.lOperator;
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
    private ActAccess AA;
    @Autowired
    private lOperatorLogic LL;

    public void aConListSubmit(commit commit) {
        CA.addCom(commit.getUid(),commit.getAid(),commit.getImg(),commit.getCom());
        activity a= AA.AddCom(commit.getAid());
        a.addCom();
        AA.updateCom(a.getCom(),commit.getAid());
    }

    public List<commit> getBy(activity activity, int uid) {
        List<commit> commitList=getByCA(activity);
        if (commitList != null) {
            for (commit c:commitList
                 ) {
                int s=LL.lpis(new lOperator(c.getCid(),3,1,uid));
                c.setLike(s != 0);
                c.setU(UA.getMore(c.getUid()));
            }
        }
        return commitList;
    }

    private List<commit> getByCA(activity activity) {
        return switch (activity.getThing()) {
            case 1 -> CA.getByLove(activity.getAid());
            case 2 -> CA.getByTime(activity.getAid());
            case 3 -> CA.getOnlyUser(activity.getAid(), activity.getUid());
            default -> null;
        };
    }
}
