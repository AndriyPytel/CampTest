package back;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlConnector {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DB_URL = "jdbc:mysql://remotemysql.com:3306/2vLAUQH3G3";
    private static final String USER = "2vLAUQH3G3";
    private static final String PASS = "UBIy0750h2";

    private Connection conn = null;

    private static SqlConnector instance = null;

    private SqlConnector() {
        try {
            openConn();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static SqlConnector get() {
        if(instance == null) {
            instance = new SqlConnector();
        }
        return instance;
    }

    public void insert(Model model) throws SQLException {
        Statement stmt = conn.createStatement();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String INSERT_TEMPLATE = "INSERT  INTO `purchase` (`name`, `date`, `amount`, `currency`) VALUES ('";
        String sql = INSERT_TEMPLATE
                + model.getName() + "', '"
                + format.format(model.getDate()) + "', '"
                + model.getAmount() + "', '"
                + model.getCurrency() + "');";

        stmt.executeUpdate(sql);
    }

    public void delete(String condition) throws SQLException {
        Statement stmt = conn.createStatement();
        String DELETE_TEMPLATE = "DELETE FROM `purchase` WHERE ";
        String sql = DELETE_TEMPLATE + condition + ";";

        stmt.executeUpdate(sql);
    }

    public List<Model> select(String ... conditions) throws SQLException, IOException {
        Statement stmt = conn.createStatement();
        String SELECT_TEMPLATE = "SELECT `name`, `date`, `amount`, `currency` FROM `purchase`";
        String sql = SELECT_TEMPLATE;
        if (conditions.length != 0){
            sql += " WHERE ";
        }

        for(String condition : conditions) {
            sql += condition ;
        }

        sql += ";";

        ResultSet resultSet = stmt.executeQuery(sql);
        List<Model> models = new ArrayList<>();

        while (resultSet.next()) {
            String name = resultSet.getString("name");
            Date date = resultSet.getDate("date");
            double amount = resultSet.getDouble("amount");
            String currency = resultSet.getString("currency");

            models.add(new Model(date, amount, currency, name));
        }

        return models;
    }

    private void openConn() throws SQLException {
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public void closeConn() throws SQLException {
        if(conn != null)
            conn.close();
    }
}