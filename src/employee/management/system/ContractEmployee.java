package employee.management.system;

import employee.management.system.EMSEnums.Department;
import employee.management.system.EMSEnums.Position;
import employee.management.system.EMSEnums.Contract;

public class ContractEmployee extends Employee{


	private static final long serialVersionUID = 1L;
	
	private Contract contractType;

	public ContractEmployee(int id, String fName, String lName, String contact, Department department, double salary,
			Position position, Contract type) {
		super(id, fName, lName, contact, department, salary, position);
		this.contractType = type;
		
	}

	public Contract getContractType() {
		return contractType;
	}

	public void setContractType(Contract contractType) {
		this.contractType = contractType;
	}

	@Override
	public String toString() {
		return super.toString() + "Contract Employee ---> Type=" + contractType +"\n";
	}

}
