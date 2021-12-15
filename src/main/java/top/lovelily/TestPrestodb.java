package top.lovelily;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Desc: TestPrestodb
 * Author: xuhe
 * Date: 2021/9/26 4:12 下午
 * Version: 1.0
 */
public class TestPrestodb {
    public static void main(String[] args) {
        String url = "jdbc:presto://localhost:8080/tpcds/sf1?SSL=false";
        Connection connection;

        try {
            connection = DriverManager.getConnection(url, "test", null);
            ResultSet resultSet = connection.createStatement().executeQuery("select * from customer");
            System.out.println(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // hdfs://ad7076da5f28:8020
    }

}
