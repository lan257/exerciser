package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.ActAccesee;
import com.aaw.aaw.O_solidObjects.activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class ActLogic {
    @Autowired
    private ActAccesee AA;

    public void aConListSubmit(activity activity) {
        AA.aConListSubmit(activity.getUid(),activity.getChangeTime(),activity.getContext(),activity.getTitleText(),
                activity.getTitleImg(),activity.getType());
    }

    public List<activity> getActList() {
        return AA.getActList();
    }
}
