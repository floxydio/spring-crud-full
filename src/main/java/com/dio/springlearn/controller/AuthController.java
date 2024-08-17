package com.dio.springlearn.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dio.springlearn.entities.ResponseCreatedOrFailed;
import com.dio.springlearn.helper.EmailHelper;
import com.dio.springlearn.helper.JwtUtil;
import com.dio.springlearn.models.AuthModels;
import com.dio.springlearn.service.AuthService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
public class AuthController {
    // private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private EmailHelper emailHelper;

    @PostMapping("/register")
    public ResponseEntity<ResponseCreatedOrFailed> signUp(@RequestBody AuthModels authModels) {
        AuthModels authRequest = authService.signUp(authModels);

        if (authRequest == null) {
            return ResponseEntity.badRequest().body(new ResponseCreatedOrFailed(400, true, "Failed to create users"));
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseCreatedOrFailed(201, false, "Successfully Signup"));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody AuthModels authModels,HttpServletRequest request) {
        Map<String, Object> responseFailOrSuccess = new LinkedHashMap<>();
        AuthModels authModelsFindBy = authService.findByEmail(authModels);

        if (authModelsFindBy == null) {
            responseFailOrSuccess.put("status", 400);
            responseFailOrSuccess.put("error", true);
            responseFailOrSuccess.put("message", "User Tidak Ditemukan");

            return ResponseEntity.status(400).body(responseFailOrSuccess);
        } else {
            BCrypt.Result resultPassword = BCrypt.verifyer().verify(authModels.getPassword().toCharArray(), authModelsFindBy.getPassword());

            if (resultPassword.verified == false) {
                responseFailOrSuccess.put("status", 400);
                responseFailOrSuccess.put("error", true);
                responseFailOrSuccess.put("message", "Password salah");

                return ResponseEntity.status(400).body(responseFailOrSuccess);
            } else {
                String token = JwtUtil.generateAccessToken(authModelsFindBy.getEmail(), authModelsFindBy.getName(), authModelsFindBy.getUsersId(),request);
                String refreshToken = JwtUtil.generateRefreshToken(authModelsFindBy.getEmail(), authModelsFindBy.getName(), authModelsFindBy.getUsersId(),request);
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("access_token", token);
                data.put("refresh_token", refreshToken);
                responseFailOrSuccess.put("status", 200);
                responseFailOrSuccess.put("error", false);
                responseFailOrSuccess.put("data", data);
                responseFailOrSuccess.put("message", "Successfully Sign In");

                emailHelper.sendMail(authModels.getEmail(), "Berhasil Login!", "Terdapat login baru");
                return ResponseEntity.status(200).body(responseFailOrSuccess); 
            }

        }
    }

}
