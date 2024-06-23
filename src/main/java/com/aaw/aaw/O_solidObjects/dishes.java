package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class dishes {
    int uid;
    String name;
    double price;
    int did;
    String ct;
    String et;//编辑时间
    int isCanBuy;
    int num;
    int zero=0;
}
