package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccess;
import com.aaw.aaw.C_Dao.commitAccess;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.C_Dao.lOperatorAccess;
import com.aaw.aaw.O_solidObjects.lOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.BindingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@Service
@RestController
public class lOperatorLogic implements Logic {
    @Autowired
    private lOperatorAccess LA;
    @Autowired
    private commitAccess CA;
    @Autowired
    private ActAccess AA;
    @Autowired
    private userAccess UA;

    public void comLoveAdd(lOperator lp) {
        thing(lp);//点赞
    }
    public int lpis(lOperator lp) {
        try {
            return LA.lpis(lp.getOid(),lp.getTid(),lp.getUid(),lp.getOType());
        }catch (BindingException e){return 0;}

    }
    public void thing(lOperator lp) {
        switch (lp.getTid()) {
            case 1://活动收藏
                if (lpis(lp) == 0) {
                    LA.add(lp.getOid(), lp.getTid(), lp.getUid(), lp.getOType());
                    updateLove(lp);
                }
                break;
            case 2:
                lp.setTid(1);
                if (lpis(lp) != 0) {
                    LA.del(lpis(lp));
                    updateLove(lp);
                }
                break;
    }}

    private void updateLove(lOperator lp) {
        switch (lp.getOType()) {
            case 1:
                UA.updateLove(lp.getOid());
                break;
            case 2:
                AA.updateLove(lp.getOid());
                UA.update(AA.getActUid(lp.getOid()));
                break;
            case 3:
                CA.updateLove(lp.getOid());
                break;
        }
    }
}
