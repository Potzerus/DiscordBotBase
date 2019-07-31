package potz.utils.commands;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import potz.Utils;

public abstract class RestrictedCommand extends Command {

    final protected PermissionType requiredPerm;


    public RestrictedCommand(String identifier, PermissionType requiredPerm) {
        super(identifier);
        this.requiredPerm = requiredPerm;

    }
    public RestrictedCommand(String identifier,String description, PermissionType requiredPerm) {
        super(identifier,description);
        this.requiredPerm = requiredPerm;

    }
    public RestrictedCommand(String identifier,String description) {
        super(identifier,description);
        this.requiredPerm = null;

    }

    public boolean hasPerm(User sender, TextChannel c, Server s) {
        boolean output;
        if(requiredPerm!=null)
        output = Utils.hasPermission(sender, s, requiredPerm);
        else
            return sender.isBotOwner();
        if (!output)
            invalidPermResponse(c);
        return output;
    }

    public boolean invalidPermResponse(TextChannel c) {
        c.sendMessage("You have insufficient Permissions for this command you need " + requiredPerm);
        return false;
    }

    public boolean notOwnerResponse(TextChannel c) {
        c.sendMessage("Only the Bot owner can run this command");
        return false;
    }

    public boolean isOwner(User sender,TextChannel c) {
        if (sender.isBotOwner())
            return true;
        else
            return notOwnerResponse(c);

    }
}
