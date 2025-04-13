package com.aaw.aaw.A_represents;

import com.aaw.aaw.B_Service.AccountTransactionMapper;
import com.aaw.aaw.O_solidObjects.simpleObjects.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(origins = {"*"})
public class AccountTransactionController {
    @Autowired
    private AccountTransactionMapper accountTransactionMapper;
    @GetMapping("/AccountTransaction/getAll")
    public Result getAll(){
        return new Result(1,"展示数据",accountTransactionMapper.selectList(null));
    }
}
