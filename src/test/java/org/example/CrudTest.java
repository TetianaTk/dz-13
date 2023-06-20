/*
Створити тестовий метод, який підключиться до бази даних
і здійснить 4 основні види запитів (INSERT, SELECT, UPDATE, DELETE),
використовувати PreparedStatement з параметрами.
 */

package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CrudTest
{

    @Test(dataProviderClass = CrudTestDataProvider.class, dataProvider = "generate person")
    public void testCRUDoperations(String fistName, String lastName, int age, String newFistName)
    {
        String url = "jdbc:postgresql://localhost:4567/person_db"; //?options=--search_path%3Dtest_s
        String db_user = "postgres";
        String db_password = "mypassword";
        try(Connection dbConnection = DriverManager.getConnection(url, db_user, db_password)) {
            SoftAssert softAssert = new SoftAssert();

            String sqlPattern = "INSERT INTO person (first_name, last_name, age) VALUES (?, ?, ?)";
            PreparedStatement sqlStatement = dbConnection.prepareStatement(sqlPattern);
            sqlStatement.setString(1, fistName);
            sqlStatement.setString(2, lastName);
            sqlStatement.setInt(3, age);
            int rowsNum = sqlStatement.executeUpdate();
            softAssert.assertEquals(rowsNum, 1);

            sqlPattern = "SELECT * FROM person WHERE first_name=? AND last_name=?";
            sqlStatement = dbConnection.prepareStatement(sqlPattern);
            sqlStatement.setString(1, fistName);
            sqlStatement.setString(2, lastName);
            ResultSet resultSet = sqlStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet.getString("first_name"));
            softAssert.assertEquals(resultSet.getString("first_name"), fistName);
            int id = resultSet.getInt("id");

            sqlPattern = "UPDATE person SET first_name=? WHERE id=?";
            sqlStatement = dbConnection.prepareStatement(sqlPattern);
            sqlStatement.setString(1, newFistName);
            sqlStatement.setInt(2, id);
            rowsNum = sqlStatement.executeUpdate();
            softAssert.assertEquals(rowsNum, 1);

            sqlPattern = "DELETE FROM person WHERE id=?";
            sqlStatement = dbConnection.prepareStatement(sqlPattern);
            sqlStatement.setInt(1, id);
            rowsNum = sqlStatement.executeUpdate();
            softAssert.assertEquals(rowsNum, 1);

            softAssert.assertAll();

        } catch (SQLException e) {
            System.err.print(e);
        }
    }
}
