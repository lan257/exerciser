package com.aaw.aaw.O_solidObjects.mindMap;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class comment {
    int comment_id,user_id,mindMap_id;
    String content;
    LocalDateTime creat_time;
}
