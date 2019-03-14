package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;

/**
 *
 * @author Olivier Liechti
 */
public class UpperCaseFilterWriter extends FilterWriter {
  
  public UpperCaseFilterWriter(Writer wrappedWriter) {
    super(wrappedWriter);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {

    for(int i = off;i < (off + len);i++){
      write(cbuf[i]);
    }
  }

  @Override
  public void write(int c) throws IOException {

    if(c>=0x61 && c <= 0x7a){
      out.write(c - 32);
    }
    else{
      out.write(c);
    }


  }

}
