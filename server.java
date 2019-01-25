package домашно;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.security.Security;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import com.sun.net.ssl.internal.ssl.Provider;

public class server
{
    public static void main(String args[])
    {

        int port = 252;

        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.keyStore","myKeyStore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword","123456");
        System.setProperty("javax.net.debug","all");
        
        try
        {

            SSLServerSocketFactory sslServerSocketfactory = (SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            SSLServerSocket sslServerSocket = (SSLServerSocket)sslServerSocketfactory.createServerSocket(port);
            System.out.println("Echo Server Started & Ready to accept Client Connection");
            SSLSocket sslSocket = (SSLSocket)sslServerSocket.accept();
            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            outputStream.writeUTF("Hello Client, Say Something!");

            while(true)
            {
                String recivedMessage = inputStream.readUTF();
                System.out.println("Client Said : " + recivedMessage);
                if(recivedMessage.equals("close"))
                {
                    outputStream.writeUTF("Bye");
                    outputStream.close();
                    inputStream.close();
                    sslSocket.close();
                    sslServerSocket.close();
                    break;
                }
                else
                {
                    outputStream.writeUTF("You Said : "+recivedMessage);
                }
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error Happened : "+ex.toString());
        }
    }
}
