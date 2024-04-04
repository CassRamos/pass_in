CREATE UNIQUE INDEX events_slug_key ON events (slug);
CREATE UNIQUE INDEX attendees_event_id_email_key ON attendees (event_id, email);
CREATE UNIQUE INDEX check_in_attendee_id_key ON check_ins (attendee_id);