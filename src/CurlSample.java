
public class CurlSample {
	public static void main(String[] args) {

		// 確認用
		System.out.println("元の引数");
		for (String string : args) {
			System.out.print(string + " ");
		}
		System.out.println("\n");

		CurlRequest request = new CurlRequest();
		// 引数チェック
		int maxArgs = args.length; // 引数の数
		// １つずつ引数を調べる
		for (int i = 0; i < maxArgs; i++) {
			// 引数 -o の場合
			if (args[i].equals("-o")) {
				System.out.println("引数は-o");
				System.out.println("ファイル名は " + args[i + 1]);
				i++; // 次の引数はファイル名なので飛ばす
			}

			// 引数が -v の場合
			else if (args[i].equals("-v")) {
				System.out.println("引数は-v");
			}

			// 引数が -d の場合
			else if (args[i].equals("-d")) {
				System.out.println("引数は-d");
				System.out.println("パラメータは " + args[i + 1]);
				i++; // 次はパラメータなので飛ばす
			}

			// 引数が -X の場合
			else if (args[i].equals("-X")) {
				System.out.println("引数は-X");
				System.out.println("次は " + args[i + 1]);
				i++; // 次はPOSTなので飛ばす
			}

			// 全てに当てはまらない時はURLと判断
			else {
				System.out.println("URLは " + args[i]);
				request.requestURL(args[i]);
			}
		}
	}
}
