package foods.components;

import com.mongodb.*;
import org.springframework.stereotype.Repository;

import java.net.UnknownHostException;
import java.util.List;

@Repository
public class MongoConnector {

    private MongoClient client;
    private DB db;
    public MongoConnector()
    {
        try {
            client = new MongoClient("192.168.83.138");
            db = client.getDB("Fruits");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void manipulateItem(int id, String name, int category)
    {
        DBCollection collection = db.getCollection("fruits");
        try {
            DBObject document = getItem(id);
            document.put("name", name);
            document.put("category", category);
            BasicDBObject search = new BasicDBObject();
            document.put("id", id);
            collection.update(search, document);
        } catch (MongoException e) {

        }
    }
    public void addItem(DBObject data)
    {
        DBCollection collection = db.getCollection("fruits");
        collection.insert(data);
    }
    public void deleteItem(int id)
    {
        try {
            db.getCollection("fruits").remove(getItem(id));
        } catch (MongoException e) {
            e.printStackTrace(System.err);
        }
    }

    public DBObject getItem(int id)
    {
        DBCollection collection = db.getCollection("fruits");
        BasicDBObject document = new BasicDBObject();
        document.put("id", id);
        DBCursor c = collection.find(document);

        if (c.hasNext()) {
            return c.next();
        }

        throw new MongoException("No data found");
    }

    public List<DBObject> getItems()
    {
        return db.getCollection("fruits").find().toArray();
    }
}
