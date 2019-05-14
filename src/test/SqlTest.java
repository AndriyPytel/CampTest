package test;

import back.Model;
import back.SqlConnector;

import org.junit.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static back.SqlConnector.DATE_FORMAT;

public class SqlTest {
    private static SqlConnector connector;
    private static Model model;

    @BeforeClass
    public static void setUp() throws ParseException {
        connector               = SqlConnector.get();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Date date               = format.parse("2019-02-05");
        model                   = new Model(date,10, "UAH","Some Test");
    }

    @Test
    public void insert() throws SQLException {
        connector.insert(model);
    }

    @Test
    public void delete() throws SQLException {
        connector.insert(model);
        connector.delete("date = 2019-01-01");
    }

    @Test
    public void select() throws SQLException, IOException {
        connector.insert(model);
        List<Model> models = connector.select();

        Assert.assertTrue(models.size() != 0);
    }

    @AfterClass
    public static void tearDown() throws SQLException {
        connector.closeConn();
    }
}
