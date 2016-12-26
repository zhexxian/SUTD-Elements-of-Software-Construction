import java.util.ArrayList;

public class VisitorPattern {

	public static void main(String[] args){
		SUTD oneSUTD = new SUTD ();
		
		ArrayList<Employee> employee = oneSUTD.getEmployee();

		//auditing: use the auditing class instead of the cumbersome condition statements

		Auditing visitor = new Auditing();

		// invoke the accept method -> invoke individual visit method for each employee type

		oneSUTD.accept(visitor);	
	}
}

class SUTD {
	private ArrayList<Employee> employee; 
	
	public SUTD () {
		employee = new ArrayList<Employee>(); 
		employee.add(new Professor("Sun Jun", 0));
		employee.add(new AdminStaff("Stacey", 5));
		employee.add(new Student("Allan", 3));		
	}

	// use the accept method to make each employee in SUTD class visitable
	public void accept(IVisitor visitor){
		for (Employee em:employee)
		{
			em.accept(visitor);
		}		
	}
	
	public ArrayList<Employee> getEmployee () {
		return employee;
	}
}


// make employee class abstract so that it can implement the IVisitable interface without
// initiating the accept method
abstract class Employee implements IVisitable {
	 public abstract void accept(IVisitor visitor);
}

// all the subclasses of employee implements IVisitable and initiates visit method
class Professor extends Employee implements IVisitable {
	private String name;
	private int no_of_publications;
	
	public Professor (String name, int no_of_publications) {
		this.name = name;
		this.no_of_publications = no_of_publications;
	}
	
	public String getName () {
		return name;
	}

	public int getNo_of_publications() {
		return no_of_publications;
	}

	public void accept(IVisitor visitor)
	{
		visitor.visit(this);	
	}
}

class AdminStaff extends Employee implements IVisitable {
	private String name;
	private float efficiency;
	
	public AdminStaff (String name, float efficiency) {
		this.name = name;
		this.efficiency = efficiency;
	}
	
	public String getName() {
		return name;
	}

	public float getEfficiency() {
		return efficiency;
	}

	public void accept(IVisitor visitor)
	{
		visitor.visit(this);	
	}
}

class Student extends Employee implements IVisitable {
	private String name;
	private float GPA;
	
	public Student (String name, float GPA) {
		this.name = name;
		this.GPA = GPA;
	}
	
	public String getName() {
		return name;
	}

	public float getGPA() {
		return GPA;
	}

	public void accept(IVisitor visitor)
	{
		visitor.visit(this);	
	}
}


// IVisitor interface for each employee for specific visiting requirements
interface IVisitor {
	public void visit(Professor professor);
	public void visit(AdminStaff adminStaff);
	public void visit(Student student);
}

// IVisitable interface for employees accept visitors
interface IVisitable {
	  public void accept(IVisitor visitor);
}

// definition of visit for each specific type of employee, during the auditing process
class Auditing implements IVisitor {
	public void visit(Professor professor){
		System.out.println("Prof: " + professor.getName() + professor.getNo_of_publications());
	}
	public void visit(AdminStaff adminStaff){
		System.out.println("Prof: " + adminStaff.getName() + adminStaff.getEfficiency());
	}
	public void visit(Student student){
		System.out.println("Prof: " + student.getName() + student.getGPA());
	}

}