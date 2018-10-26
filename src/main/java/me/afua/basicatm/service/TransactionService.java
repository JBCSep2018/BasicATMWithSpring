package me.afua.basicatm.service;

import me.afua.basicatm.model.Transaction;
import me.afua.basicatm.model.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactions;


    public  Iterable<Transaction> getTransactionSummary(String findAccountNumber)
    {

         double balance = 0;

         for(Transaction eachTransaction: transactions.findAllByAccountNumber(findAccountNumber))
         {
             balance+=eachTransaction.getAmount();
             eachTransaction.setBalance(balance);
             transactions.save(eachTransaction);
         }

        return transactions.findAllByAccountNumber(findAccountNumber);
    }

}
