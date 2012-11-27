import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.sql.ResultSet;

public class InnDatabaseUtils {
   public static final void CreateTables(DatabaseHandle handle) {
      handle.createStatement();
      handle.executeStatement(DatabaseConstants.ROOMS_TABLE_CREATE);
      handle.executeStatement(DatabaseConstants.RESERVATIONS_TABLE_CREATE);
   }

   public static void PopulateTables(DatabaseHandle handle) {
      try {
         FileInputStream in = new FileInputStream("config/inserts.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(in));

         String line;
         handle.createStatement();
         while ((line = br.readLine()) != null)
            handle.executeStatement(line);

         br.close();
      } catch (Exception e) {
         System.out.println("Error reading inserts.txt.");
         System.exit(1);
      }
   }

   public static Integer GetNumRooms(DatabaseHandle handle) {
      ResultSet rooms;
      int numRooms = 0;

      try {
         handle.createStatement();
         if ((rooms = handle.executeQuery("SELECT * FROM " +
               DatabaseConstants.ROOMS_TABLENAME)) == null) {
            return null;
         }

         while (rooms.next())
            numRooms++;

         return numRooms;
      } catch (java.sql.SQLException e) {
         System.out.println(e);
         return null;
      }
   }

   public static Integer GetNumReservations(DatabaseHandle handle) {
      ResultSet reservations;
      int numReservations = 0;

      try {
         handle.createStatement();
         if ((reservations = handle.executeQuery("SELECT * FROM " +
               DatabaseConstants.RESERVATIONS_TABLENAME)) == null) {
            return null;
         }

         while (reservations.next())
            numReservations++;

         return numReservations;
      } catch (java.sql.SQLException e) {
         System.out.println(e);
         return null;
      }
   }
}
