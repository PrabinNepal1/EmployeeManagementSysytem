package employee.management.system;

import java.io.Serializable;

import employee.management.system.EMSEnums.Department;
import employee.management.system.EMSEnums.Position;

public class Employee implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private int id;
	private String firstName;
	private String lastName;
	private String contact;
	private Department department;
	private double salary;
	private Position position;
	
	
	public Employee(int id, String fName, String lName, String contact, Department department, double salary, Position position) {
		this.id = id;
		this.firstName = fName;
		this.lastName = lName;
		this.contact = contact;
		this.department = department;
		this.salary = salary;
		this.position = position;
	}
	
	//GET METHODS
	
	public int getID() {
		return this.id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	

	public String getContact() {
		return this.contact;
	}
	
	public Department getDepartment() {
		return department;
	}

	public double getSalaray() {
		return this.salary;
	}
	
	public Position getPosition() {
		return this.position;
	}
	
	//SET METHODS

	public void setID(int id) {
		 this.id = id;
	}
	
	public void setFirstName(String name) {
		this.firstName = name;
	}
	
	public void setLastName(String name) {
		this.lastName = name;
	}
	

	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public void setSalaray(double pay) {
		this.salary = pay;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}	

	@Override
	public String toString() {
		return "<=== Employee Detail for Id: " + id + " ===>\n First Name=" + firstName + ", Last Name= " + lastName + ", Contact=" 
						+ contact + " Department = " + department + ", salary=" + salary + ", position="
				+ position + "\n";
	}

	
}
