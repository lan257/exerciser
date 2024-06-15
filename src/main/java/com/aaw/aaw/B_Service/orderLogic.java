package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.orderAccess;
import com.aaw.aaw.O_solidObjects.order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RestController
public class orderLogic implements Logic {
    @Autowired
    private orderAccess oA;

    public void Add(order o, int uid) {
        oA.add(o.getDid(),uid, LocalDateTime.now().toString(),o.getAddress());
    }
    public List<order> selectByUid(int uid) {
        return oA.selectByUid(uid);
    }

    public List<order> selectAll() {
        return oA.selectAll();
    }

    public order selectByOid(int oid) {
        return oA.selectByOid(oid);
    }

    public void cIs(int oid) {
        oA.cIs(oid);
    }
}
