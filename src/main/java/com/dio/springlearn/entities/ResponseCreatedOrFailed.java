package com.dio.springlearn.entities;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreatedOrFailed {
        private int status;
        private boolean error;
        private String message;

        public ResponseCreatedOrFailed(int status,boolean error, String message) {
            this.status = status;
            this.error = error;
            this.message = message;
        }

}
