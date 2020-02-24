import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Reader {
	public Reader() throws Exception {
		URL url = new URL("https://kenpom.com/index.php");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuffer reader = new StringBuffer();
		String line, data;
		while ((line = rd.readLine()) != null) {
			reader.append(line);
		}
		data = reader.toString();
		String[] teamStrs = data.split("<tr>");
		for (String teamStr : teamStrs) {
			if (teamStr.indexOf("team.php") != -1) {
				
			}
		}
	}
}