package nextteam.models.response;

import java.sql.Timestamp;
import nextteam.Global;
import nextteam.models.*;

public class EventRegistrationAttendanceResponse {

    int id;
    User registeredBy;
    boolean isJoined;
    String reasonForAbsence;
    Timestamp createdAt;
    Timestamp updatedAt;

    public EventRegistrationAttendanceResponse(int id, int registeredBy, boolean isJoined, String reasonForAbsence, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.registeredBy = Global.user.getUserById(registeredBy);
        this.isJoined = isJoined;
        this.reasonForAbsence = reasonForAbsence;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return """
               {
                "id": "%d",
                "registeredBy": "%s",
                "registeredById": "%s",
                "isJoined": "%s",
                "reasonForAbsence": "%s",
                "createdAt": "%s",
                "updatedAt": "%s"
               }""".formatted(
                id,
                registeredBy.getFirstname() + " " + registeredBy.getLastname(),
                registeredBy.getUsername(),
                isJoined,
                reasonForAbsence,
                createdAt,
                updatedAt
        );
    }

}
