package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import potz.utils.database.ServerStorage;

public abstract class Command {
    protected ServerStorage serverStorage;
    private String identifier;

    public Command(String identifier){
        this.identifier=identifier;
    }

    public abstract void execute(User sender, TextChannel c, Server s, String[] args);

    public final String getIdentifier(){
        return identifier;
    }

    public void setServerStorage(ServerStorage serverStorage){
        this.serverStorage=serverStorage;
    }


}
