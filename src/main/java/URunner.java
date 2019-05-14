import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.univer.dao.DaoException;
import com.foxminded.univer.dao.PropertiesHolder;

public class URunner {
///
    private static final Logger log = LogManager.getLogger(URunner.class);

    public static void main(String[] args) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) {
            System.out.println("Connection established DB name = " + connection.getMetaData().getDatabaseProductName());
            s = connection.createStatement();
            rs = s.executeQuery("select * from students");
            while (rs.next()) {
                System.out.println(rs.getString("student_card_number") + " " + rs.getString("lastname"));
            }
        } catch (Exception e) {
            System.out.println("Can't connect");
            e.printStackTrace();
        } finally {
            s.close();
            rs.close();
        }
    }

    private static Connection getConnection() {
        PropertiesHolder ph = new PropertiesHolder();
        log.trace("Entered getConnection() method");
        Connection connection = null;
        try {
            log.trace("Getting connection");
            connection = DriverManager.getConnection(ph.getUrl(), ph.getUser(), ph.getPassword());
            log.debug("Created " + connection);
        } catch (SQLException e) {
            log.error("Cannot create connection", e);
            throw new DaoException("Cannot create connection");
        }
        return connection;
    }
}
