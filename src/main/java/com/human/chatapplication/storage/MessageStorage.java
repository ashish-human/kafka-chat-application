package com.human.chatapplication.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MessageStorage {

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageStorage.class);

    private List<String> arrayList = new ArrayList<>();

    public void add (String message){arrayList.add(message);}

    public String toString(){
        LOGGER.info("Calling MessageStorage toString");
        StringBuffer info = new StringBuffer();
        arrayList.forEach(msg ->info.append(msg));
        LOGGER.info("info " + info.toString());
        return info.toString();
    }

    public void clear(){arrayList.clear();}


}
