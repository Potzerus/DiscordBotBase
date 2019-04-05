package potz.utils.database;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

public class Char {

    private ServerStorage parent;
    private Long userId;
    private HashMap<String, String> stats = new HashMap<>();


    Char(long userId, ServerStorage parent) {
        this.parent = parent;
        this.userId = userId;
        System.out.println("Created Char for: " + userId);
    }

    Char(JSONObject player, ServerStorage parent) {
        this.parent=parent;
        userId=player.getLong("userId");
        JSONObject stats=player.getJSONObject("stats");
        Iterator stat=stats.keys();
        while(stat.hasNext()){
            String key=stat.next().toString();
            setStat(key,stats.getString(key));
        }
    }

    public String check() {
           return this.toString();
        }


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

    public boolean addStat(String name, String value){
        if(stats.containsKey(name)){
            stats.put(name,value);
            return true;
        }
        return false;
    }

    public void setStat(String name, String value) {
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
        if (stats.isEmpty()) {
            return "No Stats to display";
        } else {
            Iterator<String> keys = stats.keySet().iterator();
            Iterator<String> values = stats.values().iterator();
            while (keys.hasNext()) {
                output.append(keys.next() + ": " + values.next() + '\n');
            }
        }
        return output.toString();
    }

    public String getStat(String name){
        return stats.get(name);
    }

}
