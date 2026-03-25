package com.company.ems.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageResponseDTO<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
