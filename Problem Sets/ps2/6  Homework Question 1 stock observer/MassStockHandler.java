// The way I approach the mass stock data sending problem is 
// that instead of having the stockgrabber notifying observer on every change in price,
// the observer will ask for specific stock price change.
// In this way, only the data for stock-of-interest will be sent on request.

// This remedy will work based on assumption that each observer is only 
// interested in very few stocks.

import java.util.ArrayList;

class MassStockHandler {
	public static void main(String[] args){
		StockGrabber sg = new StockGrabber();
		StockObserver so = new StockObserver(sg);

		sg.register(so);
		sg.setIBMPrice(1.00);
		sg.setAAPLPrice(2.00);
		sg.setGOOGPrice(3.00);

		so.askPriceChange("aaplPrice",sg);

	}
}

class StockGrabber implements iSubject {
	
	private ArrayList<Observer> observers;
	private double ibmPrice;
	private double aaplPrice;
	private double googPrice;
	
	public StockGrabber(){
		
		observers = new ArrayList<Observer>();
	}
	
	public void register(Observer newObserver) {
		
		observers.add(newObserver);
		
	}

	public void unregister(Observer deleteObserver) {
		
		int observerIndex = observers.indexOf(deleteObserver);
			
		System.out.println("Observer " + (observerIndex+1) + " deleted");
		
		observers.remove(observerIndex);
		
	}

// The original notifyingObserver function is replaced with price getter functions

	// public void notifyObserver() {
		
	// 	for(Observer observer : observers){
			
	// 		observer.update(ibmPrice, aaplPrice, googPrice);
	// 	}
	// }
	
	public void setIBMPrice(double newIBMPrice){
		
		this.ibmPrice = newIBMPrice;
		
		// notifyObserver();
		
	}

	public void setAAPLPrice(double newAAPLPrice){
		
		this.aaplPrice = newAAPLPrice;
		
		// notifyObserver();
		
	}

	public void setGOOGPrice(double newGOOGPrice){
	
		this.googPrice = newGOOGPrice;
	
		// notifyObserver();
	
	}

// price getters

	public double getIBMPrice(){
		
		return this.ibmPrice;
		
	}
	
	public double getAAPLPrice(){
		
		return this.aaplPrice;
		
	}

	public double getGOOGPrice(){
		
		return this.googPrice;
		
	}
	
}

class StockObserver implements Observer {
	
	private double ibmPrice;
	private double aaplPrice;
	private double googPrice;
	
	private static int observerIDTracker = 0;
	
	private int observerID;
	
	private iSubject stockGrabber;
	
	public StockObserver(iSubject stockGrabber){
			
		this.stockGrabber = stockGrabber;
	
		this.observerID = ++observerIDTracker;
			
		System.out.println("New Observer " + this.observerID);
		
		stockGrabber.register(this);
		
	}

// A new askPriceChange function is implemented to ask for specific stock price change;
// new stocks can be added to the conditional statements

	public void askPriceChange(String specificStock, StockGrabber stockGrabber){
		if (specificStock=="ibmPrice"){
			this.ibmPrice = stockGrabber.getIBMPrice();
			System.out.println("new "+specificStock+" price is: "+this.ibmPrice);
		}
		if (specificStock=="aaplPrice"){
			this.aaplPrice = stockGrabber.getAAPLPrice();
			System.out.println("new "+specificStock+" price is: "+this.aaplPrice);
		}
		if (specificStock=="googPrice"){
			this.googPrice = stockGrabber.getGOOGPrice();
			System.out.println("new "+specificStock+" price is: "+this.googPrice);
		}
	}

// The original indiscriminate update function is replaced with the askPriceChange 
// function above.
	
	// public void update(double ibmPrice, double aaplPrice, double googPrice) {
		
	// 	this.ibmPrice = ibmPrice;
	// 	this.aaplPrice = aaplPrice;
	// 	this.googPrice = googPrice;
		
	// 	printThePrices();
		
	// }

	// change this
	
	public void printThePrices(){
		
		System.out.println(observerID + "\nIBM: " + ibmPrice + "\nAAPL: " + 
				aaplPrice + "\nGOOG: " + googPrice + "\n");
		
	}
	
}


interface iSubject {
	public void register(Observer o);
	public void unregister(Observer o);
	//public void notifyObserver();
}

interface Observer {
	//public void update(double ibmPrice, double aaplPrice, double googPrice);
}
