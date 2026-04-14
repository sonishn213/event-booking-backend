package com.projects.eventticket.eventticket.domain.dtos;

import com.projects.eventticket.eventticket.domain.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageWrapperDto<T> {

    private List<T> content;
    private int page;
    private int size;
    private long total;

    public Page<T> toPage(Pageable pageable) {
        return new PageImpl<>(
                this.content,
                pageable,
                this.total
        );
    }
}
