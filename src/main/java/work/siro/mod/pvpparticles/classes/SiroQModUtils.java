package work.siro.mod.pvpparticles.classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.Timer;
import java.util.TimerTask;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509TrustManager;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class SiroQModUtils {
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

	public static void noticeInfo(final String modName) {
		try {
			URL u = new URL("https://siro.work/mods/"+modName+"/notice.txt");
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
            }
            new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					 if(Minecraft.getMinecraft().thePlayer != null) {
			            	if(!result.toString().equals("none")) {
			            		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("§e[§cPvPParticles§e] §a"+result.toString()));
			            	}
					 }
				}

            }, 5000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String removeColorCode(final String text) {
		return text.replaceAll("§0", "").replaceAll("§1", "").replaceAll("§2", "").replaceAll("§3", "").replaceAll("§4", "").replaceAll("§5", "").replaceAll("§6", "").replaceAll("§7", "").replaceAll("§8", "").replaceAll("§9", "").replaceAll("§a", "").replaceAll("§b", "").replaceAll("§c", "").replaceAll("§d", "").replaceAll("§e", "").replaceAll("§f", "").replaceAll("§k", "").replaceAll("§l", "").replaceAll("§m", "").replaceAll("§n", "").replaceAll("§o", "").replaceAll("§r", "");
	}

}
