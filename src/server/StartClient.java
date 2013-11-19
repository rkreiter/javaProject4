package server;

import static java.lang.System.out;

public class StartClient
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ClientServerSocket theClient;
    String recvdStr;
    theClient = new ClientServerSocket("108.204.30.218", 4444);
    theClient.startClient();
    theClient.sendString("Hello to the server!");
    recvdStr = theClient.recvString();
    out.println("Received this message from server: " +
    recvdStr);
  }

}