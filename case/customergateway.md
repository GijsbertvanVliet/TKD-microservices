# Customer Gateway

* Get the description of the service
* Get a customer and all its orders
  * If the customer service is not available, return only the customer id
  * If the order service is not available, return instead a message that no order information could be gathered
* (Optional) Introduce retries if the customer service or the order service is not available
