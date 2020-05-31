package com.duminska.ultimatetasklist.user;

import com.duminska.ultimatetasklist.config.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(Constants.USER_URLS)
public class UserController {

    final private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUpUser(@RequestBody DtoUserAuth user) {
        UserValidator.validate(user);
        User newUser = userService.signUpUser(user);

        return new ResponseEntity<>(DtoUser.fromUser(newUser), HttpStatus.CREATED);
    }

    @PostMapping(value = "/log-in")
    public ResponseEntity<?> login(@RequestBody DtoUserAuth userAuth) {
        UserValidator.validate(userAuth);

        String token = userService.loginUser(userAuth);

        UserLoginSuccessResponse successResponse = UserLoginSuccessResponse.builder()
                .token(token)
                .success(true).build();

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam("key") String link) {
        userService.activateUser(link);
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
