# Transactionmanagement
A spring boot based App for Transaction management using restful services.it uses a in memory h2 database .
It is a maven project based project.

Spring modules
--------------
Spring data jpa,Spring mvc



The api Specification is as follows
-----------------------------------------------
PUT /transactionservice/transaction/$transaction_id
Body:
{ "amount":double,"type":string,"parent_id":long }
where:
• transaction id is a long specifying a new transaction
• amount is a double specifying the amount
• type is a string specifying a type of the transaction.
• parent id is an optional long that may specify the parent transaction of
this transaction.	

GET /transactionservice/transaction/$transaction_id
Returns: { "amount":double,"type":string,"parent_id":long }

GET /transactionservice/types/$type
Returns: [long, long, ... ]
A json list of all transaction ids that share the same type $type.

GET /transactionservice/sum/$transaction_id
Returns: { "sum": double }
A sum of all transactions that are transitively linked by their parent_id to
$transaction_id.

----------------------------------------------------------
0.3 Examples
----------------------------------------------------------
PUT /transactionservice/transaction/10 { "amount": 5000, "type":"cars" }
=> { "status": "ok" }
PUT /transactionservice/transaction/11
{ "amount": 10000, "type": "shopping", "parent_id": 10}
=> { "status": "ok" }
GET /transactionservice/types/cars => [10]
GET /transactionservice/sum/10 => {"sum":15000}
GET /transactionservice/sum/11 => {"sum":10000}

