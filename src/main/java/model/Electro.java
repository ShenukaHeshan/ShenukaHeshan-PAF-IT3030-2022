/* Decompiler 31ms, total 973ms, lines 181 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Electro {
   private Connection connect() {
      Connection con = null;

      try {
         Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", "");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return con;
   }

   public String insertBill(String userName, int userCategory, int numberOfUnits) {
      String output = "";
      double priceForUnits = this.calculatePrice(numberOfUnits);
      double taxPrice = this.taxcalculate(userCategory, priceForUnits);

      try {
         Connection con = this.connect();
         if (con == null) {
            return "Error while connecting to the database for inserting.";
         }

         String query = " insert into billing(`userID`,`userName`,`userCategory`,`numberOfUnits`,`monthlyBill`,`tax`,`price`) values (?, ?, ?, ?, ?,?,?)";
         PreparedStatement preparedStmt = con.prepareStatement(query);
         preparedStmt.setInt(1, 0);
         preparedStmt.setString(2, userName);
         preparedStmt.setInt(3, userCategory);
         preparedStmt.setInt(4, numberOfUnits);
         preparedStmt.setDouble(5, priceForUnits + taxPrice);
         preparedStmt.setDouble(6, taxPrice);
         preparedStmt.setDouble(7, priceForUnits);
         preparedStmt.execute();
         con.close();
         output = "Inserted successfully";
      } catch (Exception var12) {
         output = "Error while inserting the item.";
         System.err.println(var12.getMessage());
      }

      return output;
   }

   public String readBill() {
      String output = "";

      try {
         Connection con = this.connect();
         if (con == null) {
            return "Error while connecting to the database for reading.";
         }

         output = "<table border='1'><tr><th>User Name</th><th>User Category</th><th>Number Of Units</th><th>Price for Usage</th><th>Tax</th><th>Monthly Bill</th><th>Update</th><th>Remove</th></tr>";
         String query = "select * from billing";
         Statement stmt = con.createStatement();

         String userID;
         for(ResultSet rs = stmt.executeQuery(query); rs.next(); output = output + "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td><td><form method='post' action='Electro'><input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'><input name='userID' type='hidden' value='" + userID + "'>" + "</form></td></tr>") {
            userID = Integer.toString(rs.getInt("userID"));
            String userName = rs.getString("userName");
            int userCategory = rs.getInt("userCategory");
            int numberOfUnits = rs.getInt("numberOfUnits");
            int price = rs.getInt("price");
            int tax = rs.getInt("tax");
            String monthlyBill = Double.toString(rs.getDouble("monthlyBill"));
            output = output + "<tr><td>" + userName + "</td>";
            output = output + "<td>" + userCategory + "</td>";
            output = output + "<td>" + numberOfUnits + "</td>";
            output = output + "<td>" + price + "</td>";
            output = output + "<td>" + tax + "</td>";
            output = output + "<td>" + monthlyBill + "</td>";
         }

         con.close();
         output = output + "</table>";
      } catch (Exception var13) {
         output = "Error while reading the items.";
         System.err.println(var13.getMessage());
      }

      return output;
   }

   public String updateBill(int userID, String userName, int userCategory, int numberOfUnits) {
      String output = "";
      double priceForUnits = this.calculatePrice(numberOfUnits);
      double taxPrice = this.taxcalculate(userCategory, priceForUnits);

      try {
         Connection con = this.connect();
         if (con == null) {
            return "Error while connecting to the database for updating.";
         }

         String query = "UPDATE billing SET userName=?,userCategory=?,numberOfUnits=?,monthlyBill=? WHERE userID=?";
         PreparedStatement preparedStmt = con.prepareStatement(query);
         preparedStmt.setString(1, userName);
         preparedStmt.setInt(2, userCategory);
         preparedStmt.setInt(3, numberOfUnits);
         preparedStmt.setDouble(4, priceForUnits);
         preparedStmt.setDouble(5, taxPrice);
         preparedStmt.setDouble(6, priceForUnits + taxPrice);
         preparedStmt.setInt(7, userID);
         preparedStmt.execute();
         con.close();
         output = "Updated successfully";
      } catch (Exception var13) {
         output = "Error while updating the item.";
         System.err.println(var13.getMessage());
      }

      return output;
   }

   public String deleteBill(String userID) {
      String output = "";

      try {
         Connection con = this.connect();
         if (con == null) {
            return "Error while connecting to the database for deleting.";
         }

         String query = "delete from billing where userID=?";
         PreparedStatement preparedStmt = con.prepareStatement(query);
         preparedStmt.setString(1, userID);
         preparedStmt.execute();
         con.close();
         output = "Deleted successfully";
      } catch (Exception var6) {
         output = "Error while deleting the item.";
         System.err.println(var6.getMessage());
      }

      return output;
   }

   public Double taxcalculate(int userCategory, Double priceForUnits) {
      double taxRange = 0.0D;

      try {
         Connection con = this.connect();
         String query = "select taxRange from category where categoryID=?";
         PreparedStatement preparedStmt = con.prepareStatement(query);
         preparedStmt.setInt(1, userCategory);

         for(ResultSet rs = preparedStmt.executeQuery(); rs.next(); taxRange = (double)rs.getInt("taxRange")) {
         }

         con.close();
      } catch (Exception var9) {
         System.err.println(var9);
      }

      return taxRange * priceForUnits / 100.0D;
   }

   public double calculatePrice(int numberOfUnits) {
      double priceForUnits = 0.0D;
      if (numberOfUnits <= 50) {
         priceForUnits = (double)(numberOfUnits * 20);
      } else if (numberOfUnits <= 100) {
         priceForUnits = (double)(numberOfUnits * 50);
      } else {
         priceForUnits = (double)(numberOfUnits * 80);
      }

      return priceForUnits;
   }
}