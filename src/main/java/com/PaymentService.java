package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Payment;





@Path("/Payment")
public class PaymentService {
	Payment paymentObj = new Payment();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readPayment() {
		return paymentObj.readPayment();
	}

	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertPayment(@FormParam("paymentName") String paymentName,
			@FormParam("paymenttype") String paymenttype, @FormParam("paymentDesc") String paymentDesc,@FormParam("paymentdate") String paymentdate) {
		String output = paymentObj.insertPayment(paymentName, paymenttype, paymentDesc,paymentdate);
		return output;
	}

	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePayment(String paymentData) {
		// Convert the input string to a JSON object
		JsonObject paymentObject = new JsonParser().parse(paymentData).getAsJsonObject();
		// Read the values from the JSON object
		String paymentID = paymentObject.get("paymentID").getAsString();
		String paymentName = paymentObject.get("paymentName").getAsString();
		String paymenttype = paymentObject.get("paymenttype").getAsString();
		String paymentDesc = paymentObject.get("paymentDesc").getAsString();
		String paymentdate = paymentObject.get("paymentdate").getAsString();
		String output = paymentObj.updatePayment(paymentID, paymentName, paymenttype, paymentDesc, paymentdate);
		return output;
	}

	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deletePayment(String paymentData) {
		// Convert the input string to an XML document
		Document doc = Jsoup.parse(paymentData, "", Parser.xmlParser());

		// Read the value from the element <paymentID>
		String paymentID = doc.select("paymentID").text();
		String output = paymentObj.deletePayment(paymentID);
		return output;
	}

}