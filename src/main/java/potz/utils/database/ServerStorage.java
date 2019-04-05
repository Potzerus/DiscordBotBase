package potz.utils.database;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerStorage {

    private HashMap<Long, Char> players = new HashMap<>();
    private Map<String,String> properties=new HashMap<>();
    private String serverName;
    private long serverId;


    public ServerStorage(Long serverId) {
        this.serverId = serverId;

    }

    public ServerStorage(JSONObject serverObject) {
        serverId=serverObject.getLong("serverId");
        if(serverObject.has("serverName"))
            serverName=serverObject.getString("serverName");
        JSONObject properties=serverObject.getJSONObject("properties");
        Iterator propertyIterator=properties.keys();
        while(propertyIterator.hasNext()){
            String key=propertyIterator.next().toString();
            addProperty(key,properties.getString(key));
            }
            JSONArray players=serverObject.getJSONArray("players");
        for (int i = 0; i < players.length(); i++) {
            JSONObject player=(JSONObject)players.get(i);
            addPlayer(player);
        }
    }

    public boolean addPlayer(long userId) {
        if (!players.containsKey(userId)) {
            players.put(userId, new Char(userId,this));
            return true;
        } else return false;
    }

    public void addPlayer(JSONObject player){
        players.put(player.getLong("userId"),new Char(player,this));
    }


    public void removePlayer(long playerId) {
        players.remove((playerId));
    }

    public Char getPlayer(long playerId) {
        return players.getOrDefault(playerId, null);
    }

    public String getServerName() {
        return serverName;
    }

    public void addProperty(String propertyName,String propertyValue){
        properties.put(propertyName,propertyValue);
    }


    @Override
    public String toString() {
        return serverName + ": " + serverId;
    }

    public JSONObject toJson() {
        JSONObject server = new JSONObject();
        JSONArray users = new JSONArray();
        Iterator i = this.players.keySet().iterator();
        int count = 0;
        while (i.hasNext()) {
            Long key = (Long) i.next();
            users.put(count, this.players.get(key).toJson());
            count++;
        }
        server.put("serverId", serverId);
        server.put("users", users);
        return server;
    }
}
