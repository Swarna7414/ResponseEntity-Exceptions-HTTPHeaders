package com.HTTPHeaders.Practise.Service;

import com.HTTPHeaders.Practise.Model.Account;
import com.HTTPHeaders.Practise.Model.Country;
import com.HTTPHeaders.Practise.Model.Currency;


import java.util.List;


public interface AccountService {
    Account createaccount(Account account);
    Account getAccount(Long id);
    Void deleteAccount(Long id);

    List<Account> findbycurriencs(Currency currency);

    List<String> findAccountsByCountries(Country country);
}
