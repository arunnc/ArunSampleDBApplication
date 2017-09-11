package org.arun.applications.abcdb.domain;

import java.util.HashMap;
import java.util.Map;

public class Region {

	// private String id;  use name as Identifier for simplicity.
	private String name;
	private Map<String, Customer> customersInRegion;
	
	public Region(){
		super();
	}
	public Region(Region b){
		if(b!=null){
		  this.name = b.name;
		  if(b.getCustomersInRegion()!=null){
			  this.customersInRegion = new HashMap<String, Customer>(b.getCustomersInRegion());
		  }
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Customer> getCustomersInRegion() {
		return customersInRegion;
	}
	public void setCustomersInRegion(Map<String, Customer> customersInRegion) {
		this.customersInRegion = customersInRegion;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if (name!=null){
			hashCode = name.hashCode();
		}
		
		return hashCode;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Region){
			Region region = (Region) obj;
			
			if( ((region.getName()==null && this.getName()==null) ||
					(region.getName().equals(name))) ){
				return true;
			}
		}
		return false;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{ name:"+ name +" }";
	}
	
}
