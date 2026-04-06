package ds.project.db;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class MongoClientConnection {
    public static void main(String[] args) {
        String connectionString = "mongodb+srv://mbadamik_db_user:oEzYO7HMfKuf3Oec@shovels-db.efko9im.mongodb.net/?appName=shovels-db";
        String databaseName = "shovels-db";
        String collectionName = "users";
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(connectionString))
                .serverApi(serverApi)
                .build();

        // Create a new MongoDB client and connect to the server
        try (MongoClient mongoClient = MongoClients.create(settings)) {
            try {
                MongoDatabase database = mongoClient.getDatabase(databaseName);
                database.runCommand(new Document("ping", 1));
                System.out.println("Successfully connected to MongoDB!");

                // Take input from user
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter a string for MongoDB: ");
                String userInput = sc.nextLine();

                MongoCollection<Document> mongoCollection = database.getCollection(collectionName);
                Document document = new Document("userInput", userInput);
                mongoCollection.insertOne(document);
                System.out.println("Writing a document " + document.getString("userInput") + " to collection " + collectionName);

                // Read documents from a collection
                for (Document doc: mongoCollection.find()) {
                    System.out.println("Fetched document " + doc.getString("userInput"));
                }
            } catch (MongoException e) {
                e.printStackTrace();
            }
        }
    }
}
