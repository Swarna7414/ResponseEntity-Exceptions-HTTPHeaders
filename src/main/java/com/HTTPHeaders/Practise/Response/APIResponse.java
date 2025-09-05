package com.HTTPHeaders.Practise.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class APIResponse<T> {
    private boolean success;
    private HttpStatus status;
    private T data;

}
