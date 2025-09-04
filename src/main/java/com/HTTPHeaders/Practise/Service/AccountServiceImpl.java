package com.HTTPHeaders.Practise.Service;

import com.HTTPHeaders.Practise.Exceptions.AccountNotFoundException;
import com.HTTPHeaders.Practise.Exceptions.BusinessBalanceException;
import com.HTTPHeaders.Practise.Model.Account;
import com.HTTPHeaders.Practise.Model.AccountType;
import com.HTTPHeaders.Practise.Model.Country;
import com.HTTPHeaders.Practise.Model.Currency;
import com.HTTPHeaders.Practise.Repository.AccountRepository;
import com.sun.jdi.request.DuplicateRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    @Autowired
    private final AccountRepository accountRepository;


    @Override
    public Account createaccount(Account account) {
        if (accountRepository.existsById(account.getId())){
            throw new DuplicateRequestException("Account Already exists");
        }

        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->new AccountNotFoundException(id));

        if (account.getAccountType().equals(AccountType.BUSINESS)
            && account.getBalance().compareTo(BigDecimal.valueOf(5000))<=0){
            throw new BusinessBalanceException("Business Accounts must have more than 5000");
        }
        return account;
    }

    @Override
    public Void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)){
            throw new AccountNotFoundException(id);
        }

        accountRepository.deleteById(id);
        return null;
    }

    @Override
    public List<Account> findbycurriencs(Currency currency) {
        return accountRepository.findAll();
    }

    @Override
    public List<String> findAccountsByCountries(Country country) {
        List<Account> accounts=accountRepository.findAll();
        List<String> persons = new LinkedList<>();

        persons=accounts.stream().filter(n->n.getCountry().equals(country)).map(Account::getName).collect(Collectors.toUnmodifiableList());

        return persons;
    }


}
