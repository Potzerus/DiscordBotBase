package potz.utils;

import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
import potz.utils.commandMaps.CommandMap;
import potz.utils.commandMaps.DefaultCommandMap;
import potz.Utils;
import potz.utils.database.ModuleStorage;
import potz.utils.database.ServerStorage;
import potz.utils.database.State;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;

import java.util.ArrayList;
import java.util.List;

import static potz.Utils.parseArgsArray;

public abstract class Module {

    protected DiscordApi api;
    protected Server server;
    protected long serverId;
    protected boolean whitelist = false;
    protected List<Long> whiteListChannels = new ArrayList<>();
    protected DefaultCommandMap commandMap;
    protected State state;
    protected String prefix;
    protected ModuleStorage moduleStorage=genStorage();

    public Module(String prefix, DiscordApi api, Server server, State state) {
        this.server = server;
        this.serverId = server.getId();
        this.api = api;
        this.state = state;
        this.commandMap = new DefaultCommandMap(this,state.getServer(server.getId()));
        this.prefix = prefix;
        api.addMessageCreateListener(event -> {
            if (event.getServer().isPresent() && event.getServer().get().getId() == serverId && !event.getMessageAuthor().isWebhook() && !event.getMessageAuthor().asUser().get().isYourself()) {
                String[] message = parseArgsArray(event.getMessageContent());
                if (message.length >= 2 && message[0].equals(prefix)) {
                    if (Utils.hasPermission(event.getMessageAuthor().asUser().get(), event.getServer().get(), PermissionType.MANAGE_MESSAGES) && message[1].equals("whitelist")) {
                        switch (event.getMessageContent().toLowerCase().substring((prefix + " whitelist ").length())) {
                            case "toggle":
                                whitelist = !whitelist;
                                event.getChannel().sendMessage("Whitelist is now " + (whitelist ? "enabled" : "disabled"));
                                break;
                            case "on":
                                whitelist = true;
                                break;
                            case "off":
                                whitelist = false;
                                break;
                            case "add":
                                whiteListChannels.add(event.getChannel().getId());
                                event.getChannel().sendMessage("Added this Channel to the whitelist!");
                                break;
                            case "remove":
                                whiteListChannels.remove(event.getChannel().getId());
                                event.getChannel().sendMessage("Removed this Channel from the whitelist!");
                                break;

                        }
                    } else {

                        long channelId = event.getChannel().getId();
                        if (whiteListChannels.contains(channelId) || !whitelist) {
                            commandMap.getCommand(message[1]).execute(event.getMessageAuthor().asUser().get(), event.getChannel(), event.getServer().get(), message);

                        }

                    }
                }
            }
        });


    }

    public void runCommand(MessageCreateEvent event) {
        String[] args = parseArgsArray(event.getMessageContent());
        System.out.println("Querying command " + args[1]);
        commandMap.getCommand(args[1]).execute(event.getMessageAuthor().asUser().get(), event.getChannel(), event.getServer().get(), args);


    }

    public abstract String getIdentifier();

    public void reloadState(){
        state=state.loadFile();
        commandMap.setServerStorage(state);
    }

    @Override
    public String toString() {
        String output =
                "Server: " + server.getName() + " " +
                        "Prefix: " + prefix + " " +
                        commandMap.toString();
        return output;
    }

    public abstract ModuleStorage genStorage();
}

