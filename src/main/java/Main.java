import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String args []){

        try {

            ArrayList<Linea> lineas = getLinesOfFileTexto("texto.txt");

            ArrayList<String> noClaves = getLinesOfFileNoClaves("noClaves.txt");

            ArrayList<Indice> cadenas = implementKwic(lineas,noClaves);

            Collections.sort(cadenas);

            for ( Indice c : cadenas){
                System.out.println(c.getIndice());
            }
        } catch (IOException e) {
            System.out.println("error causado por "+ e.getMessage() );
        }


    }

    public static ArrayList<Indice> implementKwic( ArrayList<Linea> lineas, ArrayList<String> noClaves) {
        ArrayList<Indice> cadenas = new ArrayList<>();
        for ( Linea linea : lineas ){
            Collections.sort(linea.getTokens(),String.CASE_INSENSITIVE_ORDER);
            for ( String token : linea.getTokens()){
                if ( noClaves.contains(token)){
                    continue;
                } else {
                    Indice indice = new Indice();
                    indice.setPrimerPalabra(token);
                    indice.setIndice(token.concat("\t:\t\t"));

                    boolean existe = false;
                    String enunciadoExtra = "";

                    for(int i=0;i<cadenas.size();i++){
                        if(cadenas.get(i).getPrimerPalabra().compareToIgnoreCase(token) == 0){
                            existe = true;
                            enunciadoExtra += cadenas.get(i).getEnunciado() + "\t";
                            cadenas.remove(i);
                        }
                    }

                    String enunciado = linea.getLinea().replace(token, "...");

                    enunciado.concat("\n");
                    indice.setEnunciado(enunciado);
                    String indiceFinal;
                    if(existe == true){
                        indiceFinal = indice.getIndice() + enunciado + "\n\t\t\t\t" + enunciadoExtra;
                        indice.setEnunciado(enunciado + "\n\t\t\t\t" + enunciadoExtra);
                    }else{
                     indiceFinal =  indice.getIndice() + enunciado;
                    }

                    indice.setIndice(indiceFinal);
                    cadenas.add(indice);
                }
            }
        }
        return  cadenas;
    }

    public static ArrayList<Linea> getLinesOfFileTexto (String archivo ) throws IOException {

        ArrayList<Linea> lineasObtenidas = new ArrayList<>();

        try {

            lineasObtenidas = Archivo.leerArchivo(archivo);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineasObtenidas;
    }

    public void ordenarIndices(){

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
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lineas;
    }
}

