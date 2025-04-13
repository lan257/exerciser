package com.aaw.aaw.O_solidObjects.kainghe;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallRecord {
     Integer id;
     String callType;// 通话类型
     String callLocation;// 通话地
     String networkType;// 网络标识
     String roamingLocation;// 漫游地
     String peerNumber;// 对端号码
     String startTime;// 起始时间
     String endTime;// 结束时间
     Integer durationSeconds;// 通话时长(秒)
     BigDecimal baseFee;// 基本费(元)
     BigDecimal infoFee;// 信息费(元)
     BigDecimal longDistanceFee;// 长途费(元)
     BigDecimal discountFee;// 优惠费(元)
     String freeResources;// 免费资源
     String dialMethod;// 拨打方式
     String peerLocation;// 对端用户通话所在地
     String peerNumberLocation;// 对端号码归属地
     String switchCode; // 交换机代码
     String baseStationCode; // 基站代码
     String cellId;// 小区ID
     String imei;// IMEI号
     String discountProcess; // 优惠过程
     String billingProduct;// 计费产品
     String fileName;// 文件名
     String billingTime;// 批价处理时间
}