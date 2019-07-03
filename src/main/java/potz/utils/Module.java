package potz.utils;

import org.javacord.api.entity.permission.PermissionType;
import potz.utils.commandMaps.DefaultCommandMap;
import potz.Utils;
import potz.utils.database.ModuleStorage;
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
    protected ModuleStorage moduleStorage;
    protected String identifier;

    public Module(String prefix, DiscordApi api, Server server, State state) {
        this.server = server;
        this.serverId = server.getId();
        this.api = api;
        this.state = state;
        this.commandMap = new DefaultCommandMap(this, state.getServer(server.getId()));
        this.prefix = prefix;
        this.moduleStorage = genStorage();
        api.addMessageCreateListener(event -> {
            if (event.getServer().isPresent() && event.getServer().get().getId() == serverId && !event.getMessageAuthor().isWebhook() && !event.getMessageAuthor().asUser().get().isYourself()) {
                String[] message = event.getMessageContent().split(" ");
                if (message.length >= 2 && message[0].equals(prefix)) {
                    if (!isWhitelistCommand(prefix, message, event)) {
                        long channelId = event.getChannel().getId();
                        if (whiteListChannels.contains(channelId) || !whitelist) {
                            commandMap.getCommand(message[1]).execute(event, message);

                        }

                    }
                }
            }
        });


    }

    public Module(String prefix, String identifier, DiscordApi api, Server server, State state) {
        this(prefix, api, server, state);
        this.identifier = identifier;
    }


    private boolean isWhitelistCommand(String prefix, String[] message, MessageCreateEvent event) {
        if (!(Utils.hasPermission(event.getMessageAuthor().asUser().get(), event.getServer().get(), PermissionType.MANAGE_MESSAGES) && message[1].equals("whitelist")))
            return false;
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
        return true;
    }

    public void runCommand(MessageCreateEvent event) {
        String[] args = parseArgsArray(event.getMessageContent());
        System.out.println("Querying command " + args[1]);
        commandMap.getCommand(args[1]).execute(event, args);


    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPrefix() {
        return prefix;
    }

    public void reloadState() {
        state = state.loadFile();
        commandMap.setServerStorage(state);
    }

    @Override
    public String toString() {
        return "Server: " + server.getName() + " " +
                "Prefix: " + prefix + " " +
                commandMap.toString();
    }

    public ModuleStorage genStorage() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Module))
            return false;
        Module m = (Module) o;
        return m.identifier.equals(identifier)
                && m.serverId == serverId
                && m.prefix.equals(prefix);
    }
}

