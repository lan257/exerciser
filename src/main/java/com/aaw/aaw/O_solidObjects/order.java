package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class order {
    dishes dishes;
    int did;
    int uid;
    String createTime;
    String changeTime;
    byte isBuy;
    int finStatus;
    String address;
    int oid;
    int num;
    int del;

}
