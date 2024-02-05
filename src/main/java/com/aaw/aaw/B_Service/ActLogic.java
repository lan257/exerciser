package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccesee;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.activity;
import com.aaw.aaw.O_solidObjects.lOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class ActLogic {
    @Autowired
    private ActAccesee AA;
    @Autowired
    private userAccess UA;
    @Autowired
    private lOperatorLogic LL;

    public void aConListSubmit(activity activity) {
        AA.aConListSubmit(activity.getUid(),activity.getChangeTime(),activity.getContent(),activity.getTitleText(),
                activity.getTitleImg(),activity.getType());
    }

    public List<activity> getActList() {
        List<activity> activityList = AA.getActList();
        for (activity a:activityList
             ) {
            a.setU(UA.getNickname(a.getUid()));
        }
        return activityList;
    }

    public activity getAct(activity act,int uid) {
        activity activity=AA.getAct(act.getAid());
        int s=LL.lpis(new lOperator(act.getAid(),2,1,uid));
        log.info(s+"查询loveIs");
        activity.setLoveIs(s!=0);
        activity.setU(UA.getMore(activity.getUid()));
        return activity;
    }
}
