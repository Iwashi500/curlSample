import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class CurlRequest {
	private String urlString = null; // アクセスするURL
	private boolean urlFlag = false; // URLセットフラグ
	private boolean fileFlag = false; // ファイル作成フラグ
	private String fileName = null; // 作成するファイルの名前
	private boolean headerFlag = false; // ヘッダー受け取りフラグ
	private boolean postFlag = false; // ポスト要求フラグ
	private boolean paraFlag = false; // パラメータフラグ
	private String paraString = null; // パラメータ内容

	// ファイル作成フラグとファイル名をセット
	public void setFile(String name) {
		if (fileName == null) {
			this.fileFlag = true;
			this.fileName = name;
		} else {
			// エラー
			System.out.println("ファイル名が多いです");
			System.exit(0); // 終了
		}
	}

	// ヘッダー受け取りフラグをセット
	public void setHeader() {
		this.headerFlag = true;
	}

	// ポスト要求フラグをセット
	public void setPost() {
		this.postFlag = true;
	}

	// パラメータフラグとパラメータ内容をセット
	public void setPara(String string) {
		if (paraString == null) {
			this.paraFlag = true;
			this.paraString = string;
		} else {
			// エラー
			System.out.println("パラメータが多いです");
			System.exit(0); // 終了
		}
	}

	// URLとURLフラグをセット
	public void setURL(String url) {
		if (urlString == null) {
			this.urlFlag = true; // URLフラグをセット
			this.urlString = url; // URLをセット
		} else {
			// エラー
			System.out.println("URLが多いです");
			System.exit(0);// 終了
		}
	}

	// 指定されたURLにアクセスする
	public void requestURL() {
		// URLがセットされていなければ終了
		if (!urlFlag) {
			System.out.println("URLがありません");
			System.exit(0); // 終了
		}

		HttpURLConnection urlConnection = null;
		int responseCode = 0;
		String responseData = "";
		try {
			// 接続URLを決める。
			URL url;
			url = new URL(urlString);
			// URLへのコネクションを取得する。
			urlConnection = (HttpURLConnection) url.openConnection();
			// 接続設定（メソッドの決定,タイムアウト値,ヘッダー値等）を行う。
			// 接続タイムアウトを設定する。
			urlConnection.setConnectTimeout(100000);
			// レスポンスデータ読み取りタイムアウトを設定する。
			urlConnection.setReadTimeout(100000);
			// ヘッダーにUser-Agentを設定する。
			urlConnection.setRequestProperty("User-Agent", "jp");
			// ヘッダーにAccept-Languageを設定する。
			urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
			// リクエストのボディ送信を許可する
			urlConnection.setDoOutput(true);
			// レスポンスのボディ受信を許可する
			urlConnection.setDoInput(true);

			// ポストを送る時
			if (this.postFlag) {
				// POST受け取り
				urlConnection.setRequestMethod("POST");
				urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;");
				OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
				// パラメータも送る時
				if (this.paraFlag)
					out.write(paraString);
				out.close();
			} else {
				// HTTPのメソッドをGETに設定する。
				urlConnection.setRequestMethod("GET");
			}

			// コネクションを開く
			urlConnection.connect();
			// レスポンスボディの読み出しを行う。
			responseCode = urlConnection.getResponseCode();
			responseData = convertToString(urlConnection.getInputStream());

			// ヘッダ追加
			if (this.headerFlag) {
				Map headers = urlConnection.getHeaderFields();
				Iterator headerIt = headers.keySet().iterator();
				String header = null;
				while (headerIt.hasNext()) {
					String headerKey = (String) headerIt.next();
					header += headerKey + "：" + headers.get(headerKey) + "\r\n";
				}
				// 追加
				responseData = addHeader(responseData, header);
			}

			// ファイル作成
			if (fileFlag)
				makeFile(responseData);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				// ステップ7:コネクションを閉じる。
				urlConnection.disconnect();
			}
		}

		// 表示
		System.out.println("URL:" + urlString);
		System.out.println("httpStatusCode:" + responseCode);
		System.out.println("responseData\n" + responseData);
	}

	// ヘッダーをボディの先頭に付け足す
	private String addHeader(String responseData, String header) {
		StringBuilder data = new StringBuilder(responseData);
		data.insert(0, header);
		return data.toString();
	}

	// 受け取った内容をString型に変える
	private String convertToString(InputStream stream) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		try {
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	// ファイルを作成する
	private void makeFile(String data) throws IOException {
		// ファイル場所指定
		FileWriter file = new FileWriter("." + this.fileName);
		// ファイルに書き込むよう
		PrintWriter pw = new PrintWriter(new BufferedWriter(file));
		// ファイルに書き込む
		pw.println(data);
		// ファイルを閉じる
		pw.close();
	}
}
