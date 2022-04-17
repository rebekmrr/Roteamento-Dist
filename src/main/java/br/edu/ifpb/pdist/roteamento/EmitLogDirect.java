package br.edu.ifpb.pdist.roteamento;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLogDirect {

  private static final String EXCHANGE_NAME = "direct_logs";

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel()) {
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        String arg[] = new String[2];
        arg[0] = "";
        arg[1] = "Rebeka Moreira do Nascimento";
        
        String severity = getSeverity(arg);
        String message = getMessage(arg);

        channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + severity + "':'" + message + "'");
    }
  }
  
  private static String getSeverity(String[] strings) {
      if (strings.length < 1)
          return "info";
      return strings[0];
  }

  private static String getMessage(String[] strings) {
      if (strings.length > 2)
          return "Hello World! - Rebeka Moreira do Nascimento";
      return joinStrings(strings, " ", 1);
  }

  private static String joinStrings(String[] strings, String delimiter, int startIndex) {
      int length = strings.length;
      if (length == 0) return "";
      if (length <= startIndex) return "";
      StringBuilder words = new StringBuilder(strings[startIndex]);
      for (int i = startIndex + 1; i < length; i++) {
          words.append(delimiter).append(strings[i]);
      }
      return words.toString();
  }
}