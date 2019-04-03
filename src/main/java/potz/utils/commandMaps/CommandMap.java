package potz.utils.commandMaps;

import potz.utils.commands.Command;

public interface CommandMap {
    void register(Command cmd);

    Command getCommand(String cmdString);

    void registerAll(Command... cmd);

    int size();

    boolean hasCommand(String identifier);

    boolean hasCommand(Command command);

    
}
