package org.arun.applications.abcdb.domain;

public class Customer {

	private String id;
	private String name;
	private int age;
	private Region belongsToRegion;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Region getBelongsToRegion() {
		return belongsToRegion;
	}
	public void setBelongsToRegion(Region belongsToRegion) {
		this.belongsToRegion = belongsToRegion;
	}
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		if(belongsToRegion!=null) hashCode = belongsToRegion.hashCode();
		
		hashCode += id.hashCode() + name.hashCode() + age;
		
		return hashCode;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Customer){
			Customer customerObj = (Customer) obj;
			
			if(customerObj.getAge() == this.getAge() && 
					customerObj.getId() == this.getId() &&
					customerObj.getName() == this.getName() &&
					((customerObj.getBelongsToRegion()==null && this.getBelongsToRegion()==null) || 
						(customerObj.getBelongsToRegion()!=null && customerObj.getBelongsToRegion().equals(customerObj)))){
				
				return true;
			}
		}
		return false;
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "{id:"+id + ", name:" + name + ", age:"+ age + ", belongsToRegion:" + belongsToRegion + " }" ;
	}
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	
	
}
