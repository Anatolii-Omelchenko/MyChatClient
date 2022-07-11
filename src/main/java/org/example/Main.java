package org.example;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Input your login: ");
            String login = scanner.nextLine();
            Utils.updateUserStatus(login);

            Thread th = new Thread(new GetThread(login));
            th.setDaemon(true);
            th.start();
            System.out.println("Enter your message: ");
            while (true) {
                String input = scanner.nextLine();
                String to = "All";
                String text = input;
                if (input.isEmpty()) {
                    Utils.logOut(login);
                    break;
                }
                if (input.equals("#check")) {
                    Utils.getOnline();
                    continue;
                }

                if (input.startsWith("@")) {
                    if (!input.contains(" ")) {
                        to = input.substring(input.indexOf("@") + 1);
                        text = "";
                    } else {
                        to = input.substring(input.indexOf("@") + 1, input.indexOf(" "));
                        text = input.substring(input.indexOf(" ") + 1);
                    }
                }

                Message m = new Message(login, to, text);
                int res = m.send(Utils.getURL() + "/add");
                Utils.updateUserStatus(login);

                if (res != 200) { // 200 OK
                    System.out.println("HTTP error ocurred: " + res);
                    return;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            scanner.close();
        }

    }
}
