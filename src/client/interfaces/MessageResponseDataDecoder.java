package client.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.Messages;
import server.models.User;

public class MessageResponseDataDecoder {
    Object data;
    String success;

    public Object getData() {
        return data;
    }

    public String getSuccess() {
        return success;
    }
    public Messages[] returnMessagesNotificationsList(String data)throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Messages[] messages = objectMapper.readValue(data, Messages[].class);
        return messages;
    }
    public Messages[] returnDecodedReplies(String data)throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();

        Messages[] messages = objectMapper.readValue(data, Messages[].class);
        return messages;
    }
    public  Messages returnDecodedMessage(String data) throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();

        Messages messages = objectMapper.readValue(data, Messages.class);
        return messages;
    }
}
