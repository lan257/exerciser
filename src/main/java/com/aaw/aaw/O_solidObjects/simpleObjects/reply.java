package com.aaw.aaw.O_solidObjects.simpleObjects;

import com.aaw.aaw.O_solidObjects.user;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class reply {
    user u;
    int uid;
    String com;
}
