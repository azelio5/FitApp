package com.anvar.fitapp.activityservice.DTO;

import com.anvar.fitapp.activityservice.model.ActivityType;
import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class ActivityResponseDTO {

    private String id;
    private String userId;

    private ActivityType activityType;
    private Integer duration;
    private Integer calories;


    private Map<String, Object> additionalMetrics;

    private LocalDateTime startTime;


    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;
}
