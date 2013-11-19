package server;

import static java.lang.System.out;

public class StartServer
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ClientServerSocket theServer;
    String recvdStr;
    theServer = new ClientServerSocket("108.204.30.218", 4000);
    theServer.startServer();
    recvdStr = theServer.recvString();
    out.println("Recevied message from client: " + recvdStr);
    theServer.sendString("Back at ya client");
  }
}