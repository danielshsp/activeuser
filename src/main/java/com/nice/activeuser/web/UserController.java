package com.nice.activeuser.web;

import com.nice.activeuser.dto.User;
import com.nice.activeuser.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "View a list of available users" ,response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping("/user")
    public ResponseEntity<List<User>> findAll(@RequestParam(required = false) boolean nocache) {
        return ResponseEntity.ok(userService.findAll(nocache));
    }

    @ApiOperation(value = "retrieve active user per daily by insertdate",response = User.class)
    @GetMapping("/user/{insertDate}")
    public ResponseEntity<User> findAll(@PathVariable @PastOrPresent @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate insertDate) {
        return ResponseEntity.ok(userService.findUserByInsertDate(insertDate).get());
    }

    @DeleteMapping("/cache")
    public void clearCache() {
        userService.clearCache();
    }
}
