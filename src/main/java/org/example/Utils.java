package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

public class Utils {
    private static final String URL = "http://127.0.0.1";
    private static final int PORT = 8080;
    private static final Gson gson = new GsonBuilder().create();


    public static String getURL() {
        return URL + ":" + PORT;
    }

    public static int updateUserStatus(String login) throws IOException {
        URL obj = new URL(getURL() + "/update");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(login.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode();
        }
    }

    public static int logOut(String login) throws IOException {
        URL obj = new URL(getURL() + "/logout");
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(login.getBytes(StandardCharsets.UTF_8));
            return conn.getResponseCode();
        }
    }

    public static void getOnline() throws IOException {

        java.net.URL url = new URL(getURL() + "/check");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        InputStream is = http.getInputStream();
        try {
            byte[] buff = responseBodyToArray(is);
            String strBuff = new String(buff, StandardCharsets.UTF_8);

            JsonUsers userList = gson.fromJson(strBuff, JsonUsers.class);
            userList.getList().sort(Comparator.comparing(User::getStatus).reversed());

            if (userList != null) {
                for (User user : userList.getList()) {
                    if (user.status.equals("online")) {
                        System.out.println("\033[42m" + user + "\u001B[0m");
                    }
                    else if(user.status.equals("waiting")){
                        System.out.println("\033[43m" + user + "\u001B[0m");
                    }
                    else{
                        System.out.println("\033[41m" + user + "\u001B[0m");
                    }

                }
            }
        } finally {
            is.close();
        }
    }


    private static byte[] responseBodyToArray(InputStream is) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buff = new byte[1024];
        int r;

        do {
            r = is.read(buff);
            if (r > 0) {
                bos.write(buff, 0, r);
            }
        } while (r != -1);

        return bos.toByteArray();
    }
}
