package services;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import entities.Customer;
import entities.Product;

public class CustomerService implements CustomerServiceInterface {

	private List<Customer> customers;

	public CustomerService(List<Customer> customers) {
		this.customers = customers;
	}

	@Override
	public List<Customer> findByName(String name) {
		
		return customers.stream()
				.filter(c->c.getName().equals(name))
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> findByField(String fieldName, Object value) {
		
		// TODO Auto-generated method stub
		return customers.stream()
				.filter(    c->{
					try {
						Field f = c.getClass().getDeclaredField(fieldName);
						if(f.isAccessible())
							try {
								return f.get(c).equals(value);
							} catch (IllegalArgumentException | IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						else
							try {
								return c.getClass().getMethod("get" 
								+ fieldName.substring(0, 1).toUpperCase() 
								+ fieldName.substring(1)).invoke(c).equals(value);
							} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
									| NoSuchMethodException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						return c.getClass().getDeclaredField(fieldName).equals(value);
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
					
				}    )
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoBoughtMoreThan(int number) {
		
		return customers.stream()
				.filter(c->c.getBoughtProducts().size()>number)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWhoSpentMoreThan(double price) {
		
		return customers.stream()
				.filter(c->c.getBoughtProducts().stream()
						.mapToDouble(p->p.getPrice()).sum() > price)
				.collect(Collectors.toList());
	}

	@Override
	public List<Customer> customersWithNoOrders() {
		
		return customers.stream()
				.filter(c->c.getBoughtProducts().size() == 0)
				.collect(Collectors.toList());
	}

	@Override
	public void addProductToAllCustomers(Product p) {
		customers.stream().forEach(c->c.addProduct(p));

	}

	@Override
	public double avgOrders(boolean includeEmpty) {
		
		return customers.stream()
				.mapToDouble(x-> includeEmpty ? x.getBoughtProducts().size() : x.getBoughtProducts().isEmpty() ? 0 : x.getBoughtProducts().size()).sum() / 
				(includeEmpty ? customers.size() : customers.stream().filter(x->x.getBoughtProducts().size()>0).collect(Collectors.toList()).size());
	}

	@Override
	public boolean wasProductBought(Product p) {
		
		return customers.stream()
				.filter(c->c.getBoughtProducts().stream()
						.filter(x->x.getId()==p.getId()).count()>0).count() > 0;
	}

	@Override
	public List<Product> mostPopularProduct() {
		Map<Integer, Product> pp = new HashMap<Integer, Product>();
		
		Map<Integer, Integer> pro_val = new HashMap<Integer, Integer>(); 
		
		customers.stream()
		.filter(c->c.getBoughtProducts().size()>0)
		.map(c->c.getBoughtProducts())
		.collect(Collectors.toList()).stream().flatMap(List::stream)
		.collect(Collectors.toList())
		.stream().forEach(p->{
				if(pro_val.get(p.getId()) != null){
					pro_val.put(p.getId(), 1 + pro_val.get(p.getId()));
			} else {
				pp.put(p.getId(), p);
				pro_val.put(p.getId(), 1);
			}
		});
		
		Integer max = Collections.max(pro_val.values());
		List<Product> prod = new ArrayList<Product>();
		
		for(Integer k : pro_val.keySet()){
			if(pro_val.get(k) == max) prod.add(pp.get(k));
		}
		
		
		
		
		return prod;
	}

	@Override
	public int countBuys(Product p) {
		// TODO Auto-generated method stub
		return customers.stream()
				.filter(c->c.getBoughtProducts().stream()
						.filter(x->x.getId()==p.getId()).count()>0)
				.collect(Collectors.toList()).size();
	}

	@Override
	public int countCustomersWhoBought(Product p) {
		// TODO Auto-generated method stub
		return customers.stream()
				.filter(c->c.getBoughtProducts().stream()
						.filter(x->x.getId()==p.getId()).count()>0)
				.collect(Collectors.toList()).size();
	}

}
