package com.HTTPHeaders.Practise.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/practise")
public class HttpController {


    @GetMapping("/Greeting")
    public ResponseEntity<String> greeting(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language){
        String message = "Hello ra Puka I only know"+language;
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/all-headers")
    public ResponseEntity<String> getallheaders(@RequestHeader HttpHeaders httpHeaders){
        StringBuilder headers = new StringBuilder();
        httpHeaders.forEach((keys,values)->headers.append(keys).append(": ").append(String.join(", ",values)).append("\n"));
        return ResponseEntity.ok(httpHeaders.toString());
    }

    @GetMapping("/headers")
    public ResponseEntity<String> addingHeaders(){
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("cusom","how");
        httpHeaders.add("ate","you");
        httpHeaders.add("Javk","si");

        httpHeaders.add("changin","changed");

        httpHeaders.set("changin","how"); //used for changing the Header

        return ResponseEntity.ok().headers(httpHeaders).body("PK");
    }



}
