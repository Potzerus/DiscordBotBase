package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public abstract class Command {

    public abstract void execute(User sender, TextChannel c, Server s, String[] args);

    public abstract String getIdentifier();

}
