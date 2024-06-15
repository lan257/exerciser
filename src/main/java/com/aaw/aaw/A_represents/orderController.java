package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.orderLogic;
import com.aaw.aaw.O_solidObjects.dishes;
import com.aaw.aaw.O_solidObjects.order;
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
public class orderController {
    @Autowired
    orderLogic oL;
    @PostMapping("/aaw/order/add")
    public Result add(@RequestBody order o, HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        oL.Add(o,jwtInfo.getUid());
        return new Result(1,"添加成功","");
    }
    @GetMapping("/aaw/order/select/mine")
    public Result selectMine(HttpServletRequest request){
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        List<order> orderList =oL.selectByUid(jwtInfo.getUid());
        return new Result(1,"查找成功",orderList);
    }
    @PostMapping("/aaw/order/select/oid")
    public Result selectByDid(@RequestBody order o){
        order order =oL.selectByOid(o.getOid());
        return new Result(1,"查找成功",order);
    }
    @GetMapping("/aaw/order/select/all")
    public Result selectAll(){
        List<order> orderList =oL.selectAll();
        return new Result(1,"查找成功",orderList);
    }
    //修改停售起售
    @GetMapping("/aaw/order/is")
    public Result eIs(@RequestBody order o){
        oL.cIs(o.getOid());
        return new Result(1,"修改成功","");
    }
}
