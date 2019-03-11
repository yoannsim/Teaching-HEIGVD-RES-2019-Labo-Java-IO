package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;
import ch.heigvd.res.labio.impl.Utils;

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
  private Boolean windiwsFlag = false;

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {

    String out ="";
    String[] tab;

     str = str.substring(off,len+off);

    if(numLigne == 0)
      out = ++numLigne + "\t";

    do{
        tab = Utils.getNextLine(str);
        str = tab[1];
        out += tab[0];
        if(tab[1].isEmpty() ||(!tab[1].isEmpty() &&  !tab[0].isEmpty())){
            out += ++numLigne + "\t";

        }
      }while (!(tab[0].isEmpty() || tab[1].isEmpty()));

    if(!tab[1].isEmpty())
      out += tab[1];

    super.write(out,0,out.length());

  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String s = String.valueOf(cbuf);
    write(s,off,len);
  }

  @Override
  public void write(int c) throws IOException {




    if (c == '\r'){
      windiwsFlag = true;
    }
    else if (windiwsFlag){
      windiwsFlag = false;
      char[] cbuf = {'\r',(char)c};
      write(cbuf);
    }
    else{
      char[] cbuf = {(char)c};
      write(cbuf);
    }
  }

}
