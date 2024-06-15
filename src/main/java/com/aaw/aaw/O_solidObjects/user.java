package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    String ipAddress;
    String buyAddress;
    int thing;
    boolean loveIs;
    int uid;
    String nickname;
    int type=2;
    String password;
    int fan;
    int concern;
    String create;
    String change;
    String img;
    String email;
    String proMotto;
    int love;
    //哈希加密密码
    public void passwordEnc() throws NoSuchAlgorithmException {
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        this.password = Arrays.toString(md.digest());
    }
}
