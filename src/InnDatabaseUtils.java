import java.sql.ResultSet;

public class InnDatabaseUtils {
   public static void CreateRoomsTable(DatabaseHandle handle) {
      handle.executeStatement(DatabaseConstants.ROOMS_TABLE_CREATE);
   }

   public static void CreateReservationsTable(DatabaseHandle handle) {
      handle.executeStatement(DatabaseConstants.RESERVATIONS_TABLE_CREATE);
   }

   public static void PopulateRoomsTable(DatabaseHandle handle) {
      // TODO
   }

   public static void PopulateReservationsTable(DatabaseHandle handle) {
      // TODO
   }

   public static Integer GetNumRooms(DatabaseHandle handle) {
      ResultSet rooms;
      int numRooms = 0;

      try {
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
