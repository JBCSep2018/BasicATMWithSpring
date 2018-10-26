package me.afua.basicatm;

import me.afua.basicatm.model.Transaction;
import me.afua.basicatm.model.TransactionRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicatmApplicationTests {

    @Autowired
    TransactionRepository transactionRepository;
    @Test
    public void contextLoads() {
//      Check withdraw and deposit methods
        Transaction transaction = new Transaction();
        transaction.setAccountNumber("12345");
        transaction.setAction("withdraw");
        transaction.withdraw(3000);
        System.out.println(transaction.getAmount());

        transaction.setReason("Because I want to");

        //Saving to the database works
        transactionRepository.save(transaction);

        transaction = new Transaction();
        transaction.setAccountNumber("12345");
        transaction.setAction("deposit");
        transaction.deposit(5000);
        System.out.println(transaction.getAmount());


        transaction.setReason("Because I can");
        //Saving to the database works
        transactionRepository.save(transaction);

        System.out.println("Account balance:"+transactionRepository.sumAmountByAccountNumber("12345"));

        assertEquals(transactionRepository.sumAmountByAccountNumber("12345"),new Double(2000));

    }

}
