package potz;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Utils {

    public static String trimMention(String untrimmed) {
        if (untrimmed.startsWith("<@!") && untrimmed.endsWith(">")) {
            return untrimmed.substring(3, untrimmed.length() - 1);
        } else if (untrimmed.startsWith("<@") && untrimmed.endsWith(">")) {
            return untrimmed.substring(2, untrimmed.length() - 1);
         } else if (untrimmed.startsWith("<#") && untrimmed.endsWith(">")) {
            return untrimmed.substring(2, untrimmed.length() - 1);
         } else if (untrimmed.startsWith("<&") && untrimmed.endsWith(">")) {
            return untrimmed.substring(2, untrimmed.length() - 1);
        } else {
            return untrimmed;
        }
    }

    public static String[] parseArgsArray(String message) {
        Scanner ctrl = new Scanner(message);
        int indx;
        indx = 0;
        while (ctrl.hasNext()) {
            ctrl.next();
            indx++;
        }
        String[] args = new String[indx];
        ctrl = new Scanner(message);
        for (indx = 0; indx < args.length; indx++) {
            args[indx] = ctrl.next();
        }
        return args;
    }

    public static String getToken(){
        String token = "";
        try {
            token = new String(Files.readAllBytes(Paths.get("Token")));
        } catch (IOException e) {
            System.out.println("You need to create a File called \"Token\" and put your Bot Token into it to start the bot!");
        }
        return token;

    }

    public static boolean hasPermission(User sender, Server s, PermissionType... types){
        if(sender==null)
            return false;

        List<Role> roles= sender.getRoles(s);
        for (Role role:roles) {
            for (PermissionType type:types) {
                if(role.getAllowedPermissions().contains(type))
                    return true;
            }
        }
        return false;
    }

    public static boolean hasRole(User sender, Server s, long RoleId){
        for (Role current:sender.getRoles(s)) {
            if(current.getId()==RoleId)
                return true;
        }
        return false;
    }

    public static User find(DiscordApi api, String id){
        return api.getUserById(Utils.trimMention(id)).join();
    }




}
