package org.aquapackrobotics.sw8s;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.*;
import java.io.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import org.aquapackrobotics.sw8s.App;

public class processEvent implements KeyListener
{
    // initialize socket and input output streams
        private Socket socket        = null;
        private ServerSocket server   = null;
        private DataInputStream input = null;
        private DataOutputStream out     = null;
        
        private JTextArea ta = null;
        private int port = 0;
        private String address = null;
        int y = 0;
        int x = 0;
        int z = 0;
        int yaw = 0;
        int pitch = 0;
        int roll = 0;

        @Override
        public void keyReleased(KeyEvent e) {};
        @Override
        public void keyTyped(KeyEvent event) {};
        @Override
        public void keyPressed(KeyEvent e) {// TO IMPLEMENT: ALL ZEROES, MAKE ROBOT NOT GO **BEFORE** SYSTEM.EXIT
            //currentMission = "stopped";
            try {
                input = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } 
            catch (IOException a) {
                // TODO: handle exception
            }
            try
            {
                
                
                if(e.getKeyChar() == ' ') {
                    out.writeUTF("Over");
                    input.close();
                    out.close();
                    socket.close();
                } else {
                    System.out.println("Input Read:");
                    out.writeUTF(KeyEvent.getKeyText(e.getKeyCode()));
                    receive(KeyEvent.getKeyText(e.getKeyCode()));
                }

                System.out.println("SEND COMMAND: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
            catch(Exception i)
            {
                System.out.println(i);
            }}


    /**
     * Constructor for processEvent
     */
    public processEvent(String address, int port, JTextArea ta) {
    // establish a connection
        System.out.println("ProcessEvent runs");
        this.ta = ta;
        this.port = port;
        this.address = address;
        try {
            socket = new Socket(address, port);
            System.out.println("Socket is started");
        } catch (Exception e) {
            // TODO: handle exception
            System.err.println("Error connecting to server: " + e.getMessage());
            System.exit(0);
        }
        try {
            
            this.input = new DataInputStream(socket.getInputStream());
            System.out.println("Socket Input is made");
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            this.out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Socket output is made");
        } catch (Exception e) {
            // TODO: handle exception
        }
        
        
        System.out.println("Socket Connected");

        // takes input from client
        
   
        
    }

    /**
     * Looped with keyPressed
     * Creates updated text for UI
     */
    private void receive(String line) {
        line = line.toLowerCase();
        try{
        if (!line.equals("Over")){
        try
            {
                System.out.println("Client: " + line);
                switch (line){
                    case "w":
                        y +=1;
                        break;
                    case "s":
                        y -=1;
                        break;
                    case "a":
                        x -=1;
                        break;
                    case "d":
                        x +=1; 
                        break;
                    case "q":
                        roll +=1;
                        break;
                    case "e":
                        roll -=1;
                        break;
                    case "i":
                        z +=1;
                        break;
                    case "k":
                        z -=1;
                        break;
                    case "j":
                        yaw -=1;
                        break;
                    case "l":
                        yaw +=1;
                        break;
                    case "u":
                        pitch -=1;
                        break;
                    case "o":
                        pitch +=1;
                        break;
                    default:
                        System.out.println("Invalid message: " + line);
                }

                 ta.setText("Forward and Backward = " + y + 
                        "\nSide to Side = " + x + 
                        "\nUp and Down = " + z + 
                        "\nYaw = " + yaw + 
                        "\nRoll = " + roll + 
                        "\nPitch = " + pitch);
                //Look up making a thread java lambda
            
        }
        catch(Exception u)
        {
            System.out.println(u);
        }
        

        }
    }
    catch(Exception u)
        {
            System.out.println(u);
        }
    
    
    
    }
}
