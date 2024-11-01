package com.example.securePaymentManagement.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.securePaymentManagement.Models.Balance;

public interface BalanceRepository extends JpaRepository<Balance, Long> {
	
	Balance findTopByOrderByIdBalanceDesc();
	
	/*@Query(value = "SELECT * FROM balances WHERE idUsersEventsbalance = :id AND cancelBalance = 0 ", nativeQuery = true)
		    Balance getBalanceByLastId(@Param("id") int id);*/
	 @Query(value = "SELECT * FROM balances WHERE cancel_balance = 0 ORDER BY id_balance DESC LIMIT 1", nativeQuery = true)
		    Balance getBalanceByLastId();
	 
	 @Query(value = "SELECT initial_balance "
		 		+ "FROM balances WHERE cancel_balance = 0 ORDER BY id_balance DESC LIMIT 1", nativeQuery = true)
			    int findByInitialBalance();
}
