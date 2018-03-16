package work.siro.mod.pvpparticles.classes;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.SecureRandom;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;

public class SiroQUtils {
	public static boolean hasUpdate(String modName,String modVersion) {
		try {
			URL u = new URL("https://siro.work/mods/"+modName+"/version.txt");
	        HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
	        SSLContext sslContext = SSLContext.getInstance("SSL");
	        sslContext.init(null,new X509TrustManager[] { new NoCheckTrustManager() },new SecureRandom());
	        connection.setSSLSocketFactory(sslContext.getSocketFactory());
	        connection.setRequestMethod("GET");
	        InputStream is = connection.getInputStream();
            String ver = IOUtils.toString(is, Charset.defaultCharset());
            if(ver != modVersion) {
            	return true;
            }
            return false;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}

	}

}
