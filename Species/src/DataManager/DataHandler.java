package DataManager;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static final String jdbcUrl = "jdbc:mysql://localhost:3306/schema_name";
    private static final String username = "root";
    private static final String password = "1601968714";

    public static void insertDataBatch(List<DataEntry> dataEntries) {
        try {
            // Step 1: Establish the database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Step 2: Prepare the SQL statement for batch insert
            String sqlInsert = "INSERT INTO animals (time, name, Yaxis, Xaxis, energy, energyConsumption) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsert);

            // Step 3: Set the values for the placeholders in the SQL statement for each data entry
            for (DataEntry entry : dataEntries) {
                preparedStatement.setInt(1, entry.getTime());
                preparedStatement.setString(2, entry.getName());
                preparedStatement.setInt(3, entry.getYaxis());
                preparedStatement.setInt(4, entry.getXaxis());
                preparedStatement.setDouble(5, entry.getEnergy());
                preparedStatement.setInt(6, entry.getEnergyConsumption());

                // Add the current set of values to the batch
                preparedStatement.addBatch();
            }

            // Step 4: Execute the batch insert
            int[] rowsAffected = preparedStatement.executeBatch();

            //System.out.println(rowsAffected.length + " rows inserted successfully.");

            // Step 5: Close the resources
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void truncateTable(String tableName) {
        try {
            // Step 1: Establish the database connection
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Step 2: Create a Statement object
            Statement statement = connection.createStatement();

            // Step 3: Truncate the table
            String truncateQuery = "TRUNCATE TABLE " + tableName;
            statement.execute(truncateQuery);

            // Step 4: Close the resources
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
