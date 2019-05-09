import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class CurlRequest {
	// 指定されたURLにアクセスする
	public void requestURL(String urlString) {
		HttpURLConnection urlConnection = null;
		int responseCode = 0;
		String responseData = "";
		try {
			// ステップ1:接続URLを決める。
			URL url;
			url = new URL(urlString);
			// ステップ2:URLへのコネクションを取得する。
			urlConnection = (HttpURLConnection) url.openConnection();
			// ステップ3:接続設定（メソッドの決定,タイムアウト値,ヘッダー値等）を行う。
			// 接続タイムアウトを設定する。
			urlConnection.setConnectTimeout(100000);
			// レスポンスデータ読み取りタイムアウトを設定する。
			urlConnection.setReadTimeout(100000);
			// ヘッダーにUser-Agentを設定する。
			urlConnection.setRequestProperty("User-Agent", "Android");
			// ヘッダーにAccept-Languageを設定する。
			urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().toString());
			// HTTPのメソッドをGETに設定する。
			urlConnection.setRequestMethod("GET");
			// リクエストのボディ送信を許可しない
			urlConnection.setDoOutput(false);
			// レスポンスのボディ受信を許可する
			urlConnection.setDoInput(true);
			// ステップ4:コネクションを開く
			urlConnection.connect();
			// ステップ6:レスポンスボディの読み出しを行う。
			responseCode = urlConnection.getResponseCode();
			responseData = convertToString(urlConnection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				// ステップ7:コネクションを閉じる。
				urlConnection.disconnect();
			}
		}
		System.out.println("URL:" + urlString);
		System.out.println("httpStatusCode:" + responseCode);
		System.out.println("responseData" + responseData);
		// return responseData;
	}

	public String convertToString(InputStream stream) throws IOException {
		StringBuffer sb = new StringBuffer();
		String line = "";
		BufferedReader br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		try {
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();

		/*
		 * URL url = null; // URLを入れる 初期化が必要 // URLの文字列をurlに代入する try { url = new
		 * URL(urlString.toString()); } catch (java.net.MalformedURLException e) {
		 * e.printStackTrace(); } HttpURLConnection urlConnection = null; //
		 * URLのコネクションの取得 try { urlConnection = (HttpURLConnection) url.openConnection();
		 * } catch (IOException e) { e.printStackTrace(); } // 接続タイムアウトを設定する。
		 * urlConnection.setConnectTimeout(100000); // レスポンスデータ読み取りタイムアウトを設定する。
		 * urlConnection.setReadTimeout(100000); // ヘッダーにUser-Agentを設定する。
		 * urlConnection.addRequestProperty("User-Agent", "Android"); //
		 * ヘッダーにAccept-Languageを設定する。
		 * urlConnection.addRequestProperty("Accept-Language",
		 * Locale.getDefault().toString()); // HTTPのメソッドをGETに設定する。 try {
		 * urlConnection.setRequestMethod("GET"); } catch (ProtocolException e) {
		 * e.printStackTrace(); } // リクエストのボディ送信を許可しない urlConnection.setDoOutput(false);
		 * // レスポンスのボディ受信を許可をする urlConnection.setDoInput(true); // コネクションを開く try {
		 * urlConnection.connect(); } catch (IOException e) { // TODO 自動生成された catch ブロック
		 * e.printStackTrace(); }
		 *
		 * // レスポンスボディの読み出しを行う。 try { int statusCode = urlConnection.getResponseCode();
		 * } catch (IOException e1) { // TODO 自動生成された catch ブロック e1.printStackTrace(); }
		 *
		 * String responseData = ""; InputStream stream = null; try { stream =
		 * urlConnection.getInputStream(); } catch (IOException e1) { // TODO 自動生成された
		 * catch ブロック e1.printStackTrace(); } StringBuffer sb = new StringBuffer();
		 * String line = ""; BufferedReader br = null; try { br = new BufferedReader(new
		 * InputStreamReader(stream, "UTF-8")); } catch (UnsupportedEncodingException
		 * e1) { // TODO 自動生成された catch ブロック e1.printStackTrace(); } try { while ((line =
		 * br.readLine()) != null) { sb.append(line); } } catch (IOException e1) { //
		 * TODO 自動生成された catch ブロック e1.printStackTrace(); } try { stream.close(); } catch
		 * (Exception e) { e.printStackTrace(); } responseData = sb.toString();
		 *
		 * // ステップ7:コネクションを閉じる。 urlConnection.disconnect();
		 */
	}
}
