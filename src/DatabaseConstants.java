public class DatabaseConstants {
   public static String[] ROOMS_ATTRS = {
      "Id", "Name", "NumBeds", "BedType", "MaxOccupancy", "Price", "Decor"
   };

   public static String[] RESERVATIONS_ATTRS = {
      "Code", "RoomId", "CheckInDate", "CheckOutDate", "Rate", "LastName",
      "FirstName", "NumAdults", "NumKids"
   };
}
