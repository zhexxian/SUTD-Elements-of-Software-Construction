public class PizzaStore {

	// main function added to make the class executable for checking

	public static void main(String[] args) {
		orderPizza ("greek");
	}

	// refactored code: detailed pizza choosing procedure is replaced by the "choose" function

	public static Pizza orderPizza (String type) {
		Pizza pizza;
		pizza = choose(type);
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		return pizza;
	}

	// refactored code: the "choose" function
	public static Pizza choose(String type){
		if (type.equals("cheese")) {
			return new CheesePizza();
		}
		if (type.equals("greek")) {
			return new GreekPizza();
		}
		if (type.equals("pepperoni")) {
			return new PepperoniPizza();
		}
		else {
			System.out.println("Please enter a valid pizza name");
			return null;
		}
	}
}

// the following code remain unchanged

class Pizza {
	
	public void prepare() {
	}

	public void box() {
	}

	public void cut() {
	}

	public void bake() {
	}
}

class CheesePizza extends Pizza {}
class GreekPizza extends Pizza {}
class PepperoniPizza extends Pizza {}

