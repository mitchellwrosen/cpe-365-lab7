import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;

public class InnReservations {
   public static void main(String args[]) {
      String server;
      String username;
      String password;

      try {
         FileInputStream in = new FileInputStream("../ServerSettings.txt");
         BufferedReader br = new BufferedReader(new InputStreamReader(in));

         server = br.readLine();
         username = br.readLine();
         password = br.readLine();

         br.close();
      } catch (Exception e) {
         System.out.println("Error reading ServerSettings.txt.");
         System.exit(1);
      }



   }
}
