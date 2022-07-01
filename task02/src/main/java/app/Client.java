package app;

import java.io.EOFException;
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
    private boolean sresponse=false;

    public Client(){

    }
    
    public void start() throws IOException{
        Socket s = new Socket("task02.chuklee.com", 80);
        while(true){
            try(OutputStream os = s.getOutputStream();
            InputStream is = s.getInputStream()){
                ObjectOutputStream oos = new ObjectOutputStream(os);
                ObjectInputStream ois = new ObjectInputStream(is);
                String irequest = ois.readUTF();
                String irequestid = irequest.split(" ")[0];
                String irequestnumliststring = irequest.split(" ")[1];
                String[] irequestnumlist = irequestnumliststring.split(",");
                for (String i: irequestnumlist){
                    try{
                        sum += Float.parseFloat(i);
                    }catch (NumberFormatException e){
                        System.out.println("the number in the list is not a valid number"  + e);
                    }
                        noint++;
                }
                float average = sum/noint;
                oos.writeUTF(irequestid);
                oos.writeUTF("Lim Jia Qiang");
                oos.writeUTF("jqlimjiaqiang@gmail.com");
                oos.writeFloat(average);
                oos.flush();

                sresponse = ois.readBoolean();
                if(sresponse){
                    System.out.println("Success");
                }else{
                    String fsresponse = ois.readUTF();
                    System.out.println("Failed" + fsresponse);
                }
                
                oos.close();
                os.close();
                ois.close();
                is.close();
                break;

            }catch(EOFException e){
                System.out.println(e);
            } catch(IOException e){
                System.out.println(e);
            }finally{
                System.out.println("Task 2 finished");
            }
        }
    }
}
