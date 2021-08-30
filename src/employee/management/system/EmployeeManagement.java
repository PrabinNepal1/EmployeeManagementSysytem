package employee.management.system;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


import employee.management.system.EMSEnums.Department;
import employee.management.system.EMSEnums.Position;
import employee.management.system.EMSEnums.Contract;

public class EmployeeManagement {

	public static void main(String[] args) {
		
		File rawFile = new File("resources/employee.txt");
		File objectFile = new File("resources/employeeRecord.txt");
		
		FileReader fileReader = null;
		BufferedReader reader = null;
		List <Employee> employeeList = new ArrayList<Employee>();
		Employee employeeDetail = null;
		Scanner operationChoice = new Scanner(System.in);
		
		if(objectFile.exists()) {
			employeeList = readEmployeeFromFile(objectFile);
		}
		else {
				try {
					
					fileReader = new FileReader(rawFile);
					reader = new BufferedReader(fileReader);
				
					
					String line;
					
					while ((line = reader.readLine()) != null) {
						
						String [] employeeDetails = line.split(" ");
						
						if (employeeDetails.length == 7) {
							employeeDetail = new Employee(Integer.parseInt(employeeDetails[0]), employeeDetails[1], employeeDetails[2], employeeDetails[3], Department.valueOf(employeeDetails[4]), Double.parseDouble(employeeDetails[5]), Position.valueOf(employeeDetails[6]));
							
						}
						
						if (employeeDetails.length == 8) {
							employeeDetail = new ContractEmployee(Integer.parseInt(employeeDetails[0]), employeeDetails[1], employeeDetails[2], employeeDetails[3], Department.valueOf(employeeDetails[4]), Double.parseDouble(employeeDetails[5]), Position.valueOf(employeeDetails[6]), Contract.valueOf(employeeDetails[7]));
							
						}
						
						employeeList.add(employeeDetail);
					}
					
				} catch(FileNotFoundException e) {
					System.out.println("eXCEPTION: Could not find file: " + rawFile.getAbsolutePath());
				} catch(IOException e){
					System.out.println("Exception: Could not read characters!!");
				} finally {
					try {
						fileReader.close();
						reader.close();
					} catch (IOException e) {
						System.out.println("Could not close reader objects");
					}
				}
		}
		
		do {
			System.out.println("\n<=== Welcome to the Employee Management System ===>\n");
			System.out.println("1). Display all Employees working in this company\n" +
								"2). Add new Employee\n" +
								"3). Search for Employee By Department\n" +
								"4). Edit Employee details\n" +
								"5). Delete Employee Details\n" +
								"6). Save Employees List to the File\n" +
								"7). EXIT\n");
			System.out.println("Enter your choice : ");
			int choice = operationChoice.nextInt();
			
			switch(choice) {
			case 1:
				System.out.println("<=== Displaying All Employee List ===>\n");
				display(employeeList);
				break;
			case 2:try {	
			
					System.out.println("\nEnter the required details without any whitespace to ADD new Employee:\n");
					System.out.println("Enter ID :");
					int id = operationChoice.nextInt();
					System.out.println("Enter FirstName :");
					String firstName =operationChoice.next();
					System.out.println("Enter LastName :");
					String lastName =operationChoice.next();
					System.out.println("Enter contact (xxx-xxx-xxxx):");
					String contact =operationChoice.next();
					System.out.println("Enter Department(INFOSECURITY, INFOTECH, FINANCECENTER):");
					Department department = Department.valueOf(operationChoice.next().toUpperCase());
					System.out.println("Enter Salary :");
					double salary = operationChoice.nextDouble();
					System.out.println("Enter Position (ENTRY_LEVEL/MID_SENIOR/SENIOR) :");
					Position position = Position.valueOf(operationChoice.next().toUpperCase());
					
					System.out.println("Is this employee being hired on Contract?(Y/N)");
					char contractChoice = operationChoice.next().charAt(0);
					
					if (contractChoice == 'Y' || contractChoice == 'y') {
						System.out.println("Enter Contract Type (ANNUAL/SEMI_ANNUAL) :");
						Contract contractType = Contract.valueOf(operationChoice.next().toUpperCase());
						
						employeeList.add(new ContractEmployee(id, firstName, lastName, contact, department, salary, position, contractType));
					}else {
						employeeList.add(new Employee(id, firstName, lastName, contact, department, salary, position));
					}
		
					
					System.out.println("Successfully Added Employee in the EMS!!");
				}catch(IllegalArgumentException e) {
					System.err.println("Illegal Argument Exception!");
				}
				break;
			case 3:
				System.out.println("Enter the department to display all employee:");
				String searchTerm = operationChoice.next().toUpperCase();
				try {
					departmentIsValid(searchTerm);
					for(Employee byDepartment : employeeList) {
						if(Department.valueOf(searchTerm) == byDepartment.getDepartment())
							System.out.println(byDepartment);
						}
				}catch( EmployeeManagementException e) {
					System.out.println(e.getLocalizedMessage());
		
				}
				break;
			case 4:
				System.out.println("Enter the employee id to edit employee details");
				int employeeId = operationChoice.nextInt();
				int editBreaker = 0;
				for(Employee employee : employeeList) {
					if(employeeId == employee.getID()) {
						do {
						int editChoice=0;
							System.out.println("\nEDIT Employee Details :\n" +
												"1). Employee ID\n" +
												"2). First Name\n" +
												"3). Last Name\n" +
												"4). Contact\n" +
												"5). Department\n" +
												"6). Salary\n" +
												"7). Position\n" +
												"8). End Edit\n");
							System.out.println("Enter your choice : ");
							editChoice = operationChoice.nextInt();
							
							switch(editChoice){
							case 1:
								System.out.println("\nEnter new Employee ID:");
								employee.setID(operationChoice.nextInt());
								break;
							case 2:
								System.out.println("\nEnter New First Name: ");
								employee.setFirstName(operationChoice.next());
								break;
							case 3:
								System.out.println("\nEnter New Last Name: ");
								employee.setLastName(operationChoice.next());
								break;
							case 4:
								System.out.println("\nEnter New Contact (XXX-XXX-XXXX): ");
								employee.setContact(operationChoice.next());
								break;
							case 5:
								System.out.println("\nEnter New Department: ");
								String department = operationChoice.next().toUpperCase();
								try {
									departmentIsValid(department);
									employee.setDepartment(Department.valueOf(department));
								}catch( EmployeeManagementException e) {
									System.out.println(e.getLocalizedMessage());
								}
								break;
							case 6:
								System.out.println("Enter New Salary: ");
								employee.setSalaray(operationChoice.nextDouble());
								break;
							case 7:
								System.out.println("\nEnter New Position: ");
								String position = operationChoice.next().toUpperCase();
								try {
									positionIsValid(position);
									employee.setPosition(Position.valueOf(position));
								}catch( EmployeeManagementException e) {
									System.out.println(e.getLocalizedMessage());
								}
								break;
							case 8:
								editBreaker++;
								break;
								
							default : System.out.println("\nEnter a correct choice from the List :");
										break;
							}
						}while(editBreaker==1);
					}
				}
			case 5:
				System.out.println("Enter employee id to delete the employee: ");
				int id = operationChoice.nextInt();
				
				employeeList = employeeList.stream().filter(employee -> (id != employee.getID())).collect(Collectors.toList());
				
				System.out.println("Successfully Deleted Employee with ID: " + id);
				display(employeeList);
				break;
		    case 6: 
		    	System.out.println("Saving Employee List to the File employeeRecord.txt");
		    	writeEmployeeToFile(objectFile, employeeList);
		    case 7:
		    	System.out.println("\nYou have chosen EXIT !! Saving the changes and Closing the EMS tool.");
		    	writeEmployeeToFile(objectFile, employeeList);
				operationChoice.close();
				System.exit(0);
				break;
		    default : 
		    	System.out.println("\nEnter a correct choice from the List :");
				break;
		}
		}while(true);
	
	}
	
	//HELPER METHODS
	
	public static void display(List <Employee> employeeList) {
		for(Employee employee : employeeList ) {
			System.out.println(employee);				
		}
		
	}
	
	public static boolean departmentIsValid(String depString) throws EmployeeManagementException {
		
		for(Department dep : Department.values() )
			if (dep.name().equals(depString)) {
				return true;
	        }
		
		throw new EmployeeManagementException();
	}
	
	public static boolean positionIsValid(String posString) throws EmployeeManagementException {
		
		for(Position pos : Position.values() )
			if (pos.name().equals(posString)) {
				return true;
	        }
		
		throw new EmployeeManagementException(posString);
	}
	
	
	public static void writeEmployeeToFile(File file, List<Employee> employee) {
		 try(ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(file))) {
			 
	            writer.writeObject(employee);
	            
	        }catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	@SuppressWarnings("unchecked")
	public static List<Employee> readEmployeeFromFile(File file) {
		
		 List <Employee> employeeList = new ArrayList<Employee>(); 
		
		try(ObjectInputStream reader = new ObjectInputStream(new FileInputStream(file))) {
			 
			employeeList = (List <Employee>)reader.readObject();
	            
	        }catch (IOException e) {
				e.printStackTrace();
			}catch(ClassNotFoundException e){
				e.printStackTrace();
			}
		
		return employeeList;
		}

}
