package mailmerge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class MailMerge {
    
    private Path tocsv = Paths.get("tour_packages.csv");
    private File csv = tocsv.toFile();
    private Path totemplate = Paths.get("tour_packages.txt");
    private File template = totemplate.toFile();
    private String templateString;
    private List<String> headerList;
    private List<String> rows;
    private String rowString;
    private String[] rowsplit;
    private List<String> rowList;

    
    
    public MailMerge(String[] args){
        if (args.length>0){
            this.csv = Paths.get(args[0]).toFile();
            this.template = Paths.get(args[1]).toFile();
        }
    }

    public void start() throws IOException{
        
        templateString = readTemplateFile(template);
        headerList = readcvs(csv).get(0);
        rows = readcvs(csv).get(1);

        for(int i = 0; i<rows.size(); i++){
            String messageString =  new String(templateString);
            rowString = rows.get(i);
            rowsplit = rowString.split(",");
            rowList = new LinkedList<>();
            for(String r: rowsplit){
                rowList.add(r);
            }

            for (int j=0; j<headerList.size(); j++){
                if(messageString.contains(headerList.get(j))){
                    String oldString = new String("__"+headerList.get(j)+"__");
                    messageString = messageString.replace(oldString, rowList.get(j));
                }
            }

            System.out.println(messageString);
        }
    }

    public static String readTemplateFile(File file) throws IOException{
        String currentLine;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while((currentLine = br.readLine())!=null){
                sb.append(currentLine);
                sb.append("\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<List<String>> readcvs(File file) throws IOException{
        List<String> cvsrow = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))){
            String line = "";
            while((line= br.readLine()) != null){
                cvsrow.add(line);
            }
        }catch(FileNotFoundException e){
            System.out.println(e);
        }
        String header = cvsrow.remove(0);
        String[] headerarray = header.split(",");
        List<String> headerlist = new LinkedList<>();
        for(String s: headerarray){
            headerlist.add(s);
        }

        List<List<String>> returnargs = new LinkedList<>();
        returnargs.add(headerlist);
        returnargs.add(cvsrow);
        return returnargs;
    }



}
