package calpoly;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseHandle {
   private Connection conn;
   private Statement statement;

   public DatabaseHandle(Connection conn) {
      this.conn = conn;

      createStatement();
   }

   public void createStatement() {
      try {
         this.statement = conn.createStatement();
      } catch (java.sql.SQLException e) {
         System.out.println(e);
         System.exit(1);
      }
   }

   public void executeStatement(String statement) {
      try {
         this.statement.executeUpdate(statement);
      } catch (java.sql.SQLException e) {
         System.out.println(e + " (\"" + statement + "\")");
      }
   }

   public ResultSet executeQuery(String statement) {
      ResultSet result = null;

      try {
         result = this.statement.executeQuery(statement);
      } catch (Exception e) {
         System.out.println(e + " (\"" + statement + "\")");
      }

      return result;
   }
}
