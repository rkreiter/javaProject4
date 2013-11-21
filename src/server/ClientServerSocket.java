package server;

import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Vector;
import static java.lang.System.out;

public class ClientServerSocket {
    private String ipAddr;
    private int portNum, numPlayers;
    private Socket socket[];
    private DataOutputStream outData[];
    private DataInputStream inData[];
    public ClientServerSocket(String inIPAddr, int inPortNum) {
        ipAddr = inIPAddr;
        portNum = inPortNum;
        inData = new DataInputStream[4];
        outData = new DataOutputStream[4];
        socket = new Socket[4];
    }
    
    public void startClient() {
        try {
            socket[0] = new Socket(ipAddr, portNum);
            outData[0] = new DataOutputStream(socket[0].getOutputStream());
            inData[0] = new DataInputStream(socket[0].getInputStream());
        }
        catch (IOException ioe) {
            out.println("ERROR: Unable to connect - " + "is the server running?");
            System.exit(10);
        }
    }
    
    public int startServer() {
        ServerSocket serverSock;
        numPlayers = 4;
        try {
        	serverSock = new ServerSocket(portNum);
            for(int i = 0; i < numPlayers; ++i){
            	out.println("Waiting for client " + i + " to connect...");
	            socket[i] = serverSock.accept();
	            outData[i] = new DataOutputStream(socket[i].getOutputStream());
	            inData[i] = new DataInputStream(socket[i].getInputStream());
	            out.println("Client " + i + " connection accepted");
	            
	            if(i == 0){
	            	String recvd = recvString(i);
	            	numPlayers = Integer.parseInt(recvd);
	            }
	            System.out.println(numPlayers);
        	}
        }
        catch (IOException ioe) {
            out.println("ERROR: Caught exception starting server");
            System.exit(7);
        }
        
        return numPlayers;
    }
    
    public boolean sendString(String strToSend, int client) {
        boolean success = false;
        try {
            outData[client].writeBytes(strToSend);
            outData[client].writeByte(0); //send 0 to signal the end of the string
            success = true;
        }
        catch (IOException e) {
            System.out.println("Caught IOException Writing To Socket Stream!");
            System.exit(-1);
        }
        return success;
    }
    
    public String recvString(int client) {
        Vector< Byte > byteVec = new Vector< Byte >();
        byte [] byteAry;
        byte recByte;
        String receivedString = "";
        try {
            recByte = inData[client].readByte();
            while (recByte != 0) {
                byteVec.add(recByte);
                recByte = inData[client].readByte();
            }
            byteAry = new byte[byteVec.size()];
            for (int ind = 0; ind < byteVec.size(); ind++) {
                byteAry[ind] = byteVec.elementAt(ind).byteValue();
            }
            receivedString = new String(byteAry);
        }
        catch (IOException ioe) {
            out.println("ERROR: receiving string from socket");
            System.exit(8);
        }
        return receivedString;
    }
}