package potz.utils.commandMaps;

import potz.utils.Module;
import potz.utils.commands.Command;
import potz.utils.commands.InvalidCommand;
import potz.utils.database.ModuleStorage;
import potz.utils.database.ServerStorage;
import potz.utils.database.State;

import java.util.HashMap;
import java.util.Iterator;

public class DefaultCommandMap implements CommandMap {
    private HashMap<String, Command> cmds = new HashMap<>();
    private ServerStorage serverStorage;
    private ModuleStorage moduleStorage;
    private Module module;

    public DefaultCommandMap(Module module,ServerStorage serverStorage){
        this.module=module;
        this.serverStorage=serverStorage;
    }

    @Override
    public void setServerStorage(State parent){
        parent.getServer(serverStorage.getServerId());
    }

    @Override
    public ServerStorage getServerStorage() {
        return serverStorage;
    }

    @Override
    public void register(Command cmd) {
        cmd.setCommandMap(this);
        cmds.put(cmd.getIdentifier(),cmd);
    }

    @Override
    public Command getCommand(String cmdString) {
        return cmds.getOrDefault(cmdString, new InvalidCommand(null));
    }

    @Override
    public void registerAll(Command... cmd) {
        for (Command curr : cmd) {
            register(curr);
        }
    }

    @Override
    public int size() {
        return cmds.size();
    }

    @Override
    public boolean hasCommand(String identifier) {
        for (String s : cmds.keySet()) {
            if (s.equals(identifier))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasCommand(Command command) {
        for (Command command1 : cmds.values()) {
            if (command1.equals(command))
                return true;
        }
        return false;
    }

    @Override
    public Module getModule(){
        return module;
    }

    @Override
    public String toString() {
        String output="Commands:";

        for (String key:cmds.keySet()) {
                output+="\n\t"+key;
        }

        return output;
    }

    @Override
    public Iterator<Command> iterator() {
        return cmds.values().iterator();
    }

}
