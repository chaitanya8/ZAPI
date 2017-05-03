package zapi.Utils;

import org.apache.commons.codec.binary.Base64;

import java.util.logging.Logger;

/**
 * Created by Chaitanya on 31-May-15.
 */
public class ConnectionUtils {
    static Logger logger = Logger.getLogger(ConnectionUtils.class.getName());

    public static String getEncodedAuthKey() {
        String username = PropertiesParser.getUsernameFromTerminal();
        String password = PropertiesParser.getPasswordFromTerminal();
//        String username = PropertiesParser.getUsername();
//        String password = PropertiesParser.getPassword();
        String combo = username + ":" + password;
        byte[] base64EncodedUsername = Base64.encodeBase64(combo.getBytes());
        String temp = new String(base64EncodedUsername);
        String encodedUsername = "Basic " + temp;
//        logger.info("Authorization : " + encodedUsername);
        return encodedUsername;
    }
}
