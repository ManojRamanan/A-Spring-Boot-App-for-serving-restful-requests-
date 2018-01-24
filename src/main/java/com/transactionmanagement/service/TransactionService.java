package com.transactionmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanagement.model.Transaction;
import com.transactionmanagement.repository.ITransactionsRepository;

@Service
public class TransactionService implements ITransactionService {

	@Autowired
	private ITransactionsRepository transactionRepository;

	@Override
	@Transactional
	public Transaction saveOrUpdateTransaction(Transaction transaction) {
		 return transactionRepository.save(transaction);
	}

	@Override
	public List<Transaction> fetchTransactionsByType(String transactionType) {
		return transactionRepository.findByType(transactionType);
	}

	@Override
	@Transactional(readOnly = true)
	public Double sumOfTransactions(Long transactionId) {

		Transaction transaction = transactionRepository.findOne(transactionId);

		if (transaction == null) {
			return 0d;
		}

		if (transaction.getParentId() == null) {
			return transaction.getAmount();
		}

		List<Transaction> findByParentId = transactionRepository.findByParentId(transaction.getParentId());

		return findByParentId.stream().filter(e -> e.getAmount() > 0).mapToDouble(e -> e.getAmount()).sum();

	}

}
