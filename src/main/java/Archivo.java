import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Archivo {

  String nombreArchivo;
  String ruta;

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getRuta() {
    return ruta;
  }

  public void setRuta(String ruta) {
    this.ruta = ruta;
  }

  public static ArrayList<Linea>  leerArchivo(String archivo) throws IOException {
    ArrayList<Linea> lineas = new ArrayList<>();
    ArrayList<String> tokens ;
    String cadena;
    Linea linea;
    File file = new File(archivo);
    if (!file.exists()) {
      System.out.println("El archivo no existe");
      System.exit(0);
    }
    if ( file.getName().endsWith(".pdf")){
      PDDocument documento = PDDocument.load(file);
      PDFTextStripper stripper = new PDFTextStripper();
      cadena = stripper.getText(documento);
      String [] cadenas = cadena.split("\\r?\\n");
      for ( String s : cadenas){
        linea = new Linea();
        tokens = new ArrayList<>();
        linea.setLinea(s);
        String [] palabras = s.split("\\s");
        for ( String palabra : palabras ){
          tokens.add(palabra);
        }
        linea.setTokens(tokens);
        lineas.add(linea);
      }

    } else {
      BufferedReader bf = new BufferedReader(new FileReader( archivo ));
      while ( (cadena = bf.readLine()) != null ) {
        linea = new Linea();
        tokens = new ArrayList<>();
        linea.setLinea(cadena);
        String [] palabras = cadena.split("\\s");
        for ( String palabra : palabras ){
          tokens.add(palabra);
        }
        linea.setTokens(tokens);
        lineas.add(linea);
      }
    }
    return lineas;
  }
}
