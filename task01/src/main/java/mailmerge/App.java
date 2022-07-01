package mailmerge;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        MailMerge mm = new MailMerge(args);
        mm.start();
    }
}
