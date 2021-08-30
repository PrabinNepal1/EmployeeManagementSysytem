package employee.management.system;


public class EmployeeManagementException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public EmployeeManagementException() {
		super("Please enter the valid department name!");
	}
	
	public EmployeeManagementException(String pos) {
		super("Please enter the valid position name!");
	}
}
