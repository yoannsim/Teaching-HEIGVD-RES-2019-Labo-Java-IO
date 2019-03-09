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
    super.write(str.toUpperCase(),off,len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    String s = String.valueOf(cbuf);
    write(s,off,len);
  }

  @Override
  public void write(int c) throws IOException {
    char[] cbuf = {(char)c};
    write(cbuf);
  }

}
