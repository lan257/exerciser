package com.aaw.aaw.A_represents;


import com.aaw.aaw.B_Service.contractMapper;
import com.aaw.aaw.O_solidObjects.mindMap.contract;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import com.aaw.aaw.O_solidObjects.user;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"})
@Service
@RestController
@Slf4j
public class contractController {
    @Autowired
    private contractMapper contractMapper;
    @Autowired
    private nodeController nodeController;
    //注册合同
    @PostMapping("/aaw/contract")
    public Result contract(@RequestBody contract contract , HttpServletRequest request){
        log.info("注册合同:"+contract);
        //用户本身先自动签署合同
        user jwtInfo = (user) request.getAttribute("jwtInfo");

        if(contract.getAu()==jwtInfo.getUid()){contract.setAu(0);}
        if(contract.getBu()==jwtInfo.getUid()){contract.setBu(0);}
        //检查合同双方是否均确定签署
        if(contract.getAu()==0 && contract.getBu()==0){
            //合同执行;
            Result result = executeContract(contract);
            //返回前端结果
            return new Result(1,"合同已执行",result);
        }
        else{
            //存入数据库，等待双方确认
            contractMapper.insert(contract);
            return new Result(1,"合同已存入数据库，等待双方确认","合同现状（需处理）");
        }
    }
    //查询未处理合同（按uid）
    @GetMapping("/aaw/contract/{uid}")
    public Result getUnhandledContracts(@PathVariable("uid") Integer uid){
        List<contract> unhandledContracts = contractMapper.selectList(new QueryWrapper<contract>().eq("a_sure",uid).or().eq("b_sure",uid));
        return new Result(1,"查询成功",unhandledContracts);
    }
    //签署合同
    @PutMapping("/aaw/contract/{cid}")
    public Result signContract( @PathVariable("cid") Integer cid, HttpServletRequest request){
        contract contract = contractMapper.selectById(cid);
        user jwtInfo = (user) request.getAttribute("jwtInfo");
        if(contract.getAu()==jwtInfo.getUid()){contract.setAu(0);}
        if(contract.getBu()==jwtInfo.getUid()){contract.setBu(0);}
        //检查合同双方是否均确定签署
        if(contract.getAu()==0 && contract.getBu()==0){
            //合同执行;
            Result result =executeContract(contract);
            //返回前端结果
            return new Result(1,"合同已执行",result);
        }
        else{
            //存入数据库，等待双方确认
            contractMapper.updateById(contract);
            return new Result(1,"合同已更新至数据库，等待对方确认","合同现状（需处理）");
        }
    }
    //执行合同
    public Result executeContract(contract contract){
        Result data ;
        switch (contract.getType()){
            case 1:
                data= execute1(contract);//节点关联合同
                break;
            case 2:
                data= execute2(contract);//修改密码合同
                break;
            case 3:
                data= execute3(contract);//内容审核合同
                break;
                case 4:
                    data= execute4(contract);//取消关联合同
                    break;
            default:
                data= new Result(0,"合同类型不存在","合同执行失败");
                break;
        }
        return data;
    }
    //节点关联合同执行
    private Result execute1(contract contract) {
//        return new Result(1,"节点关联合同执行成功","节点关联功能未开启");

        return nodeController.nodeConnection(contract.getAid(),contract.getBid());
    }
    //修改密码合同执行
    private Result execute2(contract contract) {
        return new Result(1,"修改密码合同执行成功","修改密码功能未开启");
    }
    //内容审核合同执行
    private Result execute3(contract contract) {
        return new Result(1,"内容审核合同执行成功","内容审核功能未开启");
    }

    //取消关联合同执行
    private Result execute4(contract contract) {
        return new Result(1,"取消关联合同执行成功",nodeController.nodeConnectionDel(contract.getAid()));
    }



}
