package foods.web;

import com.google.gson.Gson;
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
    public String add(@RequestBody DBObject body)
    {
        connector.addItem(body);
        return "DONE";
    }

    @RequestMapping(value = "/rest/change/{id}", method = RequestMethod.POST)
    @ResponseBody
    public String change(@PathVariable("id") int id, @RequestBody DBObject body)
    {
        connector.manipulateItem(id, (String)body.get("name"), (int)body.get("category"));
        return "DONE";
    }
    @RequestMapping(value = "/rest/delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable("id") int id)
    {
        connector.deleteItem(id);
        return "DONE";
    }
}