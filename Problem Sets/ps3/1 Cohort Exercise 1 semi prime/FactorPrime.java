import java.math.BigInteger;

public class FactorPrime {
	public static void main(String[] args){
		BigInteger input = new BigInteger("4294967297");
		BigInteger factor = findFactor(input);
		BigInteger anotherFactor = input.divide(factor);
		System.out.println("The prime factors is "+factor+" and "+anotherFactor);
	}

	public static BigInteger findFactor(BigInteger semiPrime){
		BigInteger zero = new BigInteger("0");
		for(BigInteger i = BigInteger.valueOf(2); 
			i.compareTo(semiPrime) <= 0; 
			i=i.add(new BigInteger("1"))){
			if (semiPrime.remainder(i).compareTo(zero) == 0){
				return i;
			}
		}
		return zero;
	}
}