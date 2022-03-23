package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT extends Thread {
	private int nbrclient;
	private boolean isActive = true;
	public static void main (String[] args )  {
		new ServeurMT().start();
	



		
		
	}
          public void run() {
      		try {
				ServerSocket ss = new ServerSocket(1234);
				System.out.println("Démarrage du serveur");
				while(isActive) {
					Socket socket = ss.accept();
					++nbrclient;
					new Conversation(socket,nbrclient).start();
					
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}


          }
        
          class Conversation extends Thread{
        	  private Socket socket;
        	  private int nbrclient;
        	  public Conversation (Socket s, int num) {
        		  this.socket=s;
        		  this.nbrclient=num;
        	  }
        	public void run() {
        		try {
					InputStream is = socket.getInputStream();
					InputStreamReader isr = new InputStreamReader(is);
					BufferedReader br = new BufferedReader (isr);
					OutputStream os = socket.getOutputStream();
					PrintWriter pw = new PrintWriter(os,true);
					String IP = socket.getRemoteSocketAddress().toString();
					System.out.println("Connexio du client numero "+nbrclient+" ip: "+IP);
					pw.println("Bienvenue vous etes le client numero /t "+nbrclient);
					while (true) {
						String req = br.readLine();
						String reponse ="Lenghth="+ req.length();
						pw.println(reponse);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
          }
          }
