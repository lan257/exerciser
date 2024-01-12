package com.aaw.aaw.B_logic;
import com.aaw.aaw.C_dataAccess.userAccess;
import com.aaw.aaw.O_solidObjects.simpleObjects.privateUser;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class pUserLogic implements Logic {
    @Autowired
    private userAccess userAccess;

    public List<Integer> login(privateUser privateUser) {
        log.info(""+privateUser.getEmail()+privateUser.getPassword());

        List<Integer> u=       userAccess.login(privateUser.getEmail(),privateUser.getPassword());
    log.info(""+u);
        return u;
    }

}
