import java.util.ArrayList;


public class Linea implements Comparable<Linea>{
  private String linea;
  private ArrayList<String> tokens;

  public String getLinea() {
    return linea;
  }

  public void setLinea(String linea) {
    this.linea = linea;
  }

  public ArrayList<String>  getTokens() {
    return tokens;
  }

  public void setTokens(ArrayList<String>  tokens) {
    this.tokens = tokens;
  }

  public int compareTo(Linea l) {
    return this.getLinea().compareTo(l.getLinea());
  }
}
