package ru.netology.web.test.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    @SneakyThrows
    public static DataHelper.VerificationCode getVerificationCode() {
        // тянем код из таблицы в БД
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        var conn = getConn();
        var code = QueryRunner.query(conn, codeSQL, new ScalarHandler<String>());
        return new DataHelper.VerificationCode(code);
    }

    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QueryRunner.execute(connection, "DELETE FROM auth_codes");
        QueryRunner.execute(connection, "DELETE FROM card_transactions");
        QueryRunner.execute(connection, "DELETE FROM cards");
        QueryRunner.execute(connection, "DELETE FROM users");
    }

    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QueryRunner.execute(connection, "DELETE FROM auth_codes");
    }

}
