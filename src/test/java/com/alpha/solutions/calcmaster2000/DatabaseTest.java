package com.alpha.solutions.calcmaster2000;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    void verifyDatabaseData() throws SQLException {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM admin")) {

            System.out.println("Printing data from ADMIN:");
            while (resultSet.next()) {

                System.out.println("Column 1: " + resultSet.getString("Username") +
                        ", Column 2: " + resultSet.getString("Password"));
            }
        }
    }
    @Test
    void testDatabaseConnection() throws SQLException {
        System.out.println("Database URL: " + dataSource.getConnection().getMetaData().getURL());
    }

}
