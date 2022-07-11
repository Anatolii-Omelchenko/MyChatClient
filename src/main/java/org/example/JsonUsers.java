package org.example;

import java.util.ArrayList;
import java.util.List;

public class JsonUsers {
    private final List<User> list = new ArrayList<>();

    public JsonUsers (List<User> sourceList) {
        for(User user : sourceList){
            list.add(user);
        }
    }

    public List<User> getList() {
        return list;
    }
}
