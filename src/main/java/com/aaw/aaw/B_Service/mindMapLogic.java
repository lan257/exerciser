package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.mindMapAccess;
import com.aaw.aaw.O_solidObjects.mindMap.mind_map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@Service
@RestController
public class mindMapLogic {
    @Autowired
    private mindMapAccess mindMapAccess;
    @Autowired
    private nodeLogic nodeLogic;
    public int mapAdd(@RequestBody mind_map mindMap) {
        mindMap.getNode().setMap_id(mindMapAccess.mapAdd(mindMap.getUid(),mindMap.getTitle(),mindMap.getDescription()
                ,mindMap.is_public(), LocalDateTime.now(),mindMap.getMainNode()));
        nodeLogic.nodeAdd(mindMap.getNode());
        return mindMap.getNode().getMap_id();
    }

    public int mapDel(mind_map mindMap) {
        mindMapAccess.mapDel(mindMap.getMap_id());
        return 1;
    }
}
