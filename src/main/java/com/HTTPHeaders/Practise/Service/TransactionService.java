package com.HTTPHeaders.Practise.Service;

import com.HTTPHeaders.Practise.Exceptions.AccountNotFoundException;
import com.HTTPHeaders.Practise.Exceptions.inSufficentFundsException;
import com.HTTPHeaders.Practise.Model.Account;
import com.HTTPHeaders.Practise.Model.Transaction;
import com.HTTPHeaders.Practise.Repository.AccountRepository;
import com.HTTPHeaders.Practise.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    private final AccountRepository accountRepository;

    @Transactional
    public String transferMoney(Transaction transaction){
        if (!accountRepository.existsById(transaction.getSenderId()) || !accountRepository.existsById(transaction.getReceiverId())){
            throw new AccountNotFoundException(transaction.getId());
        }

        Account senderaccount = accountRepository.findById(transaction.getSenderId())
                .orElseThrow(()-> new AccountNotFoundException(transaction.getSenderId()));

        Account recieverAccount=accountRepository.findById(transaction.getReceiverId()).orElseThrow(()->new AccountNotFoundException(transaction.getReceiverId()));


        if (senderaccount.getBalance().compareTo(transaction.getAmount())<0){
            throw new inSufficentFundsException("Insufficent Funds ra puka");
        }

        senderaccount.setBalance(senderaccount.getBalance().subtract(transaction.getAmount()));
        accountRepository.save(senderaccount);

        recieverAccount.setBalance(recieverAccount.getBalance().add(transaction.getAmount()));

        accountRepository.save(recieverAccount);

        transactionRepository.save(transaction);

        return "Amount Transfered Successfully";
    }

}