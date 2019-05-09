
public class CurlSample {
	public static void main(String[] args) {
		int total = 0;
		for (String string : args) {
			total += Integer.parseInt(string);
		}
		System.out.println(total);
	}
}
