public class DatabaseConstants {
   public static String ROOMS_TABLENAME = "Rooms";
   public static String[] ROOMS_ATTRS = {
      "Id", "Name", "NumBeds", "BedType", "MaxOccupancy", "Price", "Decor"
   };
   public static String ROOMS_TABLE_CREATE = 
      "CREATE TABLE Rooms (" +
      "   Id CHAR(3) PRIMARY KEY," +
      "   Name VARCHAR2(60) NOT NULL," +
      "   NumBeds INTEGER NOT NULL CHECK(NumBeds > 0)," +
      "   BedType VARCHAR2(15) NOT NULL," +
      "   MaxOccupancy INTEGER NOT NULL CHECK(MaxOccupancy > 0)," +
      "   Price INTEGER NOT NULL CHECK(Price > 0)," +
      "   Decor VARCHAR2(20) NOT NULL" +
      ");";

   public static String RESERVATIONS_TABLENAME = "Reservations";
   public static String[] RESERVATIONS_ATTRS = {
      "Code", "RoomId", "CheckInDate", "CheckOutDate", "Rate", "LastName",
      "FirstName", "NumAdults", "NumKids"
   };
   public static String RESERVATIONS_TABLE_CREATE =
      "CREATE TABLE Reservations (" +
      "Code INTEGER PRIMARY KEY," +
      "RoomId CHAR(3) REFERENCES Rooms(Id)," +
      "CheckInDate DATE NOT NULL," +
      "CheckOutDate DATE NOT NULL," +
      "Rate FLOAT NOT NULL CHECK(Rate > 0.00)," +
      "LastName VARCHAR(20) NOT NULL," +
      "FirstName VARCHAR(20) NOT NULL," +
      "NumAdults INTEGER NOT NULL CHECK(NumAdults >= 1)," +
      "NumKids INTEGER NOT NULL CHECK(NumKids >= 0)," +
      "UNIQUE (RoomId, CheckInDate)," +
      "UNIQUE (RoomId, CheckOutDate)" +
      ");";

}
