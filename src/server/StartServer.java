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
    theServer = new ClientServerSocket("141.213.74.35", 4444);
    theServer.startServer();
    recvdStr = theServer.recvString();
    out.println("Recevied message from client: " + recvdStr);
    theServer.sendString("Back at ya client");
  }

}
