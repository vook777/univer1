import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.foxminded.univer.dao.DaoException;

public class URunner {
    
    private static final Logger log = LogManager.getLogger(URunner.class);

    public static void main(String[] args) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try (Connection connection = getConnection()) {
            System.out.println("Connection established DB name = " + connection.getMetaData().getDatabaseProductName());
            s = connection.createStatement();
            rs = s.executeQuery("update students set group_id = 2 where id = 2");
            while (rs.next()) {
                System.out.println(rs.getString("firstname") + rs.getString("lastname"));
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
        log.trace("Entered getConnection() method");
        Connection connection = null;
        try {
            log.trace("Getting connection");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbunit", "postgres",
                    "123tester123");
            log.debug("Created " + connection);
        } catch (SQLException e) {
            log.error("Cannot create connection", e);
            throw new DaoException("Cannot create connection");
        }
        return connection;
    }
}
