package client.views;

import client.interfaces.DecodeResponse;
import client.interfaces.ProfileRequestData;
import client.interfaces.Request;
import client.interfaces.ResponseDecoded;
import client.views.components.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.User;
import utils.CommonUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class SendMessageView {
    int userId;
    public PrintWriter writer;
    public BufferedReader reader;
    Scanner scanner = new Scanner(System.in);

    public SendMessageView(int userId, PrintWriter writer, BufferedReader reader) {
        this.userId = userId;
        this.writer = writer;
        this.reader = reader;
    }

    public void OptionsView() {
        Component.pageTitleView("Send a Message");

        CommonUtil.addTabs(10, true);
        System.out.println("1. Direct Message");
        CommonUtil.addTabs(10, false);
        System.out.println("2. Message a group");

        Component.chooseOptionInputView("Choose an option: ");

        int action;
        do {
            action = Component.getChooseOptionChoice();
            try {
                switch (action) {
                    case 1 -> {
                        CommonUtil.resetColor();
                        DirectMessageView();
                    }
                    case 2 -> {
                         GroupMessageView();
                    }
                    default -> {
                        action = -1;
//                        CommonUtil.addTabs(10, false);
//                        CommonUtil.useColor(ConsoleColor.BoldColor.RED_BOLD);
//                        System.out.print("Enter a valid choice (1, 2): ");
//                        CommonUtil.resetColor();
                        Component.showErrorMessage("Enter a valid choice (1, 2): ");

                    }
                }
            } catch (Exception e) {
                Component.showErrorMessage(e.getMessage());
            }
        } while (action == -1);

    }
    public void allActiveUsers() throws IOException {
        String  key= "get_users_list";
        Request request = new Request(new ProfileRequestData(userId),key);
        String requestAsString = new ObjectMapper().writeValueAsString(request);
        writer.println(requestAsString);
        ResponseDecoded response = new DecodeResponse().decodedResponse(reader.readLine());
        Component.pageTitleView("USERS LIST");
        if(response.isSuccess()){
            User[] users = new DecodeResponse().returnUsersListDecoded(response.getData());
            CommonUtil.addTabs(10, true);
            for (User user : users) {
                System.out.println(user.getUserID()+". "+user.getFname()+" "+user.getLname());
                CommonUtil.addTabs(10, false);
            }
        }else {
            CommonUtil.addTabs(10, true);
            System.out.println("Failed to read users list, sorry for the inconvenience");
        }
        System.out.println("");
        Component.chooseOptionInputView("Type any number to go to main page: ");
        int choice  = scanner.nextInt();
    }

    public void DirectMessageView() {
        Component.pageTitleView("Direct Message");

        CommonUtil.addTabs(10, true);
        System.out.println("1. List all Users");
        CommonUtil.addTabs(10, false);
        System.out.println("2. Search a User (names)");
        CommonUtil.addTabs(10, false);
        System.out.println("3. Enter a user ID");

        Component.chooseOptionInputView("Choose an option: ");


        int action;
        do {
            action = Component.getChooseOptionChoice();
            try {
                CommonUtil.resetColor();
                switch (action) {
                    case 1 -> {
                        allActiveUsers();
                    }
                    case 2 -> {
                        SearchUserView();
                    }
                    case 3 -> {
                        UserIdView();
                    }
                    default -> {
                        action = -1;
                        Component.showErrorMessage("Enter a valid choice (1, 2): ");
                    }
                }
            } catch (Exception e) {
                Component.showErrorMessage(e.getMessage());
            }
        } while (action == -1);
    }

    public void GroupMessageView() {
        Component.pageTitleView("Group Message");

        CommonUtil.addTabs(10, true);
        System.out.println("1. List all Groups");
        CommonUtil.addTabs(10, false);
        System.out.println("2. Search a Group (name)");
        CommonUtil.addTabs(10, false);
        System.out.println("3. Enter a group ID");

        Component.chooseOptionInputView("Choose an option: ");

        int action;
        do {
            action = Component.getChooseOptionChoice();
            try {
                CommonUtil.resetColor();
                switch (action) {
                    case 1 -> {
                        GetAllGroupsView();
                    }
                    case 2 -> {
                        SearchGroupView();
                    }
                    case 3 -> {
                        GroupIdView();
                    }
                    default -> {
                        action = -1;
                        Component.showErrorMessage("Enter a valid choice (1, 2): ");
                    }
                }
            } catch (Exception e) {
                Component.showErrorMessage(e.getMessage());
            }
        } while (action == -1);
    }


    public void SearchUserView() {
        Component.pageTitleView("Search a User");

        Component.chooseOptionInputView("Search: ");
    }

    public void UserIdView() throws IOException {
        Component.pageTitleView("Get User");

        Component.chooseOptionInputView("Enter User Id: ");
        int id = scanner.nextInt();
        FindUser(id);

    }
    public void FindUser(int id) throws IOException {
        String key = "get_profile";
        Request request = new Request(new ProfileRequestData(id), key);
        String requestAsString = new ObjectMapper().writeValueAsString(request);
        writer.println(requestAsString);
        ResponseDecoded response = new DecodeResponse().decodedResponse(reader.readLine());
        if (response.isSuccess()) {
            User profile = new DecodeResponse().returnUserDecoded(response.getData());
            Component.pageTitleView("Chat with " + profile.getUsername()+" "+profile.getFname());
            CommonUtil.addTabs(10, false);
            System.out.println("Type message:  ");
            CommonUtil.addTabs(10, false);
            int message = scanner.nextInt();
        } else {
            CommonUtil.addTabs(10, false);
            System.out.println("User not found");
        }
    }
    public void GetAllGroupsView() {
        Component.pageTitleView("Groups List");

        System.out.println("1. All groups list");
    }



    public void SearchGroupView() {
        Component.pageTitleView("Search a Group");

        Component.chooseOptionInputView("Search: ");
    }

    public void GroupIdView() {
        Component.pageTitleView("Get Group");

        Component.chooseOptionInputView("Enter Group Id: ");
    }

}
