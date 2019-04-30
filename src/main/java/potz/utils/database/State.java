package potz.utils.database;


import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class State implements Serializable {

    private Map<Long, ServerStorage> servers;

    public State() {
        servers = new HashMap<>();
        //loadFile();
    }

    public String toString() {

        StringBuilder outputList = new StringBuilder();

        for (ServerStorage serverStorage : servers.values()) {
            outputList.append(serverStorage.getServerName() + ", ");
        }

        return "Servers={" + outputList.toString().substring(0, outputList.toString().length() - 2) + "}";
    }

    public ServerStorage addServer(long serverId) {
        ServerStorage ss = new ServerStorage(serverId,this);
        servers.put(serverId, ss);
        return ss;
    }

    /*
        public void addServer(JSONObject serverObject) {
            servers.put(serverObject.getLong("serverId"), new ServerStorage(serverObject));
        }
    */
    public Char addPlayer(long serverId, long playerId) {
        if (!servers.containsKey(serverId)) {
            addServer(serverId);
        }

        return servers.get(serverId).addPlayer(playerId);

    }

    public Char getPlayerDirectly(long serverId, long userId) {
        ServerStorage intermediate;
        try {
            intermediate = this.getServer(serverId);
            return intermediate.getPlayer(userId);
        } catch (NullPointerException e) {
            //Main.reply("Player not found in " + null);
            return null;
        }
    }


    public void save() {

        try {
            OutputStream outStream = new FileOutputStream("State.ser");
            ObjectOutputStream fileObjectOut = new ObjectOutputStream(outStream);
            fileObjectOut.writeObject(this);
            fileObjectOut.close();
            outStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
            JSONArray servers = new JSONArray();
            Iterator i = this.servers.values().iterator();
            int count = 0;
            while (i.hasNext()) {
                ServerStorage commandMap = (ServerStorage) i.next();
                servers.put(count, commandMap.toJson());
                System.out.println(servers);
                count++;
            }

            JSONObject state = new JSONObject();
            state.put("servers", servers);
            String output = state.toString();
            System.out.println(output);
            Writer writer;
            try {
                writer = new BufferedWriter(new FileWriter(new File("State.json")));
                writer.write(output);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
    */
    }

    public ServerStorage getServer(long serverId) {

        return servers.getOrDefault(serverId, addServer(serverId));

    }

    public String checkPlayer(long serverId, long userId) {

        Char player = getPlayerDirectly(serverId, userId);
        return player.toString();


    }

    private void saveAndLoad() {
        save();
        loadFile();
    }


    public State loadFile() {
        clearMap();
        try {
            InputStream inStream = new FileInputStream("State.ser");
            ObjectInputStream fileObjectIn = new ObjectInputStream(inStream);
            State s = (State) fileObjectIn.readObject();
            System.out.println(s);
            fileObjectIn.close();
            inStream.close();
            return s;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    /*            try {
                BufferedReader br = new BufferedReader((new FileReader("State.json")));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                String input = sb.toString();
                System.out.println(input);
                JSONObject state = new JSONObject(input);
                System.out.println(state);
                JSONArray servers = state.getJSONArray("servers");
                System.out.println(servers);
                for (int i = 0; i < servers.length(); i++) {
                    JSONObject server = (JSONObject) servers.get(i);
                    long serverId = server.getLong("serverId");
                    addServer(serverId);
                    JSONArray players = server.getJSONArray("users");
                    for (int j = 0; j < players.length(); j++) {
                        System.out.println(j + " " + players.length());
                        JSONObject player = (JSONObject) players.get(j);
                        long userId = player.getLong("userId");
                        System.out.println(addPlayer(serverId, userId));
                        JSONObject stats = ((JSONObject) player.get("stats"));
                        Iterator staterator = stats.toMap().keySet().iterator();
                        while (staterator.hasNext()) {
                            String statName = (String) staterator.next();
                            getPlayerDirectly(serverId, userId).
                                    setStat(statName, stats.get(statName).toString());
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

*/
    }

    private void clearMap() {
        servers.clear();
    }
}

