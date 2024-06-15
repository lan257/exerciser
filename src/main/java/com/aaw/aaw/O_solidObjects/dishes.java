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
    int name;
    int price;
    int did;
    String ct;
    String et;//编辑时间
}
