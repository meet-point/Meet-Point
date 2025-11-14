package ru.meetpoint.eventservice.mapper.event;

import org.springframework.stereotype.Component;
import ru.meetpoint.eventservice.data.dto.request.event.EventRequest;
import ru.meetpoint.eventservice.data.entity.event.EventAdditional;

import java.util.UUID;

@Component
public class EventAdditionalDataMapper {

    public EventAdditional toNewEntity(EventRequest request) {

        return null;
    }

    public EventAdditional toAlreadyExistEvent(EventRequest request, UUID userId) {
        return EventAdditional.builder()

                .build();
    }
}
