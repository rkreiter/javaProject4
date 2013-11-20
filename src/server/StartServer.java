package server;

import static java.lang.System.out;
import java.net.*;

public class StartServer
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    ClientServerSocket theServer;
    String recvdStr;
    InetAddress IP;

    try{
        IP = InetAddress.getLocalHost();
        out.println(IP.getHostAddress());
    }
    catch(SecurityException s){
        out.println("Security Exception Thrown");
        return;
    }
    catch(UnknownHostException u){
        out.println("Unknown Host");
        return;
    }

    theServer = new ClientServerSocket(IP.getHostAddress(), 2323);
    theServer.startServer();
    recvdStr = theServer.recvString();
    out.println("Recevied message from client: " + recvdStr);
    theServer.sendString("Back at ya client");
  }
}
