package es.e1sordo.worklog_sync.dto.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Optional.ofNullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueWorklogResponse {

    public static final String ZONED_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.nnnZ";
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern(ZONED_PATTERN);
    private static final DateTimeFormatter OUTPUT_FORMATTER = DateTimeFormatter.ofPattern("EEEE, d MMM yyyy, h:mm a"); //, new Locale("ru"));

    private String self;
    private UserResponse author;
    private UserResponse updateAuthor;
    private String comment;
    private String created;
    private String updated;
    private String started;
    private String timeSpent;
    private String timeSpentSeconds;
    private String id;
    private String issueId;

    @Override
    public String toString() {
        return "ID №%s, time spent: %s, comment: %s, startDate: %s, creationDate: %s".formatted(
                id,
                timeSpent,
                ofNullable(comment).map(c -> "\"" + c + "\"").orElse("[BLANK]"),
                LocalDateTime.parse(started, INPUT_FORMATTER).format(OUTPUT_FORMATTER),
                LocalDateTime.parse(created, INPUT_FORMATTER).format(OUTPUT_FORMATTER)
        );
    }

    public boolean equals(String logComment,
                          LocalDate logStartDay, String logStartTime,
                          int minutesSpent) {
        var equalsComments = Objects.equals(comment, logComment);

        var startDay = LocalDate.parse(started.split("T")[0]); // returns yyyy-MM-dd
        var equalsStartDay = startDay.isEqual(logStartDay);

        var startTime = started.split("T")[1].substring(0, 5); // returns hh:mm
        var equalsStartTime = startTime.equals(logStartTime);

        var equalsSpent = Integer.parseInt(timeSpentSeconds) == minutesSpent * 60;

        return equalsComments && equalsStartDay && equalsStartTime && equalsSpent;
    }
}
