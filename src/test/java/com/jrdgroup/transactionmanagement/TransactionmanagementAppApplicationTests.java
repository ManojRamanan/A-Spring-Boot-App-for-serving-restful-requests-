package com.jrdgroup.transactionmanagement;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.transactionmanagement.enums.StatusEnum;
import com.transactionmanagement.model.Transaction;

public class TransactionmanagementAppApplicationTests {

	public static void main(String args[]) throws JSONException {

		TransactionmanagementAppApplicationTests appApplicationTests = new TransactionmanagementAppApplicationTests();
		appApplicationTests.testSaveOrUpdateService();
		appApplicationTests.testListOfTransactions();
		appApplicationTests.testCalculateSumOfTransactions();

	}

	private void testSaveOrUpdateService() throws JSONException {

		RestTemplate restTemplate = new RestTemplate();

		Transaction transaction = new Transaction();

		transaction.setAmount(200d);
		transaction.setParentId(123L);
		transaction.setType("cars");

		String url = "http://localhost:8080/transactionservice/transaction/{id}";

		for (Long i = 101L; i < 106L; i++) {

			Long id = i;

			JSONObject jsonObject = new JSONObject();

			jsonObject.put("amount", transaction.getAmount().toString());
			jsonObject.put("type", transaction.getType());
			jsonObject.put("parent_id", transaction.getParentId());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(jsonObject.toString(), headers);
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, id);

			String responseBody = response.getBody();

			JSONObject object = new JSONObject(responseBody);

			System.out.println(object.toString());

			if (object.getString("status").equals(StatusEnum.OK.name())) {
				System.out.println("Transaction succesfully created");
			}

		}

	}

	private void testListOfTransactions() throws JSONException {
		RestTemplate restTemplate = new RestTemplate();

		String type = "cars";

		String listOfTransactionsByType = restTemplate
				.getForObject("http://localhost:8080/transactionservice/types/{type}", String.class, type);

		JSONArray listOfTransactions = new JSONArray(listOfTransactionsByType);

		assertEquals(Boolean.TRUE, listOfTransactions.length() > 0 ? Boolean.TRUE : Boolean.FALSE);

		if (listOfTransactions.length() > 0) {
			System.out.println("Total Number of Transactions successfully fetched " + listOfTransactions.length());
		} else {
			System.out.println("No Transactions found");
		}

		System.out.println(listOfTransactions.toString());
	}

	private void testCalculateSumOfTransactions() {

		RestTemplate restTemplate = new RestTemplate();
		Long transactionId = 101L;

		String sum = restTemplate.getForObject("http://localhost:8080/transactionservice/sum/{transactionId}",
				String.class, transactionId);

		System.out.println("Sum Of Transactions for the given Id " + sum);
	}

}
