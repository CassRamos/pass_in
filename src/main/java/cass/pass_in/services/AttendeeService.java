package cass.pass_in.services;

import cass.pass_in.domain.attendee.Attendee;
import cass.pass_in.domain.attendee.exceptions.AttendeeAlreadyExistsException;
import cass.pass_in.domain.attendee.exceptions.AttendeeNotFoundException;
import cass.pass_in.domain.checkin.CheckIn;
import cass.pass_in.dto.attendee.AttendeeBadgeDTO;
import cass.pass_in.dto.attendee.AttendeeBadgeResponseDTO;
import cass.pass_in.dto.attendee.AttendeeDetails;
import cass.pass_in.dto.attendee.AttendeesListResponseDTO;
import cass.pass_in.repositories.AttendeeRepository;
import cass.pass_in.repositories.CheckInRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

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

    public void verifyAttendeeSubscription(String email, String eventId) {
        Optional<Attendee> isAttendeeRegistered = this.attendeeRepository.findByEventIdAndEmail(eventId, email);
        if (isAttendeeRegistered.isPresent())
            throw new AttendeeAlreadyExistsException("Attendee is already registered!");
    }

    public void registerAttendee(Attendee newAttendee) {
        this.attendeeRepository.save(newAttendee);
    }

    public AttendeeBadgeResponseDTO getAttendeeBadge(String attendeeId, UriComponentsBuilder uriComponentsBuilder) {
        Attendee attendee = this.attendeeRepository
                .findById(attendeeId)
                .orElseThrow(() -> new AttendeeNotFoundException("Attendee not found with ID: " + attendeeId));

        var uri = uriComponentsBuilder.path("/attendees/{attendeeId}/check-in").buildAndExpand(attendeeId).toUri().toString();

        AttendeeBadgeDTO attendeeBadgeDTO = new AttendeeBadgeDTO(
                attendee.getName(),
                attendee.getEmail(),
                uri,
                attendee.getEvent().getId());

        return new AttendeeBadgeResponseDTO(attendeeBadgeDTO);
    }

}
