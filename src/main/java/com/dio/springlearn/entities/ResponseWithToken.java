package com.dio.springlearn.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseWithToken {
        private int status;
        private boolean error;
        private String token;
        private Object data;
        private String message;

        public ResponseWithToken(int status, boolean error,String token,Object data, String message ) {
            this.status = status;
            this.error = error;
            this.token = token;
            this.data = data;
            this.message = message;
        }
    
}
