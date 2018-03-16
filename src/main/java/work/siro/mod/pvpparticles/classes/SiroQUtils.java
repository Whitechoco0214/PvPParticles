package work.siro.mod.pvpparticles.classes;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

public class SiroQUtils {
	public static boolean hasUpdate(String modName,String modVersion) {
		try {
			URL u = new URL("https://siro.work/mods/"+modName+"/version.txt");
	        HttpURLConnection connection = (HttpURLConnection) u.openConnection();
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
