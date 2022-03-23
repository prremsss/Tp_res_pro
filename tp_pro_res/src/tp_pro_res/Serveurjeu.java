package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Serveurjeu extends Thread {
	private int nbrclient=0;
	private boolean isActive = true;
	private int nombreSecret;
	private boolean fin;
	private String gagnant;
	public static void main (String[] args )  {
		new Serveurjeu().start();
	



		
		
	}
          public void run() {
      		try {
				ServerSocket ss = new ServerSocket(1234);
				System.out.println("Démarrage du serveur");
				nombreSecret = new Random().nextInt(100);
				System.out.println("Nombre secret est  " + nombreSecret);
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
					System.out.println("Connexio du client numero " + nbrclient + " ip: "+IP);
					pw.println("Bienvenue vous etes le client numero"+nbrclient);
					pw.println("Devinez le nombre secret");
					while (true) {
						String req = br.readLine();						
						int nombre=0;
						boolean correctFormatRequest = false;
						try {
							nombre = Integer.parseInt(req);
							correctFormatRequest=true;
						} catch (NumberFormatException e) {
							correctFormatRequest=false;
						}
						if(correctFormatRequest) {
							System.out.println(" Client "+IP+" Tentative avec " + nombre);

							if(fin == false) {
								if(nombre>nombreSecret) {
									pw.println("\nVotre nombre est superieur au nbre secret");
								}
								else if(nombre<nombreSecret) {
									pw.println("\nVotre nombre est inferieur au nbre secret");

								}
								else {
									pw.println("\nBRAVOO!! ");
									gagnant = IP;
									System.out.println("Bravo au gagnant,IP client :" + gagnant);
									fin=true;

								}
							}
							else 
							{
								pw.println("\nJeu Termine, le gagnant est"+gagnant);
							}
							
						}
						else {
							pw.println("Format entrée incorrect");
						}
					
					
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
          }
          }
