package potz.utils.database;

import java.util.HashMap;

public abstract class ModuleStorage<Map> {
    protected HashMap<Long,Char> players;
    protected Map map;

    public ModuleStorage(Map map,boolean loadPlayers) {
        this.map = map;
        if(loadPlayers)
            load();
        else
            players=new HashMap<>();
    }

    protected abstract void load();

    public HashMap<Long, Char> getPlayers() {
        return players;
    }

    public Map getMap() {
        return map;
    }

    public Char getPlayer(long id){
        if(!players.containsKey(id))
            players.put(id,genChar(id));
        return players.get(id);

    }

    protected abstract Char genChar(long id);
}
