import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Practica2 {
    public static void main(String args []){

        try {
            ArrayList<String> cadenas = new ArrayList<>();
            ArrayList<String> lineas = getLinesOfFile("texto");
            for ( String string : lineas ){
                sortString(string,cadenas);
            }
            Collections.sort(cadenas,String.CASE_INSENSITIVE_ORDER);
            for ( String c : cadenas){
                System.out.println(c);
            }
        } catch (IOException e) {
            System.out.println("error causado por "+ e.getMessage() );
        }


    }

    public static void sortString( String cadena, ArrayList<String> resultado ) {
        String [] tokens = cadena.split("\\s");
        resultado.add(cadena);
        int tamano = tokens.length;
        int contador = 1;
        boolean bandera = true;
        String inicio = tokens[0];
        while (bandera){
            String [] tokensAux = new String[tamano];
            int n = 1;
            for ( String s : tokens ){
                tokensAux [n] = s;
                n++;
                if ( n == (tamano)){
                    n = 0;
                }
            }
            String cadenaAux = tokensAux[0];
            for ( int i=1; i < tamano; i++ ) {
                cadenaAux += " " + tokensAux[i];
            }
            tokens = tokensAux;
            resultado.add(cadenaAux);
            if ( ++contador == tamano ){
                bandera = false;
            }
        }

    }

    public static ArrayList<String> getLinesOfFile (String archivo ) throws IOException {
        ArrayList<String> lineas = new ArrayList<>();
        String cadena = "";

        try {
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
                    lineas.add(s);
                }
            } else {
                BufferedReader bf = new BufferedReader(new FileReader( archivo ));
                while ( (cadena = bf.readLine()) != null ) {
                    lineas.add(cadena);
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Practica2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineas;
    }
}
