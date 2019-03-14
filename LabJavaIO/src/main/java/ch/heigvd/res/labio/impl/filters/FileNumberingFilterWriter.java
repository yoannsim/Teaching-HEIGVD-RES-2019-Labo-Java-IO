package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;


/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
  private int numLigne = 0;
  private Boolean windowsFlag = false;

  public FileNumberingFilterWriter(Writer out) { super(out);}

  @Override
  public void write(String str, int off, int len) throws IOException {

    write(str.toCharArray(),off,len);

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
      for(int i = off;i < (off + len);i++)
      {
         write(cbuf[i]);
      }
  }

  @Override
  public void write(int c) throws IOException {
    if(numLigne==0)
      out.write(deco());

    if (windowsFlag){

      windowsFlag = false;

      if(c == '\n'){
        out.write('\r');
        newLinge(c);
      }
      else{
        newLinge('\r');
        out.write(c);
      }

    }else if(c == '\n'){
      newLinge(c);
    }
    else if(c == '\r'){
      windowsFlag = true;
    }
    else{
      out.write(c);
    }
  }


  private String deco(){
    return ++numLigne + "\t";
  }
  private void newLinge(int c)throws IOException {
    out.write(c);
    out.write(deco());
  }

}
