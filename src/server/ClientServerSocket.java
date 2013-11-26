package server;

import game.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JTextArea;

import static java.lang.System.out;

public class ClientServerSocket {
    private String ipAddr;
    private int portNum, numPlayers;
    private Socket socket[];
	private ServerSocket serverSock;
    private DataOutputStream outData[];
    private DataInputStream inData[];
    
    //Constructor
    public ClientServerSocket(String inIPAddr, int inPortNum) {
        ipAddr = inIPAddr;
        portNum = inPortNum;
        inData = new DataInputStream[4];
        outData = new DataOutputStream[4];
        socket = new Socket[4];
    }
    
    //Init for Client
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
    
    //Init for Server **returns num players
    public int startServer(JTextArea terminal) {
        numPlayers = 4;
        try {
        	serverSock = new ServerSocket(portNum);
            for(int i = 0; i < numPlayers; ++i){
            	out.println("Waiting for client " + i + " to connect...");
            	terminal.append("Waiting for client " + i + " to connect...\n");
	            socket[i] = serverSock.accept();
	            outData[i] = new DataOutputStream(socket[i].getOutputStream());
	            inData[i] = new DataInputStream(socket[i].getInputStream());
	            out.println("Client " + i + " connection accepted");
	            terminal.append("Client " + i + " connection accepted\n");
	            
	            if(i == 0){
	            	//If first connection ask for number of players
	            	askForNumber(i);
	            	
	            	//Receive number
	            	do{
	            		try{
	            			String recvd = recvString(i);
	            			numPlayers = Integer.parseInt(recvd);
	            			if(numPlayers >= 1 && numPlayers <= 4)
	            				break;
	            		}
	            		catch(NumberFormatException e){}
	            	} while(true);
	            }
	            //Send acknowledgment of join
            	sendAcknowledgement(i);
        	}
        }
        catch (IOException ioe) {
            out.println("ERROR: Caught exception starting server");
            terminal.append("ERROR: Caught exception starting server\n");
            System.exit(7);
        }
        return numPlayers;
    }
    
    //Sends a string to specific client
    public boolean sendString(String strToSend, int client) throws IOException {
        boolean success = false;
        try {
            outData[client].writeBytes(strToSend);
            outData[client].writeByte(0); //send 0 to signal the end of the string
            success = true;
        }
        catch (IOException e) {
            System.out.println("Caught IOException Writing To Socket Stream!");
            throw(e);
        }
        return success;
    }
    
    //Receive string from specific client
    public String recvString(int client) throws IOException {
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
            throw(ioe);
        }
        return receivedString;
    }



    //Server Functions
    //0 - server is happy
    //1 - server wants a number
    //2 - server wants a move
    //3 - server gives update
    //4 - server has ended game
    
    //Tells Client everything is ok
    public boolean sendAcknowledgement(int client) throws IOException{
    	return sendString("0", client);
    }
    
    //Asks client for number of players
    public boolean askForNumber(int client) throws IOException{
    	return sendString("1", client);
    }
       
    //Asks Client to send a move
    public boolean askForMove(int client) throws IOException{
    	return sendString("2", client);
    }
    
    //Tells all Clients a move to execute
    public boolean sendUpdate(String move, int client, JTextArea terminal){
    	for(int i = 0; i < numPlayers; ++i){
    		if(i != client){
    			try{
    				sendString("3 " + move, i);
    			}
    			catch(Exception e){
    				System.out.println("Player " + i + " left");
    				terminal.append("Player " + i + " left\n");
    			}
    		}
    			
    	}
    	return true;
    }
    
    //Tells a client its color
    public boolean sendPlayerInfoToClient(Player p, int client) throws IOException{
    	String s = "4 " + p.getColor();
    	return sendString(s, client);
    }
    
    public boolean sendAllPlayersToClient(Player p[], int client) throws IOException{
    	String s = "5 " + numPlayers + " ";
    	for(int i = 0; i < numPlayers; i++){
    		s += p[i].getName() + " ";
    		s += p[i].getColor() + " ";
    	}
    	return sendString(s, client);
    }
    
    //Tells all Clients to end game and says winner
    public boolean sendEndGame(String name){
    	for(int i = 0; i < numPlayers; ++i){
    		try{
    			sendString("6 " + name, i);
    		}
    		catch(Exception e){}
    		closeConnection(i);
    	}
    	return true;
    }
    
    //Gets player login information from client
    public String getPlayerName(int client) throws IOException{
    	return recvString(client);
    }
    
    //Get move
    public String getMove(int client) throws IOException{
    	return recvString(client);
    }

    
    //Shared function that parses a move
    public String[] parseMove(String move){
    	String result[] = new String[5];
    	Scanner s = new Scanner(move);
    	try{
    		for(int i = 0; i < 5; ++i)
    			result[i] = s.next();
    	}
    	catch(Exception e){
    		s.close();
    		return null;
    	}
    	s.close();
    	return result;
    }
    
    
    //Client Functions
    //Send move to server
    public boolean sendMove(String move) throws IOException{
    	return sendString(move, 0);
    }
    
    //Send request to make player
    public boolean sendName(String name) throws IOException{
    	return sendString(name, 0);
    }
    
    //Gets response from server
    public String getResponse() throws IOException{
    	return recvString(0);
    }
    
    public void closeConnection(int i){
    	try{
    		socket[i].close();
    	}
    	catch(Exception e){}
    }
}