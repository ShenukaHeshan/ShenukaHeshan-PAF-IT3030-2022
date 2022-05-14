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

      // Prepare the html table to be displayed
         output = "<table border='1'><tr><th style=\"padding: 5px;\">User Name</th><th style=\"display:none;\"></th><th style=\"padding: 5px;\">User Category</th><th style=\"padding: 5px;\">Number Of Units</th><th style=\"padding: 5px;\">Charge for use </th><th style=\"padding: 5px;\">Tax for usage</th><th style=\"padding: 5px;\">Monthly Bill</th><th style=\"padding: 5px;\">Update</th><th style=\"padding: 5px;\">Remove</th></tr>";
         String query = "SELECT b.userID, b.userName, b.numberOfUnits, b.monthlyBill, b.tax, b.price, b.userCategory, c.categoryName FROM billing b, category c WHERE b.userCategory = c.categoryID";
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(query);
         
      // iterate through the rows in the result set   
			while (rs.next()) {
	            String userID = Integer.toString(rs.getInt("userID"));
	            String userName = rs.getString("userName");
	            int userCategory = rs.getInt("userCategory");
	            String usercat = rs. getString("categoryName");
	            int numberOfUnits = rs.getInt("numberOfUnits");
	            int price = rs.getInt("price");
	            int tax = rs.getInt("tax");
	            String monthlyBill = Double.toString(rs.getDouble("monthlyBill"));
         
	            
	         // Add into the html table

				output += "<tr><td style=\"padding: 5px;\"><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + userID
						+ "'>" + userName + "</td>";
				output += "<td style=\"display:none;\">" + userCategory + "</td>";
				output += "<td style=\"padding: 5px;\">" + usercat + "</td>";
				output += "<td style=\"padding: 5px;\">" + numberOfUnits + "</td>";
				output += "<td style=\"padding: 5px;\">" + price + "</td>";
				output += "<td style=\"padding: 5px;\">" + tax + "</td>";
				output += "<td style=\"padding: 5px;\">" + monthlyBill + "</td>";
// buttons
				output += "<td style=\"padding: 5px;\"><input name='btnUpdate' type='button'  value='Update' class='btn-sm btnUpdate btn btn-secondary'></td> <td style=\"padding: 5px;\"><form method='post' action='Billing.jsp'> <input name='btnRemove' type='submit' value='Remove' class=' btn-sm btn btn-danger'> <input name='hidItemIDDelete' type='hidden' value='" 
						 + userID + "'>" + "</form></td></tr>"; 
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

         String query = "UPDATE billing SET userName=?,userCategory=?,numberOfUnits=?,price=?,tax=?,monthlyBill=? WHERE userID=?";
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

      return taxRange * priceForUnits / 100.00;
   }

   public double calculatePrice(int numberOfUnits) {
      double priceForUnits = 0.00;
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