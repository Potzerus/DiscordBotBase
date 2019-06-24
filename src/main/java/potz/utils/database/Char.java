package potz.utils.database;


import potz.Link;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Char implements Serializable {

    protected ServerStorage parent;
    protected String name;
    protected Long userId;
    protected HashMap<String,Object> stats = new HashMap<>();
    protected ModuleStorage moduleStorage;
    protected List<Link> links=new ArrayList<>();


    Char(long userId, String name, ServerStorage parent) {
        this.userId = userId;
        this.name = name;
        this.parent = parent;
        System.out.println("Created Char: " + name + " for: " + userId);
    }

    public Char(long userId, ModuleStorage moduleStorage){
        this.userId = userId;
        this.name = name;
        //this.moduleStorage=moduleStorage;
    }

    public Char(long userId, ServerStorage parent) {
        this(userId, null, parent);
    }

    public boolean addStat(String name, Object value) {
        if (!stats.containsKey(name)) {
            stats.put(name, value);
            return true;
        }
        return false;
    }

    public void setStat(String name, Object value) {
        if (stats.containsKey(name)) {
            stats.replace(name, value);
        } else {
            stats.put(name, value);
        }
    }

    public void removeStat(String name) {
        stats.remove(name);
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        if (name != null) {
            output.append("Name: ");
            output.append(name);
            output.append('\n');
        }
        for (String key:stats.keySet()) {
            System.out.println(key);
        }
        if (stats.isEmpty()) {
            output.append("No Stats to display");
        } else {
            for (String key : stats.keySet()) {
                output.append(key + ": " + getStat(key));
            }
        }
        return output.toString();
    }

    public Object getStat(String name) {
        return stats.get(name);
    }

    public Object getOrAddStat(String name,Object value) {
        stats.putIfAbsent(name, value);
        return stats.get(name);
    }

    public boolean hasStat(String name) {
        return stats.containsKey(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
