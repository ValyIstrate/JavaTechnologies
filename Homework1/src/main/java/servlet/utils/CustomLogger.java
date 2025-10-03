package servlet.utils;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLogger {

    public void info(String httpMethod, String clientIp, String userAgent, String language, String parameter) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String messageLog = String.format("%s: %s called by %s using %s in language %s. Parameter used: %s",
                dtf.format(OffsetDateTime.now()), httpMethod, clientIp, userAgent, language, parameter);
        System.out.println(messageLog);
    }
}
