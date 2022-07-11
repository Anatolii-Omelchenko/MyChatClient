package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GetThread implements Runnable {
    private final Gson gson;
    private int n;
    private String login;

    public GetThread(String login) {
        gson = new GsonBuilder().setDateFormat(Message.DATE_FORMAT).create();
        this.login = login;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                URL url = new URL(Utils.getURL() + "/get?from=" + n + "&login=" + login);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();

                InputStream is = http.getInputStream();
                try {
                    byte[] buff = responseBodyToArray(is);
                    String strBuff = new String(buff, StandardCharsets.UTF_8);

                    JsonMessages list = gson.fromJson(strBuff, JsonMessages.class);
                    if (list != null) {
                        for (Message m : list.getList()) {
                            if (m.getTo().equals("All")) {
                                System.out.println("\033[0;34m" + m + "\u001B[0m");
                            }
                            else{
                                System.out.println("\u001B[32m" + m + "\u001B[0m");
                            }
                            n++;
                        }
                    }
                } finally {
                    is.close();
                }
                Thread.sleep(500);
            }
        } catch (Exception ex) {

        }
    }

    private byte[] responseBodyToArray(InputStream is) throws IOException {
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
