package com.anvar.fitapp.activityservice.service;

import com.anvar.fitapp.activityservice.DTO.ActivityRequestDTO;
import com.anvar.fitapp.activityservice.DTO.ActivityResponseDTO;
import com.anvar.fitapp.activityservice.model.Activity;
import com.anvar.fitapp.activityservice.repository.ActivityRepository;
import com.anvar.fitapp.activityservice.util.MapToResponse;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;

    public ActivityService(ActivityRepository activityRepository, UserValidationService userValidationService) {
        this.activityRepository = activityRepository;
        this.userValidationService = userValidationService;
    }

    public ActivityResponseDTO trackActivity(ActivityRequestDTO requestDTO) {

        boolean isValidUser = userValidationService.validateUser(requestDTO.getUserId());

        if (!isValidUser) {
            throw new NotFoundException("User not found");
        }

        Activity activity = Activity.builder()
                .userId(requestDTO.getUserId())
                .activityType(requestDTO.getActivityType())
                .duration(requestDTO.getDuration())
                .calories(requestDTO.getCalories())
                .startTime(requestDTO.getStartTime())
                .additionalMetrics(requestDTO.getAdditionalMetrics())
                .build();
        activityRepository.save(activity);

        return MapToResponse.mapToResponse(activity);


    }

    public List<ActivityResponseDTO> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
        return activities.stream().map(MapToResponse::mapToResponse).collect(Collectors.toList());
    }

    public ActivityResponseDTO getUserActivity(String activityId) {
        return activityRepository.findById(activityId).map(MapToResponse::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
    }
}
