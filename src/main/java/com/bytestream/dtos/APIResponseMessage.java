package com.bytestream.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponseMessage {

    private String message;
    private boolean success;
    private HttpStatus status;
}
