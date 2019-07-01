import java.net.HttpURLConnection;
import java.net.URL;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;

enum Symbols {
	plus,
	minus,
	mul,
	div
}

class Challenge3 {
	private static final String token = "";

	private static String request(String page,String method,String postData) throws Exception{
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
		Symbols[] symbols=new Symbols[6];
		String answer="";

		String start=request("?level=3","GET","");
		String id=start.substring(7,39);
		int[] n=new int[7];					//設問の数字部分の配列
		for(int i=0;i<7;i++){
			n[i]=Integer.parseInt(start.substring(53+i*4,53+i*4+1));
		}

		int a=Integer.parseInt(start.substring(81,start.length()-2));

		for(int i=0;i<4096;i++){	//4^6=4096回
			for(int j=0;j<6;j++){	//計算は６回。
				switch (i%(int)Math.pow(4,j+1)/(int)Math.pow(4,j)){
					case 0:
						symbols[j]=Symbols.plus;
						break;
					case 1:
						symbols[j]=Symbols.minus;
						break;
					case 2:
						symbols[j]=Symbols.mul;
						break;
					case 3:
						symbols[j]=Symbols.div;
				}
			}
			if (calc(n,symbols)==a){
				for(int k=0;k<6;k++){	//計算は６回。
					switch (i%(int)Math.pow(4,k+1)/(int)Math.pow(4,k)){
						case 0:
							answer+="+";
							break;
						case 1:
							answer+="-";
							break;
						case 2:
							answer+="*";
							break;
						case 3:
							answer+="/";
					}
				}
				String ret=request("/"+id,"POST","{\"answer\":\""+answer+"\"}");

				System.out.println(start);
				System.out.println(a);
				System.out.println(answer);
				System.out.println(ret);
				try {
					PrintWriter pw = new PrintWriter("result.txt");
					pw.print(ret);
					pw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				System.exit(0);		//正常終了
			}
		}
		System.out.println("探索失敗");
		String end=request("/"+id,"DELETE","");
		System.out.println(end);
	}


	//数式を計算する
	private static int calc(int[] in,Symbols[] symbols){
		int[] nums=in.clone();
		int tmp=0;

		//乗算・除算を行う
		for(int i=0;i<6;i++){
			if (symbols[i]==Symbols.mul){
				nums[i-tmp]*=nums[i+1];
				tmp++;
			} else if (symbols[i]==Symbols.div){
				if (nums[i-tmp]%nums[i+1]==0){
					nums[i-tmp]/=nums[i+1];
					tmp++;
				} else {
					return -2100000000;
				}
			} else {
				nums[i+1-tmp]=nums[i+1];
			}
		}

		tmp=0;
		//加減算を行う
		for(int i=0;i<6;i++){
			if (symbols[i]==Symbols.plus){
				nums[0]+=nums[tmp+1];
				tmp++;
			} else if (symbols[i]==Symbols.minus){
				nums[0]-=nums[tmp+1];
				tmp++;
			}
		}
		return nums[0];
	}
}
