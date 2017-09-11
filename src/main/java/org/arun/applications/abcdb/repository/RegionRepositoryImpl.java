package org.arun.applications.abcdb.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.log4j.Logger;
import org.arun.applications.abcdb.domain.Customer;
import org.arun.applications.abcdb.domain.Region;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RegionRepositoryImpl implements RegionRepository {

	private static final Map<String, Region> regionsDB = new HashMap<>();
	private static final Logger LOG = Logger.getLogger(RegionRepositoryImpl.class);
	
	@Override
	public Region save(Region entity) {
		
		if(entity==null) return entity;
		
		String id = entity.getName();	// assumption - name is mandatory - use id - the same as name for now.
		if(StringUtils.isEmpty(id)){
			id = UUID.randomUUID().toString();
			LOG.error("unexpected data input found. Risk of invalid data growth. Please avoid inserting invalid data.");
			entity.setName(id);
		}
		
		Region savedEntity;
		synchronized(regionsDB){
			savedEntity = regionsDB.get(id);
			if(savedEntity==null){
				savedEntity = new Region();
				savedEntity.setName(id);
				regionsDB.put(id, savedEntity);
			}
		}
		
		synchronized(savedEntity){
			savedEntity.setName(entity.getName());
			if(entity.getCustomersInRegion()!=null){
				savedEntity.setCustomersInRegion(entity.getCustomersInRegion());
			}
			return savedEntity;
		}
	}

	@Override
	public long count() {
		synchronized(regionsDB){
			return regionsDB.size();
		}
	}

	@Override
	public boolean delete(Region entity) {
		synchronized(regionsDB){
			if(regionsDB.containsValue(entity)){	// default O(n) implementation
				regionsDB.remove(entity.getName(), entity);
				return true;
			}
		}
		return false;
	}

	@Override
	public Region findByID(String id) {
		synchronized(regionsDB){
			Region foundRegion = regionsDB.get(id);
			
			if(foundRegion==null) return null;
			
			return new Region(foundRegion);
		}
	}
	
	@Override
	public Region findByIDAndApplyFilterOnCustomers(String id, Predicate<Customer> predicate){
		Region regionToBeReturned = null;
		Map<String, Customer> filteredCustomersInRegion = new HashMap<String, Customer>();
		
		synchronized(regionsDB){
			Region foundRegion = regionsDB.get(id);
			
			if(foundRegion==null) return null;
			
			regionToBeReturned = new Region(foundRegion);
		}
		
		if(predicate!=null){
			Map<String, Customer> customersInRegion = regionToBeReturned.getCustomersInRegion();
			regionToBeReturned.setCustomersInRegion(filteredCustomersInRegion);
			
			if(customersInRegion!=null && customersInRegion.size()>0){
				
				Iterable<Customer> customers = customersInRegion.values();
				customers.forEach((Customer cust) -> {
					if(predicate.test(cust)){
						filteredCustomersInRegion.put(cust.getId(), cust);
					}
				});
				
			}
		}
		return regionToBeReturned;
	}

	@Override
	public Iterable<Region> findAll() {
		synchronized(regionsDB){
			return new ArrayList<Region>(regionsDB.values());
		}
	}

	@Override
	public Region findByName(String name) {
		List<Region> list, returnList = new ArrayList<Region>();
		synchronized(regionsDB){
			list = new ArrayList<Region>(regionsDB.values());
		}
		
		list.forEach((Region t) -> {
			
			if (t!=null && t.getName()!=null && t.getName().equals(name)){
				returnList.add(t);
			}
		});
		
		if(returnList.size()>0){
			return new Region(returnList.get(0));
		}
		return null;
	}

}
