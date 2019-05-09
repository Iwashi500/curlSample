
public class CurlSample {
	public static void main(String[] args) {

		// 確認用
		System.out.println("元の引数");
		for (String string : args) {
			System.out.print(string + " ");
		}
		System.out.println("\n");

		// 引数チェック
		for (String string : args) {
			if (string.equals("-o")) {
				System.out.println("OK!");
			}
		}

	}
}
