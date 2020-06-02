package com.example.MongoJavaConnect;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

public class MongoClass 
{
	public static com.mongodb.client.MongoClient mongoClient;
	
    public static void main( String[] args ) throws Exception
    {
    	String connectionString = "mongodb+srv://user385:dJF93mev7KO2@cluster0-oulsl.mongodb.net/test?retryWrites=true&w=majority";
    	
    	try {
    		mongoClient = MongoClients.create(connectionString);
    		System.out.println("Connection established successfully");

    		//Logger.getLogger("org.mongodb.driver").setLevel(Level.WARNING);  	
    		//Code for listing the databases
    		//List<String> databases = mongoClient.listDatabaseNames().into(new ArrayList<String>());
    		//System.out.println(databases);
    		
    		//local host configuration:
    		//MongoClient mongoClient = new MongoClient("localhost", 27017); //Default port is 27017
        	//DB db = mongoClient.getDB("javatest"); //javatest is the database for local host

    		//windfly is the database, marketing is the collection.
    		//The ip address of a visitor to the windfly website is stored in a document
    		//and the ad ID number is stored for each ad the visitor clicks on.
    		MongoDatabase database = mongoClient.getDatabase("windfly");
    		MongoCollection<Document> marketingCol = database.getCollection("marketing");
    		
    		BasicDBObject document = new BasicDBObject();
    		// Delete All documents from collection using blank BasicDBObject
    		marketingCol.deleteMany(document);
    		
    		Document document1 = new Document("ipaddress", "68.64.65.63")
    		.append("adid", 945236);
    		Document document2 = new Document("ipaddress", "33.85.29.30")
    		.append("adid", 520138);
    		Document document3 = new Document("ipaddress", "74.22.85.19")
    		.append("adid", 243512);
    		Document document4 = new Document("ipaddress", "55.44.33.22")
    		.append("adid", 482076);
    		List<Document> documents = new ArrayList<Document>();
    		documents.add(document1);
    		documents.add(document2);
    		documents.add(document3);
    		documents.add(document4);
    		marketingCol.insertMany(documents);
    		//Update the document with IP address 33.85.29.30, set ad ID to 864725
    		marketingCol.updateOne(Filters.eq("ipaddress", "33.85.29.30"), Updates.set("adid", 864725));
    		
    		//Delete the document with IP address 68.64.65.63
    		marketingCol.deleteOne(Filters.eq("ipaddress", "68.64.65.63"));
    		
    		//Get the iterable object 
    	    FindIterable<Document> iterDoc = marketingCol.find(); 
    	    Iterator it = iterDoc.iterator();
    	    System.out.println("Documents in the marketing collection:");
    	    
    	    while (it.hasNext()) {  
    	       System.out.println(it.next());
    	    }
    	    
    	    System.out.println("Collections in the windfly database:");
    	    for (String name : database.listCollectionNames()) { 
    	         System.out.println(name); 
    	    }
    	}
        catch (Exception e) {
        	e.printStackTrace();
        }
    	finally {
    		mongoClient.close();
    	}
    }
}