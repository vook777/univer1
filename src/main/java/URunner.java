import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.foxminded.univer.dao.ConnectionFactory;

public class URunner {
    private static ConnectionFactory connectionFactory = new ConnectionFactory();

    public static void main(String[] args) throws SQLException {
        Statement s = null;
        ResultSet rs = null;
        try (Connection connection = connectionFactory.getConnection()) {
            System.out.println("Connection established DB name = " 
                    + connection.getMetaData().getDatabaseProductName());
            s = connection.createStatement();
            rs = s.executeQuery("delete from students where id = 1");
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
}
