package potz.utils.database;


import potz.utils.Module;

import java.io.Serializable;
import java.util.*;

public class ServerStorage implements Serializable {

    private HashMap<Long, Char> players = new HashMap<>();
    private Map<String, Object> properties = new HashMap<>();
    private String serverName;
    private long serverId;
    private State parent;
    private List<Module> activeGames = new ArrayList<>();

    public ServerStorage(Long serverId, State parent) {
        this.serverId = serverId;
        this.parent = parent;


    }

    /*
    public ServerStorage(JSONObject serverObject) {
        serverId = serverObject.getLong("serverId");
        if (serverObject.has("serverName"))
            serverName = serverObject.getString("serverName");
        JSONObject properties = serverObject.getJSONObject("properties");
        Iterator propertyIterator = properties.keys();
        while (propertyIterator.hasNext()) {
            String key = propertyIterator.next().toString();
            addProperty(key, properties.getString(key));
        }
        JSONArray players = serverObject.getJSONArray("players");
        for (int i = 0; i < players.length(); i++) {
            JSONObject player = (JSONObject) players.get(i);
            addPlayer(player);
        }
    }
*/
    public Char addPlayer(long userId) {
        return addPlayer(null, userId);
    }

    public Char addPlayer(String name, long userId) {
        if (!players.containsKey(userId)) {
            players.put(userId, new Char(userId, name, this));
            return players.get(userId);
        } else return null;
    }
/*
    public void addPlayer(JSONObject player) {
        players.put(player.getLong("userId"), new Char(player, this));
    }
*/

    public void removePlayer(long playerId) {
        players.remove((playerId));
    }

    public Char getPlayer(long playerId) {
        return players.get(playerId);
    }

    public Char getOrAddPlayer(long playerId) {
        if (!players.containsKey(playerId))
            players.put(playerId, new Char(playerId, this));
        return players.get(playerId);
    }

    public String getServerName() {
        return serverName;
    }

    public void addProperty(String propertyName, Object propertyValue) {
        if (properties.keySet().contains(propertyName))
            properties.remove(propertyName);
        properties.put(propertyName, propertyValue);
    }

    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    public void addModule(Module... modules) {
        activeGames.addAll(Arrays.asList(modules));
    }

    public boolean hasActiveModule(String identifier) {
        for (Module m : activeGames) {
            if (m.getIdentifier().equals(identifier))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return serverName + ": " + serverId;
    }

    public State getParent() {
        return parent;
    }

    public long getServerId() {
        return serverId;
    }
    /*
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
    */
}
