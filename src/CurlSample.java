
public class CurlSample {
	public static void main(String[] args) {
		CurlRequest request = new CurlRequest();
		// 引数チェック
		int maxArgs = args.length; // 引数の数
		// １つずつ引数を調べる
		for (int i = 0; i < maxArgs; i++) {
			// 引数 -o の場合
			if (args[i].equals("-o")) {
				request.setFile(args[i + 1]); // ファイルの作成フラグをセット
				i++; // 次の引数はファイル名なので飛ばす
			}

			// 引数が -v の場合
			else if (args[i].equals("-v")) {
				request.setHeader(); // ヘッダー受け取りフラグをセット
			}

			// 引数が -d の場合
			else if (args[i].equals("-d")) {
				request.setPara(args[i + 1]); // パラメータをセット
				i++; // 次はパラメータなので飛ばす
			}

			// 引数が -X の場合
			else if (args[i].equals("-X")) {
				request.setPost(); // ポスト要求フラグをセット
				i++; // 次はPOSTなので飛ばす
			}

			// 全てに当てはまらない時はURLと判断
			else {
				request.setURL(args[i]);
			}
		}
		// URLにアクセス
		request.requestURL();
	}
}
