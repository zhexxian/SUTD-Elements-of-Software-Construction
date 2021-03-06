public class SynchronizedAccount {
	public static void main(String args[]){
		SynchronizedAccount newAccount = new SynchronizedAccount();   	
		SynchronizedAccountThread a = new SynchronizedAccountThread(newAccount);
		SynchronizedAccountThread b = new SynchronizedAccountThread(newAccount);

		a.start();
		b.start();
		try {
			a.join();
			b.join();
		} catch (InterruptedException e) {
			System.out.println("some thread is not finished");
		}	
	}

	//initial balance
	int balance = 0;

	//deposite
	public synchronized void deposite(int amount){
		balance+=amount;
		System.out.println(String.valueOf(amount)+" dollars deposited");
	}
	//withdraw
	public synchronized boolean withdraw(int amount){
		if(amount<=balance){
			balance-=amount;
			System.out.println(String.valueOf(amount)+" dollars withdrawn");
			return true;
		}
		return false;
	}
	//checkBalance
	public void checkBalance(){
		System.out.println("balance: "+String.valueOf(balance)+" dollars");
	}
}

class SynchronizedAccountThread extends Thread{

	SynchronizedAccount account;

	SynchronizedAccountThread(SynchronizedAccount account){
		this.account = account;
	}

	@Override

	// test run
	public void run(){
		synchronized(account){
			account.checkBalance();
			account.deposite(200);
			account.checkBalance();
			account.withdraw(150);
			account.checkBalance();
		}
	}

}