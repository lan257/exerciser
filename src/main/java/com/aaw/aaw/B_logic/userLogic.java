package com.aaw.aaw.B_logic;
import com.aaw.aaw.A_represents.privateUser;
import com.aaw.aaw.C_dataAccess.userAccess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class userLogic implements userL {
    @Autowired
    private userAccess userAccess;

    public List<privateUser> login(privateUser privateUser) {
        return null;
    }
}
