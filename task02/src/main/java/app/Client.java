package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public Client(){

    }
    
    public void start() throws IOException{
        Socket s = new Socket("sdf.chuklee.com", 80);

        try(OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream()){
                ObjectOutputStream oos = new ObjectOutputStream(os);
                ObjectInputStream ois = new ObjectInputStream(is);

                String irequest = ois.readUTF();
                String irequestid = irequest.split(" ")[0];
                String[] irequestnumlist = irequest.split(" ")[1].split(",");

                

            }catch(IOException e){
                System.out.println(e);
            }

    }
    
}
