package com.aaw.aaw.H_tool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallRecordImporter {

    // 数据库配置信息
    private static final String DB_URL = "jdbc:mysql://localhost:3306/aaw";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    // 日期格式转换
    private static final SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private static final SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        String filePath = "F:\\AndroidAndWeb\\src\\main\\resources\\templates\\kainghe\\话单测试示例_20170329.txt";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            // 跳过文件头部
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("通话类型")) {
                    break; // 找到数据行开始
                }
            }

            // 准备sql语句
            String insertSQL = "INSERT INTO call_record (" +
                    "call_type, call_location, network_type, roaming_location, peer_number, " +
                    "start_time, end_time, duration_seconds, base_fee, info_fee, " +
                    "long_distance_fee, discount_fee, free_resources, dial_method, " +
                    "peer_location, peer_number_location, switch_code, base_station_code, " +
                    "cell_id, imei, discount_process, billing_product, file_name, billing_time" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            // 读取数据...
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("总计：") || line.startsWith("优惠合计：") || line.startsWith("按通话地点分类：")) {
                    continue; // 跳过空行和最后
                }

                String[] parts = line.split("\\s+", -1); // 使用空白字符分割，保留空字段

                if (parts.length < 24) {
                    System.out.println("跳过不完整行: " + line);
                    continue;
                }

                int index = 0;
                pstmt.setString(1, parts[index++]); // call_type
                pstmt.setString(2, parts[index++]); // call_location
                pstmt.setString(3, parts[index++]); // network_type
                pstmt.setString(4, parts[index++]); // roaming_location
                pstmt.setString(5, parts[index++]); // peer_number

                String startDateTimeStr = parts[index++] + " " + parts[index++];
                String endDateTimeStr = parts[index++] + " " + parts[index++];

                try {
                    Date startDate = inputDateFormat.parse(startDateTimeStr);
                    Date endDate = inputDateFormat.parse(endDateTimeStr);
                    pstmt.setString(6, outputDateFormat.format(startDate));
                    pstmt.setString(7, outputDateFormat.format(endDate));
                } catch (Exception e) {
                    System.err.println("解析日期错误: " + e.getMessage());
                    continue;
                }

                pstmt.setInt(8, Integer.parseInt(parts[index++])); // duration_seconds
                pstmt.setBigDecimal(9, new java.math.BigDecimal(parts[index++])); // base_fee
                pstmt.setBigDecimal(10, new java.math.BigDecimal(parts[index++])); // info_fee
                pstmt.setBigDecimal(11, new java.math.BigDecimal(parts[index++])); // long_distance_fee
                pstmt.setBigDecimal(12, new java.math.BigDecimal(parts[index++])); // discount_fee

                StringBuilder freeResources = new StringBuilder(parts[index++]);
                while (index < parts.length && !parts[index].matches("非家庭网.*")) {
                    freeResources.append(" ").append(parts[index++]);
                }
                pstmt.setString(13, freeResources.toString());

                pstmt.setString(14, parts[index++]); // dial_method
                pstmt.setString(15, parts[index++]); // peer_location
                pstmt.setString(16, parts[index++]); // peer_number_location
                pstmt.setString(17, parts[index++]); // switch_code
                pstmt.setString(18, parts[index++]); // base_station_code
                pstmt.setString(19, parts[index++]); // cell_id
                pstmt.setString(20, parts[index++]); // imei

                StringBuilder discountProcess = new StringBuilder(parts[index++]);
                while (!parts[index].matches("4G商旅套餐.*|本地主叫.*")) {
                    discountProcess.append(" ").append(parts[index++]);
                }
                pstmt.setString(21, discountProcess.toString());

                pstmt.setString(22, parts[index++]); // billing_product
                pstmt.setString(23, parts[index++]); // file_name

                String billingTimeStr = parts[index++] + " " + parts[index];
                try {
                    Date billingDate = inputDateFormat.parse(billingTimeStr);
                    pstmt.setString(24, outputDateFormat.format(billingDate));
                } catch (Exception e) {
                    System.err.println("解析错误: " + e.getMessage());
                    pstmt.setNull(24, Types.TIMESTAMP);
                }

                pstmt.executeUpdate();
            }

            System.out.println("数据写入完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}