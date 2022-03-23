package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServeurChat extends Thread {
	private int nbrclient=0;
	private boolean isActive = true;
	private List <Conversation> clients = new ArrayList<Conversation>();
	public static void main (String[] args )  {
		new ServeurChat().start();
	



		
		
	}
          public void run() {
      		try {
				ServerSocket ss = new ServerSocket(1234);
				System.out.println("Démarrage du serveur");
				while(isActive) {
					Socket socket = ss.accept();
					++nbrclient;
					Conversation conversation =new Conversation(socket,nbrclient);
					clients.add(conversation);
					conversation.start();
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}


          }
        
          class Conversation extends Thread{
        	  private Socket socketClient;
        	  private int nbrclient;
        	  public Conversation (Socket socketClient, int num) {
        		  this.socketClient=socketClient;
        		  this.nbrclient=num;
        	  }
        	  public void broadcastMessage(String message,Socket socket,int numClient) {
        			  try {
                		  for (Conversation client:clients) {

        				  if(client.socketClient!=socket) {
        					  if(client.nbrclient==numClient ||numClient==-1 ) {
        						  PrintWriter printWriter = new PrintWriter(client.socketClient.getOutputStream(),true);
            					    printWriter.println(message);
        					  }
        					 
        				  }
                		  }
        			  } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		  
        	  }
        	public void run() {
        		try {
					InputStream is = socketClient.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader (isr);
					OutputStream os = socketClient.getOutputStream();
					PrintWriter pw = new PrintWriter(os,true);
					String IP = socketClient.getRemoteSocketAddress().toString();
					System.out.println("Connexio du client numero "+nbrclient+" ip: "+IP);
					pw.println("Bienvenue vous etes le client numero "+nbrclient);
					while (true) {
						String req = br.readLine();
						if (req.contains("=>")) {
							String[] requestParams = req.split("=>");
							if (requestParams.length==2);
							String message= requestParams[1];
							int nbrclient = Integer.parseInt(requestParams[0]);
							broadcastMessage(message,socketClient,nbrclient);
						}
						else {
							broadcastMessage(req,socketClient,-1);

						}
						
						
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
          }
          }
