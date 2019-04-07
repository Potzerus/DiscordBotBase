package potz.utils.database;


import org.json.JSONArray;
import org.json.JSONObject;
import potz.utils.Module;

import java.util.*;

public class ServerStorage {

    private HashMap<Long, Char> players = new HashMap<>();
    private Map<String, Capsule> properties = new HashMap<>();
    private String serverName;
    private long serverId;
    private List<Module> activeGames = new ArrayList<>();


    public ServerStorage() {
    }

    public ServerStorage(Long serverId) {
        this.serverId = serverId;

    }

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

    public boolean addPlayer(long userId) {
        if (!players.containsKey(userId)) {
            players.put(userId, new Char(userId, this));
            return true;
        } else return false;
    }

    public void addPlayer(JSONObject player) {
        players.put(player.getLong("userId"), new Char(player, this));
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

    public void addProperty(String propertyName, Object propertyValue) {
        if(properties.keySet().contains(propertyName))
        properties.remove(propertyName);
        properties.put(propertyName, new Capsule<>(propertyValue));
    }

    public Object getProperty(String propertyName) {
        return properties.get(propertyName).getValue();
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
