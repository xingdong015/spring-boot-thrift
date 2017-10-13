package weather.impl;

import org.apache.thrift.TException;
import weather.Weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class GetbaiduWeather implements Weather.Iface {

	public String doWebSanc(String city) {
		try {
			
			String urlString = "http://api.map.baidu.com/telematics/v3/weather?location=" +java.net.URLEncoder.encode(city,"utf-8")+"&output=json&ak=W69oaDTCfuGwzNwmtVvgWfGH";
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.connect();

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			wr.flush();
			wr.close();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				return line;
			}

			rd.close();
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static void main(String[] args) {
		GetbaiduWeather w = new GetbaiduWeather();
		String aaa=w.doWebSanc("上海");
		System.out.println(aaa);
	}

	@Override
	public String getWeather(String city) throws TException {

		return doWebSanc(city);
	}

}
