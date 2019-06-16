package potz.utils.database;


import java.io.Serializable;
import java.util.HashMap;

public class Char implements Serializable {

    private ServerStorage parent;
    private String name;
    private Long userId;
    private HashMap<String,Object> stats = new HashMap<>();


    Char(long userId, String name, ServerStorage parent) {
        this.userId = userId;
        this.name = name;
        this.parent = parent;
        System.out.println("Created Char: " + name + " for: " + userId);
    }

    Char(long userId, ServerStorage parent) {
        this(userId, null, parent);
    }
/*
    Char(JSONObject player, ServerStorage parent) {
        this.parent = parent;
        userId = player.getLong("userId");
        JSONObject stats = player.getJSONObject("stats");
        Iterator stat = stats.keys();
        while (stat.hasNext()) {
            String key = stat.next().toString();
            setStat(key, stats.getString(key));
        }
    }
*/

    /*
    public JSONObject toJson() {
        JSONObject user = new JSONObject();
        user.put("userId", userId);
        JSONObject stats=new JSONObject();
        Iterator<String> keys = stats.keySet().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            user.put(key, stats.get(key));
        }
        System.out.println(user);
        return user;
    }
    */
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
