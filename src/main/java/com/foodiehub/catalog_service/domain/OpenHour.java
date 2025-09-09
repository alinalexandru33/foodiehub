package com.foodiehub.catalog_service.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenHour {
    private Integer dayOfWeek;
    private String open;
    private String close;
}
