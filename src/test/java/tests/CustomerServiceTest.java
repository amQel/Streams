package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import services.CustomerService;
import services.CustomerServiceInterface;
import entities.Customer;
import entities.Product;

public class CustomerServiceTest {

	@Test
	public void testFindByName() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByName("Customer: 1");
		//System.out.println(res.get(0).getName());
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}
	
	@Test
	public void testFindByField() throws NoSuchFieldException, SecurityException {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.findByField("email", "email");
		//System.out.println(res.get(0).getName());
		assertNotNull("Result can't be null", res);
		assertEquals(10, res.size());
	}
	
	@Test
	public void testCustomersWhoBoughtMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWhoBoughtMoreThan(2);

		assertNotNull("Result can't be null", res);
		assertEquals(5, res.size());
	}
	
	@Test
	public void testCustomersWhoSpentMoreThan() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWhoSpentMoreThan(2);
		
		assertNotNull("Result can't be null", res);
		assertEquals(4, res.size());
	}
	
	@Test
	public void testCustomersNoOrders() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		
		List<Customer> res = cs.customersWithNoOrders();

		assertNotNull("Result can't be null", res);
		assertEquals(3, res.size());
	}
	
	@Test
	public void testAddProductsToAllAndWasProductBought() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		Product xxx = new Product(185, "13 ziaren piasku" , 2.99);
		cs.addProductToAllCustomers(xxx);
		
		int res = cs.countBuys(xxx);
//		System.out.println(res.size());
		assertNotNull("Result can't be null", res);
		assertEquals(10, res);
	}
	
	@Test
	public void testCountOfProducts() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
		Product xxx = new Product(186, "17 ziaren piasku" , 2.98);
		cs.addProductToAllCustomers(xxx);
		
		int res = cs.countCustomersWhoBought(xxx);
//		System.out.println(res.size());
		assertNotNull("Result can't be null", res);
		assertEquals(10, res);
	}
	
	@Test
	public void testAvgProducts() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
	
		
		double res = cs.avgOrders(false);
		System.out.println(res);
		assertNotNull("Result can't be null", res);
		assertEquals(4.0, res, 0.5);
	}
	
	@Test
	public void testMostPopular() {
		CustomerServiceInterface cs = new CustomerService(DataProducer.getTestData(10));
	
		
		List<Product> res = cs.mostPopularProduct();
		assertNotNull("Result can't be null", res);
		assertEquals(1, res.size());
	}
	
	
	
	

}
