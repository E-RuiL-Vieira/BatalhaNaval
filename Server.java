/* 
Autores: Rui Emanuel Lima Viera - NUSP: 11810182
         André Guarnier de Mitri - NUSP: 11395579
*/
package com.mycompany.batalhanaval;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Server {
    private ObjectInputStream input;
    private ObjectOutputStream output;

    public Server() {
        try {
            ServerSocket ss = new ServerSocket(6666);
            Socket s = ss.accept();
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String str = (String) dis.readUTF();
            System.out.println("");
            ss.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
  
    
    public static void Client() {
        try {
            Socket s = new Socket("192.168.1.101", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF("Hello server");
            dout.flush();
            dout.close();
            s.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }
    
}
