import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.StringBuilder;

class Challenge2 {
	private static final String token = "";

	public static String request(String page,String method,String postData) throws Exception{
	 	 final String baseURL="https://apiv2.twitcasting.tv/internships/2019/games";

		HttpURLConnection urlConn=null;
		InputStream in=null;
		BufferedReader reader=null;
		try {
			//接続するURLなどを設定し、コネクションを取得
			URL url = new URL(baseURL+page);
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod(method);
			urlConn.setRequestProperty("Authorization","Bearer "+token);

			//POSTのデータ対応
			if (postData!=""){
				urlConn.setDoOutput(true);
				OutputStream out = urlConn.getOutputStream();
				out.write(postData.getBytes("ISO8859_1")); 
			}			//通信してステータスを表示
			urlConn.connect();
			int status=urlConn.getResponseCode();
			if (status == HttpURLConnection.HTTP_OK) {
				in = urlConn.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
				StringBuilder output = new StringBuilder();
				String line;

				return reader.readLine();
			} else {
				return "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (urlConn != null) {
					urlConn.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args)throws Exception{
		String start=request("?level=3","GET","");
		String id=start.substring(7,39);

		String ans1=request("/"+id,"POST","{\"answer\":\"++++++\"}");
		String hint1=ans1.substring(10,16);

		String ans2=request("/"+id,"POST","{\"answer\":\"------\"}");
		String hint2=ans2.substring(10,16);

		String ans3=request("/"+id,"POST","{\"answer\":\"******\"}");
		String hint3=ans3.substring(10,16);

		String answer="";
		for(int i=0;i<6;i++){
			if (hint1.charAt(i)!='?'){
				answer+='+';
			} else if (hint2.charAt(i)!='?'){
				answer+='-';
			} else if (hint3.charAt(i)!='?'){
				answer+='*';
			} else {
				answer+='-';
			}
		}

		String ans4=request("/"+id,"POST","{\"answer\":\""+answer+"\"}");
		System.out.println(ans4);
		String end=request("/"+id,"DELETE","{\"answer\":\"------\"}");


		try {
			PrintWriter pw = new PrintWriter("result.txt");
			pw.print(ans4);

			pw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
