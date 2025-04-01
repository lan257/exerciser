package com.aaw.aaw.B_Service;


import com.aaw.aaw.C_Dao.nodeAccess;
import com.aaw.aaw.O_solidObjects.mindMap.node;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@Service
@RestController
public class nodeLogic {
    @Autowired
    private nodeAccess nodeAccess;


    public void nodeAdd(node node) {
        nodeAccess.nodeAdd(node.getMap_id(),node.getCard(), LocalDateTime.now(),node.getWordSize(),node.getUnderLine(),node.getDelLine(),node.getWordStyle(),node.getWordColor());
    }
}
