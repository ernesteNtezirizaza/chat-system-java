package client.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.Group;

public class GroupResponseDataDecoder {

    public Group[] returnGroupsListDecoded(String data)throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        Group[] groups = objectMapper.readValue(data, Group[].class);
        return groups;
    }
    public Group returnGroupDecoded(String data)throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode value = objectMapper.readTree(data);
        return new Group(value.get("name").asText(),value.get("description").asText(),value.get("group_creator").asInt());
    }
}