package com.HTTPHeaders.Practise.Repository;

import com.HTTPHeaders.Practise.Model.Account;
import com.HTTPHeaders.Practise.Model.Currency;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long>{
    List<Account> findByCurrency(Currency currency);
}