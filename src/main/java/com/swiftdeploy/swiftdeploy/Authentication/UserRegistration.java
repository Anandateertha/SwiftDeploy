package com.swiftdeploy.swiftdeploy.Authentication;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.swiftdeploy.swiftdeploy.Models.ResponseMessage;
import com.swiftdeploy.swiftdeploy.Models.UserModel;
import com.swiftdeploy.swiftdeploy.Repositories.UserRepository;

@Service
public class UserRegistration {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    public String hashPassword(String password) {
        String strong_salt = BCrypt.gensalt(10);
        String encyptedPassword = BCrypt.hashpw(password, strong_salt);
        return encyptedPassword;
    }

    public ResponseEntity<ResponseMessage> resgisterUserService(UserModel user) {

        ResponseMessage responseMessage = new ResponseMessage();
        try {

            UserModel userExist = userRepository.findByUserName(user.getUserName());
            if (userExist == null) {
                user.setPassword(hashPassword(user.getPassword()));
                userRepository.save(user);
                responseMessage.setSuccess(true);
                responseMessage.setMessage("User registred. Token: " + authService.generateToken(user.getEmail()));
                return ResponseEntity.status(200).body(responseMessage);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("User already exists with this user name");
                return ResponseEntity.status(200).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }

    public ResponseEntity<Object> getUserDetailsByUserNameService(String userName) {
        ResponseMessage responseMessage = new ResponseMessage();
        try {

            UserModel userExist = userRepository.findByUserName(userName);
            if (userExist == null) {
                return ResponseEntity.status(200).body(userExist);
            } else {
                responseMessage.setSuccess(false);
                responseMessage.setMessage("No user found");
                return ResponseEntity.status(200).body(responseMessage);
            }

        } catch (Exception e) {
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Internal Server Error. Reason: " + e.getMessage());
            return ResponseEntity.status(500).body(responseMessage);
        }
    }
}
