package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fireship {
	public static void log(String message) {
		System.out.println("( "+ new SimpleDateFormat().format(new Date()) + " ) " + message);
	}
}
