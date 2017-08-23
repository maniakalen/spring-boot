package foods.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

import java.net.InetAddress;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("mongodb")
public class MongoDb {
    private InetAddress server;
    private String db;
    private final Collections collections = new Collections();

    public InetAddress getServer() {
        return server;
    }

    public void setServer(InetAddress server) {
        this.server = server;
    }

    public String getDb() {
        return db;
    }

    public void setDb(String db) {
        this.db = db;
    }

    public Collections getCollections()
    {
        return this.collections;
    }

    public static class Collections {
        private String items;
        private String categories;

        public String getItems() {
            return items;
        }

        public void setItems(String items) {
            this.items = items;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
        }
    }
}
