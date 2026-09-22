package app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import app.dto.DiningBookingRequest;
import app.dto.DiningBookingResponse;
import app.dto.DiningEventResponse;
import app.dto.EvenFilterRequest;
import app.dto.EventFilterResponse;
import app.dto.EventResponse;
import app.dto.StatusUpdate;
import app.dto.UserUpdateProfileRequest;
import app.entity.Event;
import app.service.impl.UserServiceImpl;




@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/list")
    public ResponseEntity<?> getAllEvents(
            @RequestParam(required=false) String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false,defaultValue = "Chennai") String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<Event> event =
                userService.getAllEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/list/popularity")
    public ResponseEntity<?> TopPopularityEvents(
            @RequestParam String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false,defaultValue = "Chennai")  String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<EventResponse> event =
                userService.TopPopularityEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/list/weekend")
    public ResponseEntity<?> weekEndEvents(
            @RequestParam String userId,
            @RequestParam(required = false) Double latitude,
            @RequestParam(required = false) Double longitude,
            @RequestParam(required = false,defaultValue = "Chennai") String location,
            @RequestParam(defaultValue = "10") double radius) {

        List<EventResponse> event =
                userService.weekEndEvents(
                        userId,
                        latitude,
                        longitude,
                        location,
                        radius
                );

        return new ResponseEntity<>(
                event,
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(
            @PathVariable String id) {

        return new ResponseEntity<>(
                userService.getUser(id),
                HttpStatus.OK
        );
    }

    @PutMapping("/profile/update")
    public ResponseEntity<?> updateProfileById(
            @RequestParam String userId,
            @RequestParam String role,
            @RequestBody UserUpdateProfileRequest userUpdate) {

        return new ResponseEntity<>(
                userService.updateProfileById(
                        userId,
                        role,
                        userUpdate
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateStatus(
            @RequestParam String userId,
            @RequestParam String role,
            @RequestBody StatusUpdate status) {

        return new ResponseEntity<>(
                userService.updateStatus(
                        userId,
                        role,
                        status
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/filter/events")
    public ResponseEntity<?> getFilteredEvents(
            @RequestBody EvenFilterRequest filter) {

        List<EventFilterResponse> events =
                userService.getFilteredEvents(filter);

        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/list/category/{categoryId}")
    public ResponseEntity<?> getEventsByCategory(

            @PathVariable String categoryId,

            @RequestParam String userId,

            @RequestParam(required = false) Double latitude,

            @RequestParam(required = false) Double longitude,

            @RequestParam(required = false, defaultValue = "Chennai") String location,

            @RequestParam(defaultValue = "10") double radius) {

        List<EventResponse> events = userService.getEventsByCategory(
                categoryId,
                userId,
                latitude,
                longitude,
                location,
                radius
        );

        return ResponseEntity.ok(events);
    }
    
    @GetMapping("/diningevent/{Id}")
    public ResponseEntity<?> getDiningEvent(@PathVariable String Id) {

        DiningEventResponse event=userService.getDiningEvent(Id);

        return ResponseEntity.ok(event);
    }
    
    @PostMapping("/booking/dining")
    
    public ResponseEntity<?> bookingDining(@RequestParam String userId,
            @RequestParam String Id,@RequestBody DiningBookingRequest request) {

        DiningBookingResponse response =userService.bookingDining(userId,Id,request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}
