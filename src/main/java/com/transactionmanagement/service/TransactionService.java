package com.transactionmanagement.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transactionmanagement.model.Transaction;
import com.transactionmanagement.repository.ITransactionsRepository;

@Service
public class TransactionService implements ITransactionService {

	private static final Log logger = LogFactory.getLog(TransactionService.class);

	@Autowired
	private ITransactionsRepository transactionRepository;

	@Override
	@Transactional
	public Transaction saveOrUpdateTransaction(Transaction transaction) {
		try {
			return transactionRepository.save(transaction);
		} catch (Exception e) {
			logger.warn("Exception in saveOrUpdateTransaction " + e.toString());
		}
		return null;
	}

	@Override
	public List<Transaction> fetchTransactionsByType(String transactionType) {
		try {
			return transactionRepository.findByType(transactionType);
		} catch (Exception e) {
			logger.error("Exception in fetchTransactionsByType " + e.toString());
		}
		return Collections.emptyList();
	}

	@Override
	@Transactional(readOnly = true)
	public Double sumOfTransactions(Long transactionId) {
		try {
			Transaction transaction = transactionRepository.findOne(transactionId);
			if (transaction == null) {
				return 0d;
			}
			if (transaction.getParentId() == null) {
				return transaction.getAmount();
			}
			List<Transaction> findByParentId = transactionRepository.findByParentId(transaction.getParentId());
			return findByParentId.stream().filter(e -> e.getAmount() > 0).mapToDouble(e -> e.getAmount()).sum();
		} catch (Exception e) {
			logger.warn("Exception in calculate transaction amount " + e.toString());
		}

		return 0D;

	}

}
