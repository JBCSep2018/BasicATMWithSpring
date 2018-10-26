package me.afua.basicatm.model;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction,Long> {
//  Pass a variable of the type that you would like to search on. Return an iterable (collection)
    Iterable <Transaction> findAllByAccountNumber(String searchFor);
    Iterable <Transaction> findAllByAction(String depositWithdraw);

    //Find a count
    Double countAllByAccountNumber(String searchFor);

    @Query(value = "select sum(amount) from transaction where account_number=?1",nativeQuery = true)
    Double sumAmountByAccountNumber(String searchFor);

}
