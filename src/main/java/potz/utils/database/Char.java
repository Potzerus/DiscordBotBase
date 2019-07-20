package potz.utils.database;


import org.json.JSONArray;
import org.json.JSONObject;
import potz.Link;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Char implements Serializable {

    protected ServerStorage parent;
    protected String name;
    protected Long userId;
    protected HashMap<String, Object> properties = new HashMap<>();
    protected ModuleStorage moduleStorage;
    protected List<Link> links = new ArrayList<>();


    Char(long userId, String name, ServerStorage parent) {
        this.userId = userId;
        this.name = name;
        this.parent = parent;
        System.out.println("Created Char: " + name + " for: " + userId);
    }

    public Char(long userId, ModuleStorage moduleStorage) {
        this.userId = userId;
        this.name = name;
        //this.moduleStorage=moduleStorage;
    }

    public Char(long userId, ServerStorage parent) {
        this(userId, null, parent);
    }

    public Char(JSONObject jsonObject, ServerStorage parent) {

    }

    public boolean addStat(String name, Object value) {
        if (!properties.containsKey(name)) {
            properties.put(name, value);
            return true;
        }
        return false;
    }

    public void setStat(String name, Object value) {
        if (properties.containsKey(name)) {
            properties.replace(name, value);
        } else {
            properties.put(name, value);
        }
    }

    public void removeStat(String name) {
        properties.remove(name);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        if (name != null) {
            output.append("Name: ");
            output.append(name);
            output.append('\n');
        }
        for (String key : properties.keySet()) {
            System.out.println(key);
        }
        if (properties.isEmpty()) {
            output.append("No Stats to display");
        } else {
            for (String key : properties.keySet()) {
                output.append(key + ": " + getStat(key));
            }
        }
        return output.toString();
    }

    public Object getStat(String name) {
        return properties.get(name);
    }

    public Object getOrAddStat(String name, Object value) {
        properties.putIfAbsent(name, value);
        return properties.get(name);
    }

    public boolean hasStat(String name) {
        return properties.containsKey(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return userId;
    }

    public ServerStorage getParent() {
        return parent;
    }

    public JSONObject toJSON() {
        JSONObject jObject = new JSONObject();
        jObject.append("id", userId);
        if (name != null)
            jObject.append("name", name);
        JSONObject jChild = new JSONObject();
        jObject.append("properties", jChild);
        for (String s : properties.keySet()) {
            jChild.append(s, properties.get(s).toString());
        }
        return jObject;
    }
}
