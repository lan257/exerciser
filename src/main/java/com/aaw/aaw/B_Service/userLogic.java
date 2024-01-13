package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Service
@RestController
public class userLogic implements Logic {
    @Autowired
    private userAccess userAccess;

    public user getUser_Uid(int pUid) {
        return userAccess.getUser_Uid(pUid);
    }
}

