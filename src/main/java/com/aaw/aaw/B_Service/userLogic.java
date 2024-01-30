package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.userAccess;
import com.aaw.aaw.O_solidObjects.user;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RestController
public class userLogic implements Logic {
    @Autowired
    private userAccess userAccess;

    public user getUser_Uid(int pUid) {
        return userAccess.getUser_Uid(pUid);
    }

    public void sign(user u) {
        u.setUid(userAccess.login(u.getEmail(),u.getPassword()).get(0));
        LocalDateTime change= LocalDateTime.now();
        userAccess.usign(u.getUid(), u.getNickname(),u.getEmail(),u.getImg(),change.toString());
    }

    public List<user> selectUser(user u) {
        if(Objects.equals(u.getNickname(), "all")){
            return userAccess.getUserall();
        }else {
        return userAccess.getUser(u.getUid(),u.getNickname(),u.getType());}
    }

    public user selectUserByUid(user u) {
        return userAccess.getUserByUid(u.getUid());
    }
}

