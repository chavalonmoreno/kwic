
public class Indice implements Comparable<Indice>{

  private String indice;
  private String enunciado;
  private String primerPalabra;

  public Indice(String t) {
    indice = t;
  }

  public Indice(){

  }

  public boolean equals(Object o) {
    return indice.equalsIgnoreCase((String) o);
  }
  public int hashCode() {
    return indice.hashCode();
  }

  public String getIndice() {
    return indice;
  }

  public void setIndice(String indice) {
    this.indice = indice;
  }

  public String getEnunciado() {
    return enunciado;
  }

  public void setEnunciado(String enunciado) {
    this.enunciado = enunciado;
  }

  public String getPrimerPalabra() {
    return primerPalabra;
  }

  public void setPrimerPalabra(String primerPalabra) {
    this.primerPalabra = primerPalabra;
  }

  public String toString(){
    return indice;
  }

  @Override
  public int compareTo(Indice indice) {
    return this.indice.compareToIgnoreCase(indice.indice);
  }
}
