public class Helper {
	
	/** 
    * Class constructor.
    */
	private Helper () {}

	/**
	* This method is used to check if a number is prime or not
	* @param x A positive integer number
	* @return boolean True if x is prime; Otherwise, false
	*/
	public static boolean isPrime(int x) {
		// TODO Add your code here
        boolean prime = true;
        for(int i = 2; i <= x/2; ++i)
        {
            // condition for nonprime number
            if(x % i == 0)
            {
                prime = false;
                break;
            }
        }
        return prime;
	}

	/**
	* This method is used to get the largest prime factor 
	* @param x A positive integer number
	* @return int The largest prime factor of x
	*/
	public static int getLargestPrimeFactor(int x) {
    	// TODO Add your code here
		
		int largest = 0;
		if (isPrime(x)) {
			return x;
		}
		else {
			for(int i = 2; i <= x/2; ++i) {
				// A prime that could divide this x
				if (x % i == 0 && isPrime(i)) {
					largest = i;
				}
			}
		}	
		return largest;

    }
}