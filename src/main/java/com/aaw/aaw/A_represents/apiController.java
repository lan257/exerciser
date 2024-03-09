package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.apiLogic;
import com.aaw.aaw.O_solidObjects.api;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class apiController {
    @Autowired
    private apiLogic apiLogic;
    @PostMapping("/api/id")
    public Result getApiById(@RequestBody api api){
        log.info("api:"+api);
        api reapi= apiLogic.getApiById(api);
        return new Result(1,"获得特定api",reapi);
    }
    @GetMapping("/api/all")
    public Result getApiById(){
        List<api> apiList= apiLogic.getApi();
        return new Result(1,"所有api",apiList);
    }
    @PostMapping("/aaw/api")
    public Result addApi(@RequestBody api api){
        apiLogic.addApi(api);
        return new Result(1,"添加api成功",api);
    }
    @PutMapping("/aaw/api")
    public Result updateApi(@RequestBody api api){
        log.info("api:"+api);
        apiLogic.updateApi(api);
        return new Result(1,"修改api成功",api);
    }
    @DeleteMapping("/aaw/root/api")
    public Result delApi(@RequestBody api api){
        apiLogic.delApi(api);
        return new Result(1,"删除api成功","");
    }
}
