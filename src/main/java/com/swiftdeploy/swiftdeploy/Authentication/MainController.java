package com.swiftdeploy.swiftdeploy.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;
import com.swiftdeploy.swiftdeploy.Models.UserModel;

@RestController
@RequestMapping("api")
public class MainController {

    @Autowired
    private UserRegistration userRegistration;

    @PostMapping("registeruser")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody UserModel userModel) {
        return userRegistration.resgisterUserService(userModel);
    }

    @GetMapping("getuserdetailsbyusername")
    public ResponseEntity<Object> getUserDetailsByUserName(@RequestHeader String userName)
    {
        return userRegistration.getUserDetailsByUserNameService(userName);
    }
}
