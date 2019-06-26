package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import potz.utils.commandMaps.CommandMap;

public abstract class Command {
    protected CommandMap commandMap;
    protected final String identifier;
    protected final String description;

    public Command(String identifier){
        this.identifier=identifier;
        description="Default Description";
    }

    public Command(String identifier,String description){
        this.identifier=identifier;
        this.description=description;
    }

    public void execute(MessageCreateEvent event, String[] args) {
        execute(event.getMessageAuthor().asUser().get(),event.getServer().get(),event.getChannel(),args);
    }

    public abstract void execute(User sender, Server s, TextChannel c, String[] args);

    public final String getIdentifier(){
        return identifier;
    }

    public String getDescription(){return description;}

    public void setCommandMap(CommandMap commandMap){
        this.commandMap =commandMap;
    }


}
