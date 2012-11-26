import java.sql.Connection;
import java.sql.ResultSet;

public class DatabaseHandle {
   private Connection conn;

   public DatabaseHandle(Connection conn) {
      this.conn = conn;
   }

   public void ExecuteStatement(String statement) {
      try {
         conn.createStatement().executeUpdate(statement);
      } catch (Exception e) {
         System.out.println(e);
      }
   }

   public ResultSet ExecuteQuery(String statement) {
      ResultSet result = null;

      try {
         result = conn.createStatement().executeQuery(statement);
      } catch (Exception e) {
         System.out.println(e);
      }

      return result;
   }
}
