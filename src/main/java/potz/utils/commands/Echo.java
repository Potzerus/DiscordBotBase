package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

public class Echo extends Command{

    private String echo;

    public Echo(String identifier, String description, String echo) {
        super(identifier, description);
        this.echo=echo;
    }

    @Override
    public void execute(User sender, Server s, TextChannel c, String[] args) {
        c.sendMessage(echo);
    }
}
