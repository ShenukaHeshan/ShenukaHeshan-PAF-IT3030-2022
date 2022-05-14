/* Decompiler 4ms, total 939ms, lines 63 */
package com;

import model.Electro;
//For REST Service
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
//For JSON
import com.google.gson.*;
//For XML
import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

@Path("/Electro")
public class ElectroService {
   Electro itemObj = new Electro();

   @GET
   @Path("/")
   @Produces({"text/html"})
   public String readElectro() {
      return this.itemObj.readBill();
   }

   @POST
   @Path("/")
   @Consumes({"application/x-www-form-urlencoded"})
   @Produces({"text/plain"})
   public String insertBill(@FormParam("userName") String userName, @FormParam("userCategory") int userCategory, @FormParam("numberOfUnits") int numberOfUnits) {
      String output = this.itemObj.insertBill(userName, userCategory, numberOfUnits);
      return output;
   }

   @PUT
   @Path("/")
   @Consumes({"application/json"})
   @Produces({"text/plain"})
   public String updateBill(String billData) {
      JsonObject itemObject = (new JsonParser()).parse(billData).getAsJsonObject();
      int userID = itemObject.get("userID").getAsInt();
      String userName = itemObject.get("userName").getAsString();
      int userCategory = itemObject.get("userCategory").getAsInt();
      int numberOfUnits = itemObject.get("numberOfUnits").getAsInt();
      String output = this.itemObj.updateBill(userID, userName, userCategory, numberOfUnits);
      return output;
   }

   @DELETE
   @Path("/")
   @Consumes({"application/xml"})
   @Produces({"text/plain"})
   public String deleteItem(String billData) {
      Document doc = Jsoup.parse(billData, "", Parser.xmlParser());
      String userID = doc.select("userID").text();
      String output = this.itemObj.deleteBill(userID);
      return output;
   }
}