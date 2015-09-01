package rt.main.utils;

public class Smart {
	public static int mod(float x, float y){

		int r = (int)x % (int)y;
		if (r < 0)
		{
		    r += (int)y;
		}
		
		return r;
	}
}
