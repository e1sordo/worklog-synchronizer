package es.e1sordo.worklog_sync.dto.notion;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import static java.util.Optional.ofNullable;

@Data
public class ObjectResponse {

    private String id;

    private int jiraTaskId;

    private double hoursSpent;

    private String date;

    private String startTime;

    private String comment;

    @JsonCreator
    public ObjectResponse(JsonNode body) {
        this.id = body.get("id").asText();

        var properties = body.get("properties");
        this.jiraTaskId = properties.get("Jira Task #").get("number").asInt(-1);
        this.hoursSpent = properties.get("⏰ (hrs.)").get("number").asDouble(-1.0);
        this.date = properties.get("Date").get("date").get("start").asText();
        this.startTime = ofNullable(properties.get("Start at").findValues("rich_text"))
                .map(list -> list.get(0).get(0))
                .map(node -> node.get("plain_text"))
                .map(JsonNode::asText)
                .orElse(null);
        this.comment = ofNullable(properties.get("Name").findValues("title"))
                .map(list -> list.get(0).get(0))
                .map(node -> node.get("plain_text"))
                .map(JsonNode::asText)
                .orElse(null);
    }
}
