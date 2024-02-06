package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccess;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.activity;
import com.aaw.aaw.O_solidObjects.lOperator;
import com.aaw.aaw.O_solidObjects.user;
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
    private ActAccess AA;
    @Autowired
    private userAccess UA;
    @Autowired
    private lOperatorLogic LL;

    public void aConListSubmit(activity activity) {
        AA.aConListSubmit(activity.getUid(),activity.getChangeTime(),activity.getContent(),activity.getTitleText(),
                activity.getTitleImg(),activity.getType());
    }

    public List<activity> getActList(user u) {
        List<activity> activityList = getList(u) ;
        if (activityList != null) {
            for (activity a:activityList
                 ) {
                a.setU(UA.getNickname(a.getUid()));
            }
        }
        return activityList;
    }

    private List<activity> getList(user u) {
        return switch (u.getThing()) {
            case 1 -> AA.getActList();//随机
            case 2 -> AA.getActListByUserSent(u.getUid());//用户发布
            case 3 -> AA.getActListByUserLove(u.getUid());//用户收藏
            default -> null;
        };
    }

    public activity getAct(activity act,int uid) {
        activity activity=AA.getAct(act.getAid());
        int s=LL.lpis(new lOperator(act.getAid(),2,1,uid));
        activity.setLoveIs(s!=0);
        activity.setU(UA.getMore(activity.getUid()));
        activity.getU().setLoveIs(LL.lpis(new lOperator(activity.getUid(),1,1,uid))!=0);
//        log.info(activity.getU().isLoveIs()+"判断关注与否");
        return activity;
    }
}
