package potz.utils.commandMaps;

import potz.utils.Module;
import potz.utils.commands.Command;
import potz.utils.database.ServerStorage;
import potz.utils.database.State;

public interface CommandMap extends Iterable<Command> {
    void register(Command cmd);

    Command getCommand(String cmdString);

    void registerAll(Command... cmd);

    int size();

    boolean hasCommand(String identifier);

    boolean hasCommand(Command command);

    void setServerStorage(State parent);

    ServerStorage getServerStorage();

    Module getModule();
    
}
