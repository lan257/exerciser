package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.dishesAccess;
import com.aaw.aaw.O_solidObjects.dishes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RestController
public class dishesLogic{
    @Autowired
    private dishesAccess dA;

    public void Add(dishes dishes, int uid) {
        dA.add(dishes.getName(),dishes.getPrice(), LocalDateTime.now().toString(),uid);
    }

    public List<dishes> selectByUid(int uid) {
        return dA.selectByUid(uid);
    }

    public List<dishes> selectAll() {
        return dA.selectAll();
    }

    public dishes selectByDid(int did) {
        return dA.selectByDid(did);
    }

    public void cIs(int did) {
        dA.cIs(did);
    }

    public void del(int did) {dA.del(did);
    }

    public void numAdd(int did, int num) {dA.numAdd(did,num);
    }
}
