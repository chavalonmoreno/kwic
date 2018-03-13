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
            ArrayList<Linea> lineas = getLinesOfFileTexto("texto.txt");
            ArrayList<String> noClaves = getLinesOfFileNoClaves("noClaves.txt");
            ArrayList<String> cadenas = implementKwic(lineas,noClaves);
            for ( String c : cadenas){
                System.out.println(c);
            }
        } catch (IOException e) {
            System.out.println("error causado por "+ e.getMessage() );
        }


    }

    public static ArrayList<String> implementKwic( ArrayList<Linea> lineas, ArrayList<String> noClaves) {
        ArrayList<String> cadenas = new ArrayList<>();
        for ( Linea linea : lineas ){
            Collections.sort(linea.getTokens(),String.CASE_INSENSITIVE_ORDER);
            for ( String token : linea.getTokens()){
                if ( noClaves.contains(token)){
                    continue;
                } else {
                    String indice = token.concat("\t:\t\t");
                    String enunciado = linea.getLinea().replace(token, "....");
                    enunciado.concat("\n");
                    indice += enunciado;
                    cadenas.add(indice);
                }
            }
        }
        return  cadenas;
    }

    public static ArrayList<Linea> getLinesOfFileTexto (String archivo ) throws IOException {
        ArrayList<Linea> lineas = new ArrayList<>();
        ArrayList<String> tokens ;
        String cadena = "";
        Linea linea;
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

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Practica2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineas;
    }

    public static ArrayList<String> getLinesOfFileNoClaves (String archivo ) throws IOException {
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
                    String [] tokens = s.split("\\s");
                    for ( String token : tokens ){
                        lineas.add(token);
                    }
                }
            } else {
                BufferedReader bf = new BufferedReader(new FileReader( archivo ));
                while ( (cadena = bf.readLine()) != null ) {
                    String [] tokens = cadena.split("\\s");
                    for ( String token : tokens ){
                        lineas.add(token);
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Practica2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineas;
    }
}
    class Linea implements Comparable<Linea>{
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
