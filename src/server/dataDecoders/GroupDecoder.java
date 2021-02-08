package server.dataDecoders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.models.Group;

public class GroupDecoder {
    String data;
    public GroupDecoder(String data) {
        this.data = data;
    }
    public Group CreateGroupDecode() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode dataDecrypt = objectMapper.readTree(data);
        return new Group(dataDecrypt.get("name").asText(),dataDecrypt.get("description").asText(),dataDecrypt.get("group_creator").asInt());
    }
}
