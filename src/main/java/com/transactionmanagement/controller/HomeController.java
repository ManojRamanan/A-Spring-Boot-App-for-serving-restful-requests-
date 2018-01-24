package com.transactionmanagement.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.transactionmanagement.enums.StatusEnum;
import com.transactionmanagement.model.Transaction;
import com.transactionmanagement.service.ITransactionService;
import com.transactionmanagement.webhelper.JsonResponse;

/**
 * Transaction Controller
 * 
 * @author manojramana
 *
 */
@RestController
@RequestMapping("/transactionservice/")
public class HomeController {

	@Autowired
	private ITransactionService transactionService;

	/**
	 * Facilitates either creation or updating transactions
	 * 
	 * @param transaction
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "/transaction/{transaction_id}", method = RequestMethod.PUT)
	private JsonResponse saveOrUpdateTransaction(@RequestBody Transaction transaction,
			@PathVariable("transaction_id") Long transactionId, BindingResult bindingResult) {
		JsonResponse jsonResponse = new JsonResponse();

		if (transaction.hasErrors()) {
			jsonResponse.setStatusMessage("Mandatory fields are missing");
			return jsonResponse;
		}

		transaction.setId(transactionId);
		Transaction transactionFromDb = transactionService.saveOrUpdateTransaction(transaction);

		if (transactionFromDb != null && transactionFromDb.getId() != null) {
			jsonResponse.setStatus(StatusEnum.OK.name());
		} else {
			jsonResponse.setStatus(StatusEnum.FAILURE.name());
		}

		return jsonResponse;
	}

	/**
	 * Facilitates fetching of transactions by type returns empty if no
	 * transactions is found
	 * 
	 * @param transactionType
	 * @return
	 */
	@RequestMapping(value = "/types/{type}", method = RequestMethod.GET)
	private List<Long> listTransactionsbyType(@PathVariable("type") String transactionType) {
		List<Long> transactionIds = new ArrayList<>();
		List<Transaction> transactions = transactionService.fetchTransactionsByType(transactionType);
		transactions.forEach(e -> transactionIds.add(e.getId()));
		return transactionIds;
	}

	/**
	 * Calculates and returns sum of all the transactions given A transaction id
	 * of transactions groups based on the parent id similar transactions
	 * 
	 * @param transactionId
	 * @return
	 */
	@RequestMapping(value = "/sum/{transaction_id}", method = RequestMethod.GET)
	private Map<String, Double> calcSumOfTransactions(@PathVariable("transaction_id") Long transactionId) {
		Double sumOfTransactions = transactionService.sumOfTransactions(transactionId);
		Map<String, Double> results = new HashMap<>();
		results.put("sum", sumOfTransactions);
		return results;
	}

	/**
	 * Exception handler if NoSuchElementException is thrown in this Controller
	 *
	 * @param ex
	 * @return Error message String.
	 */
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public String return400(NoSuchElementException ex) {
		return ex.getMessage();

	}

}
