package foods.web;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import foods.components.MongoConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Autowired
    private MongoConnector connector;

    @RequestMapping(value = "/rest/list", method = RequestMethod.GET)
    @ResponseBody
    public String list() {
        return new Gson().toJson(connector.getItems());
    }

    @RequestMapping(value = "/rest/add", method = RequestMethod.POST)
    @ResponseBody
    public String add(@RequestBody Object body)
    {
        DBObject obj = (new Gson()).fromJson(body.toString(), BasicDBObject.class);
        connector.addItem(obj);
        return (new Gson()).toJson(obj);
    }

    @RequestMapping(value = "/rest/change/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String change(@PathVariable("id") int id, @RequestBody Object body)
    {
        DBObject obj = (new Gson()).fromJson(body.toString(), BasicDBObject.class);
        connector.manipulateItem(id, (String)obj.get("name"), ((Double)obj.get("category")).intValue());
        return (new Gson()).toJson(obj);
    }
    @RequestMapping(value = "/rest/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable("id") int id)
    {
        connector.deleteItem(id);
    }
}