package домашно;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.security.Security;
import com.sun.net.ssl.internal.ssl.Provider;

public class client
{
    public static void main(String args[])
    {

        int serverPort = 252;
        String serverName = "localhost";
        Security.addProvider(new Provider());
        System.setProperty("javax.net.ssl.trustStore","myTrustStore.jts");
        System.setProperty("javax.net.ssl.trustStorePassword","123456");
        System.setProperty("javax.net.debug","all");
        try
        {

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory)SSLSocketFactory.getDefault();
            SSLSocket sslSocket = (SSLSocket)sslsocketfactory.createSocket(serverName,serverPort);
            DataOutputStream outputStream = new DataOutputStream(sslSocket.getOutputStream());
            DataInputStream inputStream = new DataInputStream(sslSocket.getInputStream());
            System.out.println(inputStream.readUTF());

            while (true)
            {
                System.out.println("Write a Message : ");
                String messageToSend = System.console().readLine();
                outputStream.writeUTF(messageToSend);
                System.err.println(inputStream.readUTF());
                if(messageToSend.equals("close"))
                {
                    break;
                }
            }
        }
        catch(Exception ex)
        {
            System.err.println("Error Happened : "+ex.toString());
        }
    }
}