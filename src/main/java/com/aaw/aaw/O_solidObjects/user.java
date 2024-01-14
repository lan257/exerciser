package com.aaw.aaw.O_solidObjects;

import com.aaw.aaw.O_solidObjects.simpleObjects.privateUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class user {
    int uid;
    String nikename;
    int type;
    privateUser privateUser;
    String loveMusic;
    String loveMovie;
    String fun;
    String concern;
    LocalDate create;
    LocalDate change=LocalDate.now();
    String img;
}
