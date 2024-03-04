package com.aaw.aaw.B_Service;

import com.aaw.aaw.C_Dao.apiAccess;
import com.aaw.aaw.O_solidObjects.api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Service
@RestController
public class apiLogic {
    @Autowired
    private apiAccess apiAccess;

    public api getApiById(api api) {
        return apiAccess.getApiById(api.getApiId());
    }

    public List<api> getApi() {
        return apiAccess.getApi();
    }

    public void addApi(api api) {
        apiAccess.addApi(api.getAddress(),api.getType(),api.getSort(),api.getGetData(),api.getReData(),api.getErrorData(),api.getUse(),api.getOther());
    }

    public void updateApi(api api) {
        apiAccess.updateApi(api.getApiId(),api.getAddress(),api.getType(),api.getSort(),api.getGetData(),api.getReData(),api.getErrorData(),api.getUse(),api.getOther());
    }

    public void delApi(api api) {
        apiAccess.del(api.getApiId());
    }
}
