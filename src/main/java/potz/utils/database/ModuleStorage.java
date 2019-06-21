package potz.utils.database;

import java.util.HashMap;

public abstract class ModuleStorage<CharType/*,Map*/> {
    protected HashMap<Long,CharType> players;
    //Potential Maps in the future, not yet though
    //protected Map map;

    public ModuleStorage(/*Map map,*/boolean loadPlayers) {
        //this.map = map;
        if(loadPlayers)
            load();
        else
            players=new HashMap<>();
    }

    protected abstract void load();

    public HashMap<Long, CharType> getPlayers() {
        return players;
    }

/*    public Map getMap() {
        return map;
    }*/

    public CharType getPlayer(long id){
        if(!players.containsKey(id))
            players.put(id,genChar(id));
        return players.get(id);

    }

    protected abstract CharType genChar(long id);
}
