package com.example.socketchat;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

public class HelloController {

    @FXML
    private Button SendButton;

    @FXML
    private TextArea show;

    @FXML
    private TextField InputFeild;

    @FXML
    private AnchorPane ancor;

    boolean isConnected = false;

    BufferedReader Reader;
    BufferedWriter Writer;

    public HelloController(){

    }
    @FXML
    void ButtonPressed(ActionEvent event) {
        if(!isConnected) {

            String ImputName = InputFeild.getText();
            InputFeild.clear();
            if(ImputName == null || ImputName.length()==0){
                show.appendText("Please Enter Your Name!\n");
                return;
            }

            try {
                Socket sc = new Socket("localhost", 6600);

                OutputStreamWriter osw = new OutputStreamWriter(sc.getOutputStream());
                Writer = new BufferedWriter(osw);

                Writer.write(ImputName+"\n");
                Writer.flush();

                InputStreamReader isr = new InputStreamReader(sc.getInputStream());
                Reader = new BufferedReader(isr);

                Thread serverListener = new Thread() {

                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String data = Reader.readLine() + "\n";
                                show.appendText(data);
//                                System.out.println(data);

                            }catch (SocketException e){
                                show.appendText("Connection lost :(\n");
                                break;
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                };
                serverListener.start();

                show.appendText("Connection Established! \n");
                SendButton.setText("Send");
//                show.appendText(ImputName+InputFeild.getText());
                isConnected = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                String msg = InputFeild.getText();
                InputFeild.clear();
                if (msg == null || msg.length() == 0) {
                    return;
                }

                Writer.write(msg+"\n");
                Writer.flush();

            }catch(Exception e){
                e.printStackTrace();

            }
        }
    }

}
