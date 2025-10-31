package com.anvar.fitapp.activityservice.util;

import com.anvar.fitapp.activityservice.DTO.ActivityRequestDTO;
import com.anvar.fitapp.activityservice.DTO.ActivityResponseDTO;
import com.anvar.fitapp.activityservice.model.Activity;

public class MapToResponse {
    public static ActivityResponseDTO mapToResponse(Activity activity) {
        return ActivityResponseDTO.builder()
                .id(activity.getId())
                .userId(activity.getUserId())
                .activityType(activity.getActivityType())
                .calories(activity.getCalories())
                .duration(activity.getDuration())
                .startTime(activity.getStartTime())
                .additionalMetrics(activity.getAdditionalMetrics())
                .createdAt(activity.getCreatedAt())
                .updatedAt(activity.getUpdatedAt())
                .build();
    }
}
