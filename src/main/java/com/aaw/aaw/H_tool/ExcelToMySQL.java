package com.aaw.aaw.H_tool;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class ExcelToMySQL {

    // 数据库配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/aaw";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] args) {
        String excelFilePath = "F:\\AndroidAndWeb\\src\\main\\resources\\templates\\kainghe\\账单测试示例_20170329.xlsx";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             FileInputStream inputStream = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            createTable(connection);

            Sheet sheet = workbook.getSheetAt(0); // 第一个sheet

            for (int i = 2; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                String account = getCellValue(row.getCell(0));
                String currency = getCellValue(row.getCell(1));
                String cardNumber = getCellValue(row.getCell(2));
                String debitCreditFlag = getCellValue(row.getCell(3));
                String amount = getCellValue(row.getCell(4));
                String balance = getCellValue(row.getCell(5));
                String note = getCellValue(row.getCell(6));
                String counterpartyAccount = getCellValue(row.getCell(7));
                String transactionBranch = getCellValue(row.getCell(8));
                System.out.println(getCellValue(row.getCell(9))+getCellValue(row.getCell(10)));
                String transactionDate = formatDate(getCellValue(row.getCell(9)))+" "+formatTime(getCellValue(row.getCell(10)));
                String accountingTime =  formatDate(getCellValue(row.getCell(9)))+" "+formatTime(getCellValue(row.getCell(10)));

                insertTransaction(connection, account, currency, cardNumber, debitCreditFlag,
                        amount, balance, note, counterpartyAccount, transactionBranch,
                        transactionDate, accountingTime);
            }

            System.out.println("导入成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS bank_transactions (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "account VARCHAR(50)," +
                "currency VARCHAR(20)," +
                "card_number VARCHAR(30)," +
                "debit_credit_flag VARCHAR(10)," +
                "amount DECIMAL(15,2)," +
                "balance DECIMAL(15,2)," +
                "note VARCHAR(50)," +
                "counterparty_account VARCHAR(50)," +
                "transaction_branch VARCHAR(100)," +
                "transaction_date DATE," +
                "accounting_time TIME," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        }
    }

    private static void insertTransaction(Connection connection, String account, String currency,
                                          String cardNumber, String debitCreditFlag, String amount,
                                          String balance, String note, String counterpartyAccount,
                                          String transactionBranch, String transactionDate,
                                          String accountingTime) throws SQLException {
        String insertSQL = "INSERT INTO account_transaction (" +
                "account_number, currency, card_number, debit_credit_flag, transaction_amount, balance, " +
                "note, counterparty_account, transaction_branch, transaction_date, transaction_time" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
            statement.setString(1, account);
            statement.setString(2, currency);
            statement.setString(3, cardNumber);
            statement.setString(4, debitCreditFlag);
            statement.setBigDecimal(5, new java.math.BigDecimal(amount));
            statement.setBigDecimal(6, new java.math.BigDecimal(balance));
            statement.setString(7, note);
            statement.setString(8, counterpartyAccount.isEmpty() ? null : counterpartyAccount);
            statement.setString(9, transactionBranch);
            statement.setString(10, transactionDate);
            statement.setString(11, accountingTime);
            statement.executeUpdate();
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    public static String formatDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            throw new IllegalArgumentException("输入日期字符串不能为空");
        }

        String[] possiblePatterns = {
                "yyyy-MM-dd",
                "yyyy/M/d",
                "yyyyMMdd",
                "EEE MMM dd HH:mm:ss zzz yyyy"
        };

        for (String pattern : possiblePatterns) {
            try {
                if (pattern.contains("EEE")) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);
                    ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateStr, formatter);
                    return zonedDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                } else {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                    LocalDate date = LocalDate.parse(dateStr, formatter);
                    return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                }
            } catch (DateTimeParseException e) {
                // 尝试下一个格式
            }
        }

        throw new IllegalArgumentException("无法解析日期: " + dateStr);
    }

    public static String formatTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            throw new IllegalArgumentException("输入时间字符串不能为空");
        }

        String formattedTime = getString(timeStr);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime.parse(formattedTime, formatter);
            return formattedTime;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("无效的时间: " + timeStr, e);
        }
    }

    private static String getString(String timeStr) {
        String digitsOnly = timeStr.replaceAll("[^0-9]", "");

        if (digitsOnly.length() < 6) {
            throw new IllegalArgumentException("时间字符串数字不足 6 位: " + timeStr);
        }

        String hh = digitsOnly.substring(0, 2);
        String mm = digitsOnly.substring(2, 4);
        String ss = digitsOnly.substring(4, 6);

        return String.format("%s:%s:%s", hh, mm, ss);
    }
}