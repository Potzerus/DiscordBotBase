package potz.utils.database;


import potz.utils.Module;

import java.io.Serializable;
import java.util.*;

public class ServerStorage implements Serializable, Iterable<Char> {

    private HashMap<Long, Char> players = new HashMap<>();
    private Map<String, Object> properties = new HashMap<>();
    private String serverName;
    private long serverId;
    private State parent;
    private List<Module> activeGames = new ArrayList<>();
    private HashMap<Module, ModuleStorage> moduleStorages = new HashMap<>();

    public ServerStorage(Long serverId, State parent) {
        this.serverId = serverId;
        this.parent = parent;


    }

    public Char addPlayer(long userId) {
        return addPlayer(null, userId);
    }

    public Char addPlayer(String name, long userId) {
        if (!players.containsKey(userId)) {
            players.put(userId, new Char(userId, name, this));
            return players.get(userId);
        } else return null;
    }


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

    //Replace Property with ModuleStorage Asap
    public void addProperty(String propertyName, Object propertyValue) {
        if (properties.keySet().contains(propertyName))
            properties.remove(propertyName);
        properties.put(propertyName, propertyValue);
    }

    //Replace Property with ModuleStorage Asap
    public Object getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    public void removeProperty(String propertyName) {
        properties.remove(propertyName);
    }

    public void addModule(Module... modules) {
        for (Module m : modules) {
            if(!activeGames.contains(m))
            activeGames.add(m);
        }
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

    @Override
    public Iterator<Char> iterator() {
        return players.values().iterator();
    }
}
