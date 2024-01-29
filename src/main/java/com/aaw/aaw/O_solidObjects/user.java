package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    int uid;
    String nickname;
    int type=2;
    String password;
    String loveMusic;
    String loveMovie;
    String fun;
    String concern;
    String create;
    String change;
    String img;
    String email;
}
