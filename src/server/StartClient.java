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
    theClient = new ClientServerSocket("192.168.1.213", 2333);
    theClient.startClient();
    theClient.sendString("Hello to the server!");
    recvdStr = theClient.recvString();
    out.println("Received this message from server: " +
    recvdStr);
  }
}