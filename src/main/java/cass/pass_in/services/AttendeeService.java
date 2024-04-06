package cass.pass_in.services;

import cass.pass_in.domain.attendee.Attendee;
import cass.pass_in.domain.checkin.CheckIn;
import cass.pass_in.dto.attendee.AttendeeDetails;
import cass.pass_in.dto.attendee.AttendeesListResponseDTO;
import cass.pass_in.repositories.AttendeeRepository;
import cass.pass_in.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendeeService {

    private final AttendeeRepository attendeeRepository;
    private final CheckInRepository checkInRepository;

    public List<Attendee> getAllAttendeesFromEvent(String eventId) {
        return this.attendeeRepository.findByEventId(eventId);
    }

    public AttendeesListResponseDTO getEventsAttendee(String eventId) {
        List<Attendee> attendeeList = this.getAllAttendeesFromEvent(eventId);

        List<AttendeeDetails> attendeeDetailsList = attendeeList
                .stream()
                .map(attendee -> {
                    Optional<CheckIn> checkIn = this.checkInRepository.findByAttendeeId(attendee.getId());
                    //same to >>>> LocalDateTime checkedInAt = checkIn.isPresent() ? checkIn.get().getCreatedAt() : null;
                    LocalDateTime checkedInAt = checkIn.<LocalDateTime>map(CheckIn::getCreatedAt).orElse(null);

                    return new AttendeeDetails(
                            attendee.getId(),
                            attendee.getName(),
                            attendee.getEmail(),
                            attendee.getCreatedAt(),
                            checkedInAt);
                }).toList();
        return new AttendeesListResponseDTO(attendeeDetailsList);
    }

}
