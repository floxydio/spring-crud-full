package com.dio.springlearn.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseOK {
        private int status;
        private boolean error;
        private Object data;
        private String message;

        public ResponseOK(int status, boolean error,Object data, String message ) {
            this.status = status;
            this.error = error;
            this.data = data;
            this.message = message;
        }
    
}
