package com.aaw.aaw.B_Service;
import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
@Service
@RestController
public class pUserLogic implements Logic {
    @Autowired
    private userAccess userAccess;

    public List<Integer> login(user privateUser) throws NoSuchAlgorithmException {
        privateUser.passwordEnc();
        return userAccess.login(privateUser.getEmail(),privateUser.getPassword());
    }

    public void sign(user u) throws NoSuchAlgorithmException {
        u.passwordEnc();
        userAccess.pusign(u.getEmail(),u.getPassword());
    }
}
