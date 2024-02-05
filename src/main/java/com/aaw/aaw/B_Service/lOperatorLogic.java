package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccesee;
import com.aaw.aaw.C_Dao.commitAccess;
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
    private ActAccesee AA;

    public void comLoveAdd(lOperator lp) {
        add(lp);//点赞
    }
    public void comLoveDel(lOperator lp) {
        del(lp);//取消点赞
    }
    public int lpis(lOperator lp) {
        try {
            return LA.lpis(lp.getOid(),lp.getTid(),lp.getUid(),lp.getOType());
        }catch (BindingException e){return 0;}

    }
    public void add(lOperator lp) {
        if (lpis(lp)==0){
        LA.add(lp.getOid(),lp.getTid(),lp.getUid(),lp.getOType());
        switch (lp.getOType()){
            case 1:
                switch (lp.getTid()){
                    case 1://点赞
                        break;
                }
                break;
            case 2:
                switch (lp.getTid()){
                    case 1://活动收藏
                        int i=AA.getLove(lp.getOid());
                        i++;
                        AA.addLove(i,lp.getOid());
                }
                break;
            case 3:
                switch (lp.getTid()){
                    case 1://评论点赞
                        int i=CA.getLove(lp.getOid());
                        i++;
                        CA.addLove(i,lp.getOid());
                        break;
                }
                break;
        }
    }}
    public void del(lOperator lp) {
        if (lpis(lp)!=0){
        LA.del(lpis(lp));
            switch (lp.getOType()){
                case 1:
                    switch (lp.getTid()){
                        case 1:
                            break;
                    }
                    break;
                case 2:switch (lp.getTid()){
                    case 1://活动收藏
                        int i=AA.getLove(lp.getOid());
                        i--;
                        AA.addLove(i,lp.getOid());
                }
                    break;
                case 3:
                    switch (lp.getTid()){
                        case 1:
                            int i=CA.getLove(lp.getOid());
                            i--;
                            CA.addLove(i,lp.getOid());
                            break;
                    }
                    break;
            }}
    }
}
