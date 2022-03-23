package tp_pro_res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Interfacejeu extends Application {
	PrintWriter pw;
	  public static void main (String[] args) {
		  launch(args);
		  
	  }
		@Override
		public void start(Stage primaryStage) throws Exception {
			primaryStage.setTitle("Jeu de nombre");
			BorderPane borderPane = new BorderPane();
			Label labelHost=new Label("Host:");
			TextField textFieldHost = new TextField("localhost");
			Label labelPort=new Label("Port:");
			TextField textFieldPort = new TextField("1234");
			Button buttonConnecter = new Button ("Connecter");
			HBox hBox= new HBox();
			hBox.setSpacing(10);hBox.setPadding(new Insets(10));
			hBox.setBackground(new Background(new BackgroundFill(Color.AQUAMARINE, null, null)));
			hBox.getChildren().addAll(labelHost,textFieldHost,labelPort,textFieldPort,buttonConnecter);
			borderPane.setTop(hBox);
			VBox vBox=new VBox();vBox.setSpacing(10);vBox.setPadding(new Insets(10));
			ObservableList<String> listModel=FXCollections.observableArrayList();
			ListView<String> listview = new ListView<String>(listModel);
			vBox.getChildren().add(listview);
			borderPane.setCenter(vBox);
			
			Label labelMessage = new Label("Nombre:");
			TextField textfieldMessage= new TextField(); textfieldMessage.setPrefSize(400, 30);
			Button buttonEnvoyer = new Button("Deviner");
			HBox hbox2=new HBox(); hbox2.setSpacing(10);hbox2.setPadding(new Insets(10));
			hbox2.getChildren().addAll(labelMessage,textfieldMessage,buttonEnvoyer);
			borderPane.setBottom(hbox2);

			Scene scene = new Scene(borderPane,800,600);
			primaryStage.setScene(scene);
			primaryStage.show();
			buttonConnecter.setOnAction((evt)->{
				String host = textFieldHost.getText();
				String nombre = textfieldMessage.getText();

				int port = Integer.parseInt(textFieldPort.getText());
				try {
					Socket socket = new Socket(host,port);
					InputStream inputStream=socket.getInputStream()	;
					InputStreamReader isr = new InputStreamReader(inputStream);
					BufferedReader bufferedReader = new BufferedReader(isr);
					pw = new PrintWriter(socket.getOutputStream(),true);
					new Thread(()->{
						while(true) {

							try {
									String response = bufferedReader.readLine();
									Platform.runLater(()->{
										listModel.add(response);
									});

								
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}}
					}).start();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
			buttonEnvoyer.setOnAction((evt)->{
				String message = textfieldMessage.getText();
				pw.println(message);
				listModel.add(message);

			});
			
		}
	}
 


