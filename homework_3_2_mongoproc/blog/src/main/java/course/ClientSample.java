package course;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Robin Wu (rwu@groupon.com)
 * @since 6/14/15.
 */
public class ClientSample {
    public static void main(String[] args) {
        MongoClient mongo = new MongoClient( "localhost" , 27017 );
        MongoDatabase db = mongo.getDatabase("school");
        MongoCollection<Document> collection = db.getCollection("students");

//        MongoCursor it = collection.find().iterator();
//        while (it.hasNext()) {
//            Object obj = it.next();
//        }

        List<Document> all = collection.find().into(new ArrayList<Document>());
        for (Document cur: all) {
            System.out.println(cur);
            List<Document> scores = (List<Document>)cur.get("scores");
//            System.out.println(scores);

            Document min = null;
            for (Document score: scores) {
                if (score.getString("type").equals("homework")) {
                    if (min == null) {
                        min = score;
                    } else {
                        if (score.getDouble("score") < min.getDouble("score")) {
                            min = score;
                        }
                    }

                }
            }
            System.out.println(min);

            List<Document> newScores = new ArrayList<Document>();
            for (Document score: scores) {
                if (score.getString("type").equals("homework")) {
                    if (! score.getDouble("score").equals(min.getDouble("score"))) {
                        newScores.add(score);
                    }
                } else {
                    newScores.add(score);
                }
            }
            System.out.println(newScores);

            // update once
//            collection.updateOne(new Document("_id", cur.get("_id")), new Document("$set", new Document("scores", newScores)));
        }
    }
}
