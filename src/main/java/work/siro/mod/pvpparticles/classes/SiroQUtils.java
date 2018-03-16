package work.siro.mod.pvpparticles.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

public class SiroQUtils {
	public static boolean hasUpdate(final String modName,final String modVersion) {
		try {
			URL u = new URL("https://siro.work/mods/"+modName+"/version.txt");
			final StringBuffer result = new StringBuffer();
			HttpsURLConnection connect = (HttpsURLConnection)u.openConnection();
			connect.setRequestMethod("GET");
			SSLContext sslContext = SSLContext.getInstance("SSL");
	        sslContext.init(null,new X509TrustManager[] { new NoCheckTrustManager() },new SecureRandom());
	        connect.setSSLSocketFactory(sslContext.getSocketFactory());
			connect.connect();
			final int status = connect.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
            	final InputStream in = connect.getInputStream();
                String encoding = connect.getContentEncoding();
                if(encoding == null){
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                while((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
                if(!result.toString().equals(modVersion)) {
                	return true;
                }
            }
            return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
