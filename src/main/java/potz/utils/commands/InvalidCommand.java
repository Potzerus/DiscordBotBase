package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class InvalidCommand extends SimpleCommand {
    private final String errorMessage="Invalid Command!\nMake sure you spelled the command right!";

    public InvalidCommand(String identifier) {
        super(identifier);
    }

    @Override
    public void execute(User sender, Server s, TextChannel c, String[] args) {
    c.sendMessage(errorMessage);
    }

    public void execute(TextChannel c){
        c.sendMessage(errorMessage);
    }

}
