package nextteam.models.response;

import java.util.Date;
import nextteam.models.*;

public class EventRegistrationResponse {

    private String firstName;
    private String lastName;
    private String studentCode;
    private String email;
    private String phoneNumber;
    private Date createdAt;

    public EventRegistrationResponse(int id, int eventId, int registeredBy, boolean isJoined, String reasonForAbsence, Date createdAt, Date updatedAt) {
//        super(id, eventId, registeredBy, isJoined, reasonForAbsence, createdAt, updatedAt);
    }

    public EventRegistrationResponse(String firstName, String lastName, String studentCode, String email, String phoneNumber, Date createdAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentCode = studentCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

}
