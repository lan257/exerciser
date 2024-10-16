package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.dishesLogic;
import com.aaw.aaw.O_solidObjects.dishes;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class dishesController {
    @Autowired
    private dishesLogic dL;
    @PostMapping("/aaw/dishes/Add")
    public Result Add(@RequestBody dishes dishes, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        dL.Add(dishes,jwtInfo.getUid());
        return new Result(1,"添加成功","");
    }
    @GetMapping("/aaw/dishes/select/mine")
    public Result selectMine(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<dishes> dishesList =dL.selectByUid(jwtInfo.getUid());
        return new Result(1,"查找成功",dishesList);
    }
    @PostMapping("/aaw/dishes/select/did")
    public Result selectByDid(@RequestBody dishes dishes){
        dishes dish =dL.selectByDid(dishes.getDid());
        return new Result(1,"查找成功",dish);
    }
    @GetMapping("/aaw/dishes/select/all")
    public Result selectAll(){
        List<dishes> dishesList =dL.selectAll();
        return new Result(1,"查找成功",dishesList);
    }
    //修改停售起售
    @PostMapping("/aaw/dishes/is")
    public Result eIs(@RequestBody dishes dishes){
        dL.cIs(dishes.getDid());
        return new Result(1,"修改成功","");
    }
    @PostMapping("/aaw/dishes/del")
    public  Result del(@RequestBody dishes dishes){
        dL.del(dishes.getDid());
        return new Result(1,"删除成功","");
    }
    @PostMapping("/aaw/dishes/numAdd")
    public  Result numAdd(@RequestBody dishes dishes,int num){
        dL.numAdd(dishes.getDid(),num);
        return new Result(1,"添加成功","");
    }
}
