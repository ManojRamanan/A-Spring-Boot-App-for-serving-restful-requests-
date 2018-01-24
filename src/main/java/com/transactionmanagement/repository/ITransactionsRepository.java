package com.transactionmanagement.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.transactionmanagement.model.Transaction;

public interface ITransactionsRepository extends CrudRepository<Transaction, Serializable> {

	public List<Transaction> findByType(String transactionType);
	
	public List<Transaction> findByParentId(Long parentId);
	
	
}
