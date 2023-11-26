package com.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONObject;

public class ChatServer extends WebSocketServer {

    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    Operations op = new Operations();

    public ChatServer (int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onStart() {
        // Quan el servidor s'inicia
        String host = getAddress().getAddress().getHostAddress();
        int port = getAddress().getPort();
        System.out.println("WebSockets server running at: ws://" + host + ":" + port);
        System.out.println("Type 'exit' to stop and exit server.");
        setConnectionLostTimeout(0);
        setConnectionLostTimeout(100);
        op.printBoard();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        // Quan un client es connecta
        String clientId = getConnectionId(conn);

        // // Li enviem el seu identificador
        // JSONObject objId = new JSONObject("{}");
        // objId.put("type", "id");
        // objId.put("from", "server");
        // objId.put("value", clientId);
        // conn.send(objId.toString()); 

        // // Enviem al client la llista amb tots els clients connectats
        // sendList(conn);

        // // Enviem la direcció URI del nou client a tothom 
        // JSONObject objCln = new JSONObject("{}");
        // objCln.put("type", "connected");
        // objCln.put("from", "server");
        // objCln.put("id", clientId);
        // broadcast(objCln.toString());

        // // Mostrem per pantalla (servidor) la nova connexió
        // String host = conn.getRemoteSocketAddress().getAddress().getHostAddress();
        // System.out.println("New client (" + clientId + "): " + host);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        // Quan un client es desconnecta
        String clientId = getConnectionId(conn);

        // Informem a tothom que el client s'ha desconnectat
        JSONObject objCln = new JSONObject("{}");
        objCln.put("type", "disconnected");
        objCln.put("from", "server");
        objCln.put("id", clientId);
        broadcast(objCln.toString());

        // Mostrem per pantalla (servidor) la desconnexió
        System.out.println("Client disconnected '" + clientId + "'");
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        // Quan arriba un missatge
        String clientId = getConnectionId(conn);
        try {
            JSONObject objRequest = new JSONObject(message);
            String type = objRequest.getString("type");

            if (type.equalsIgnoreCase("hello")) {

                String userName = objRequest.getString("name");
                op.players.add(userName);

            } else if (type.equalsIgnoreCase("flip")) {
                
                
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        // Quan hi ha un error
        ex.printStackTrace();
    }

    public void runServerBucle () {
        boolean running = true;
        try {
            System.out.println("Starting server");
            start();
            while (running) {
                String line;
                line = in.readLine();
                if (line.equals("exit")) {
                    running = false;
                }
            } 
            System.out.println("Stopping server");
            stop(1000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }  
    }

    public void sendList (WebSocket conn) {
        JSONObject objResponse = new JSONObject("{}");
        objResponse.put("type", "list");
        objResponse.put("from", "server");
        objResponse.put("list", getClients());
        conn.send(objResponse.toString()); 
    }

    public String getConnectionId (WebSocket connection) {
        String name = connection.toString();
        return name.replaceAll("org.java_websocket.WebSocketImpl@", "").substring(0, 3);
    }

    public String[] getClients () {
        int length = getConnections().size();
        String[] clients = new String[length];
        int cnt = 0;

        for (WebSocket ws : getConnections()) {
            clients[cnt] = getConnectionId(ws);               
            cnt++;
        }
        return clients;
    }

    public WebSocket getClientById (String clientId) {
        for (WebSocket ws : getConnections()) {
            String wsId = getConnectionId(ws);
            if (clientId.compareTo(wsId) == 0) {
                return ws;
            }               
        }
        
        return null;
    }
}