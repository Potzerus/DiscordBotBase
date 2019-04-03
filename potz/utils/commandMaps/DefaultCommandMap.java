package potz.utils.commandMaps;

import potz.utils.commands.Command;
import potz.utils.commands.InvalidCommand;

import java.util.HashMap;
import java.util.Iterator;

public class DefaultCommandMap implements CommandMap {
    private HashMap<String, Command> cmds = new HashMap<>();

    @Override
    public void register(Command cmd) {
        cmds.put(cmd.getIdentifier(),cmd);
    }

    @Override
    public Command getCommand(String cmdString) {
        return cmds.getOrDefault(cmdString, new InvalidCommand());
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
        Iterator<String> iterator=cmds.keySet().iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(identifier))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasCommand(Command command) {
        Iterator<Command> iterator=cmds.values().iterator();
        while(iterator.hasNext()) {
            if(iterator.next().equals(command))
                return true;
        }
        return false;
    }
}
