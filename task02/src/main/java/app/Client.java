package app;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private Float sum = 0.0f;
    private Float noint = 0.0f;
    private Float average; 
    private String irequest;
    private boolean sresponse;

    public Client(){

    }
    
    public void start() throws IOException{
        Socket s = new Socket("task02.chuklee.com", 80);

        try(OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream()){
                ObjectOutputStream oos = new ObjectOutputStream(os);
                ObjectInputStream ois = new ObjectInputStream(is);

                String irequest = ois.readUTF();
                String irequestid = irequest.split(" ")[0];
                String[] irequestnumlist = irequest.split(" ")[1].split(",");
                for (String i: irequestnumlist){
                    try{
                        sum += Float.parseFloat(i);
                    }catch (NumberFormatException e){
                        System.out.println("the number in the list is not a valid number"  + e);
                    }
                }
                float average = sum/noint;

                oos.writeUTF(irequest);
                oos.writeUTF("Lim Jia Qiang");
                oos.writeUTF("jqlimjiaqiang@gmail.com");
                oos.writeFloat(average);
                oos.flush();

                sresponse = ois.readBoolean();
                System.out.println(sresponse);
                
                oos.close();
                os.close();
                ois.close();
                is.close();
                

            }catch(IOException e){
                System.out.println(e);
            }finally{
                System.out.println("Task 2 finished");
            }

    }
    
}
