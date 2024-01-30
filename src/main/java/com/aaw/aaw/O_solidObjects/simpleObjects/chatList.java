package com.aaw.aaw.O_solidObjects.simpleObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.aaw.aaw.O_solidObjects.user;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class chatList {
    msg msg;
    user user;
}
