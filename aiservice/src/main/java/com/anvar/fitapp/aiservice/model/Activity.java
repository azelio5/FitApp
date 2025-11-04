package com.anvar.fitapp.aiservice.model;

import com.anvar.fitapp.aiservice.service.ActivityType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
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
