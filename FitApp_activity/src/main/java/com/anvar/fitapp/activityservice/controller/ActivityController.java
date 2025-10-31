package com.anvar.fitapp.activityservice.controller;

import com.anvar.fitapp.activityservice.DTO.ActivityRequestDTO;
import com.anvar.fitapp.activityservice.DTO.ActivityResponseDTO;
import com.anvar.fitapp.activityservice.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> trackActivity(@RequestBody ActivityRequestDTO requestDTO){
        return ResponseEntity.ok(activityService.trackActivity(requestDTO));

    }

    @GetMapping
    public ResponseEntity<List<ActivityResponseDTO>> getUserActivities(@RequestHeader("X-User-Id") String userId){
        return ResponseEntity.ok(activityService.getUserActivities(userId));

    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponseDTO> getUserActivity(@PathVariable String activityId){
        return ResponseEntity.ok(activityService.getUserActivity(activityId));

    }
}
