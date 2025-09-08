package com.HTTPHeaders.Practise.Controller;

import com.HTTPHeaders.Practise.Model.Account;
import com.HTTPHeaders.Practise.Model.Country;
import com.HTTPHeaders.Practise.Model.Currency;
import com.HTTPHeaders.Practise.Response.APIResponse;
import com.HTTPHeaders.Practise.Service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse<Account>> createAccount(@RequestBody Account account) throws URISyntaxException {
        APIResponse response = new APIResponse(true, HttpStatus.CREATED,accountService.createaccount(account));

        return ResponseEntity.created(new URI("/accounts"+account.getId())).body(response);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<APIResponse<Account>> getAccount(@PathVariable Long id){
        APIResponse response = new APIResponse(true, HttpStatus.OK, accountService.getAccount(id));

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/currency")
    public ResponseEntity<APIResponse<List<Account>>> getByCurriences(@RequestParam Currency currency){
        return ResponseEntity.ok().body(new APIResponse<>(true, HttpStatus.OK, accountService.findbycurriencs(currency)));
    }

    @GetMapping("/countires/{country}")
    public ResponseEntity<APIResponse<List<String>>> getbyCountries(@PathVariable Country country){
        return ResponseEntity.ok().body(new APIResponse<>(true, HttpStatus.OK, accountService.findAccountsByCountries(country)));
    }


}
