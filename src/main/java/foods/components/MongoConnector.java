package foods.components;

import com.mongodb.*;
import foods.properties.MongoDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.List;

@Repository
public class MongoConnector {

    private MongoClient client;
    private DB db;

    @Autowired
    private MongoDb config;

    @PostConstruct
    public void initConnection()
    {
        try {
            client = new MongoClient(config.getServer().getHostAddress());
            db = client.getDB(config.getDb());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

    public void manipulateItem(int id, String name, int category)
    {
        DBCollection collection = db.getCollection(config.getCollections().getItems());
        try {
            DBObject document = getItem(id);
            document.put("name", name);
            document.put("category", category);
            BasicDBObject search = new BasicDBObject();
            document.put("id", id);
            collection.update(search, document);
        } catch (MongoException e) {
            e.printStackTrace(System.err);
        }
    }
    public void addItem(DBObject data)
    {
        DBCollection collection = db.getCollection(config.getCollections().getItems());
        collection.insert(data);
    }
    public void deleteItem(int id)
    {
        try {
            db.getCollection(config.getCollections().getItems()).remove(getItem(id));
        } catch (MongoException e) {
            e.printStackTrace(System.err);
        }
    }

    DBObject getItem(int id)
    {
        DBCollection collection = db.getCollection(config.getCollections().getItems());
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
        return db.getCollection(config.getCollections().getItems()).find().toArray();
    }
}
