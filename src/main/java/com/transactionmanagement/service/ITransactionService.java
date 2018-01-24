package com.transactionmanagement.service;

import java.util.List;

import com.transactionmanagement.model.Transaction;

public interface ITransactionService {
	
	public Transaction saveOrUpdateTransaction(Transaction transaction);

	public List<Transaction> fetchTransactionsByType(String transactionType);
	
	public Double sumOfTransactions(Long transactionId);

}
