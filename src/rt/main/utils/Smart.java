package rt.main.utils;

/**
 * This class is used for math.
 * 
 * @author Sebastian Robert Karlsson
 */
public class Smart {
	
	/**
	 * This function is basically modulo but it will always return a positive result.
	 * 
	 * @param x the value we want to perform modulo on.
	 * @param y the modulo value.
	 * @return the positive product from x % y as an Integer. 
	 */
	public static int mod(float x, float y){
		int r = (int)x % (int)y;
		if (r < 0)
		{
		    r += (int)y;
		}
		
		return r;
	}
}
