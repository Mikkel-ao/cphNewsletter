package app.persistence;

import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SubscriberMapper {
    public static int subscribe(String email, ConnectionPool connectionPool) throws DatabaseException {

        String sql = "INSERT INTO subscriber (email, creation_date) VALUES (?, CURRENT_DATE) ON CONFLICT (email) DO NOTHING";
        try (
                Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setString(1, email);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }
        catch (SQLException e) {
            String msg = "Der er sket en fejl under din tilmelding til nyhedsbrev. Prøv igen";
            throw new DatabaseException(msg, e.getMessage());
        }
    }

    // TODO: Implement unsubscribe function?
}
