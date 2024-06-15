package com.aaw.aaw.O_solidObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class order {
    int did;
    int uid;
    String creatTime;
    String changeTime;
    byte isBuy;
    String address;
    int oid;
}
