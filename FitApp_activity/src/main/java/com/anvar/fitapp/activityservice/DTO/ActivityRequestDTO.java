package com.anvar.fitapp.activityservice.DTO;

import com.anvar.fitapp.activityservice.model.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ActivityRequestDTO {

    private String id;
    private String userId;

    private ActivityType activityType;
    private Integer duration;
    private Integer calories;
    private LocalDateTime startTime;
    private Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;


    private LocalDateTime updatedAt;


}
