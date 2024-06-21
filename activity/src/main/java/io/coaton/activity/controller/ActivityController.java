package io.coaton.activity.controller;

import io.coaton.activity.dto.ActivityDto;
import io.coaton.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activity/{activityId}")
    public Optional<ActivityDto> getActivity(@PathVariable BigInteger activityId) {
        return activityService.getActivity(activityId);
    }

    @GetMapping("/activity")
    public List<ActivityDto> getAllActivities(@RequestParam(required = false) Map<String,String> tags) {
        return activityService.getAllActivities(tags);
    }

    @PostMapping("/activity")
    public void addActivity(@RequestBody ActivityDto activity) {
        activityService.addActivity(activity);
    }

    @PutMapping("/activity/{activityId}")
    public void updateActivity(@RequestBody ActivityDto activity, @PathVariable BigInteger activityId) {
        activity.setId(activityId);
        activityService.updateActivity(activity);
    }

    @DeleteMapping("/activity/{activityId}")
    public void deleteActivity(@PathVariable BigInteger activityId) {
        activityService.deleteActivity(activityId);
    }
}
