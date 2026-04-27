package com.projects.eventticket.eventticket.services;

import com.projects.eventticket.eventticket.config.RedisCacheConfig;
import com.projects.eventticket.eventticket.domain.dtos.GetPublishedEventDetailsResponseDto;
import com.projects.eventticket.eventticket.domain.entity.Event;
import com.projects.eventticket.eventticket.domain.enums.EventStatusEnum;
import com.projects.eventticket.eventticket.mappers.EventsMapper;
import com.projects.eventticket.eventticket.repository.EventRepository;
import com.projects.eventticket.eventticket.repository.UserRepository;
import com.projects.eventticket.eventticket.services.impl.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({ RedisCacheConfig.class, EventServiceImpl.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class EventServiceCachingIntegrationTest {

    @MockBean
    private EventRepository mockEventRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private EventsMapper eventsMapper;

    @Autowired
    private EventService eventService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindPublishedEventById_thenPublishedEventReturnedFromCache() {

        UUID id = UUID.randomUUID();

        Event anEvent = new Event();
        anEvent.setId(id);
        anEvent.setStatus(EventStatusEnum.PUBLISHED);

        var eventDto = new GetPublishedEventDetailsResponseDto();
        eventDto.setId(anEvent.getId());

        Optional<Event> oEvent = Optional.of(anEvent);

        given(mockEventRepository.
                findByIdAndStatus(id, EventStatusEnum.PUBLISHED))
                .willReturn(oEvent);

        given(eventsMapper.
                toGetPublishedEventDetailsResponseDto(anEvent))
                .willReturn(eventDto);

        GetPublishedEventDetailsResponseDto eventCacheMiss = eventService.getPublishedEvent(id);
        GetPublishedEventDetailsResponseDto eventCacheHit = eventService.getPublishedEvent(id);

        assertThat(eventCacheMiss).isEqualTo(eventDto);
        assertThat(eventCacheHit).isEqualTo(eventDto);

        verify(mockEventRepository, times(1)).findByIdAndStatus(id,EventStatusEnum.PUBLISHED);
        assertThat(eventFromCache(id).orElse(null)).isEqualTo(eventDto);
    }

    private Optional<GetPublishedEventDetailsResponseDto> eventFromCache(UUID id) {
        var cache = cacheManager.getCache("eventDetails");
        if (cache == null) return Optional.empty();

        return Optional.ofNullable(cache.get(id, GetPublishedEventDetailsResponseDto.class));
    }
}
