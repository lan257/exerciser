package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.dishesAccess;
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
    @Autowired
    private dishesAccess dA;

    public void Add(order o, int uid) {
        oA.add(o.getDid(),uid, LocalDateTime.now().toString(),o.getAddress(),o.getNum());
    }
    public List<order> selectByUid(int uid) {
        List<order> orderList=oA.selectByUid(uid);
        for (order order:orderList
        ) {
            order.setDishes(dA.selectByDid(order.getDid()));
        }
        return orderList;
    }
    public order selectByOid(int oid) {
        return oA.selectByOid(oid);
    }

    public void cIs(int oid) {
        oA.cIs(oid);
    }

    public List<order> selectBuyCart(int uid) {
        List<order> orderList=oA.selectBuyCart(uid);
        for (order order:orderList
             ) {
            order.setDishes(dA.selectByDid(order.getDid()));
        }
        return orderList;
    }

    public void del(int oid) {
        oA.del(oid);
    }
}
