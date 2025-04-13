package com.aaw.aaw.O_solidObjects.kainghe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AccountTransaction {
     Long id;
     String accountNumber;// 账号
     String currency;// 币种
     String cardNumber;// 卡号
     String debitCreditFlag;// 借贷标志（借/贷）
     BigDecimal transactionAmount;// 发生额
     BigDecimal balance;// 余额
     String note;// 注释
     String counterpartyAccount;// 对方账户
     String transactionBranch;// 交易网点号
     String transactionDate;// 交易日期
     String transactionTime;// 记账时间
}