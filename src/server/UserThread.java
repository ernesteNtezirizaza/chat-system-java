package server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.requestHandlers.MessageRequestHandler;
import server.requestHandlers.UserRequestHandler;

import java.io.*;
import java.net.Socket;

/**
 * This is a thread that allows many clients to the server as it handles one currently connected and when new one comes any
 * @Author: Didier Munezero
 */
public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    public UserThread(Socket socket, ChatServer server) {
        this.socket = socket;
        this.server = server;
    }
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);


            String clientMessage;
            do {
                clientMessage = reader.readLine();
                System.out.println(clientMessage);
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(clientMessage);
                String request_type = jsonNode.get("request_type").asText();
                String data = jsonNode.get("data").toString();

                if(request_type.equals("login")){
                    new UserRequestHandler().HandleLogin(data,writer,objectMapper,server);
                }
                else if(request_type.equals("register")){
                    new UserRequestHandler().HandleRegister(data,writer,objectMapper,server);
                }
                else if(request_type.equals("get_profile")){
                    new UserRequestHandler().HandleGetProfile(data,writer,objectMapper,server);
                }
                else if(request_type.equals("get_users_list")){
                    new UserRequestHandler().HandleUsersList(data,writer,objectMapper,server);
                }
                else if(request_type.equals("get_messages_between_two")){
                    new MessageRequestHandler().HandleMessageBetweenTwo(data,writer,objectMapper,server);
                }
                else if(request_type.equals("get_my_notifications")){
                    new MessageRequestHandler().HandleNotification(data,writer,objectMapper,server);
                }
                else if(request_type.equals("view_my_groups")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("delete_group")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("add_member_to_group")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("remove_member_from_group")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("send_message")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("view_reports")){
                    System.out.println("Not yet done request");
                }
                else if(request_type.equals("delete_user")){
                    System.out.println("Not yet done request");
                }
                else{
                    writer.println("Request type not known");
                }
            } while (!clientMessage.equals("bye"));
            socket.close();
        } catch (Exception ex) {
            System.out.println("Error in UserThread: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    /**
     * Sends a list of online users to the newly connected user.
     */
    void printUsers() {
        System.out.println(server.hasUsers());
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }
    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}