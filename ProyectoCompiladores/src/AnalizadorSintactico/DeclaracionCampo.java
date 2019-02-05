package AnalizadorSintactico;

import AnalizadorLexico.Token;
import java.util.ArrayList;

public class DeclaracionCampo {

    private Token visibilidad;
    private Token tipoDato;
    private ArrayList<Variable> listaIdentificadores;

    public DeclaracionCampo(Token visibilidad, Token tipoDato, ArrayList<Variable> listaIdentificadores) {
        super();
        this.visibilidad = visibilidad;
        this.tipoDato = tipoDato;
        this.listaIdentificadores = listaIdentificadores;
    }

    public Token getVisibilidad() {
        return visibilidad;
    }

    public Token getTipoDato() {
        return tipoDato;
    }

    public ArrayList<Variable> getListaIdentificadores() {
        return listaIdentificadores;
    }

    @Override
    public String toString() {
        return "DeclaracionCampo [visibilidad=" + visibilidad + ", tipoDato=" + tipoDato + ", listaIdentificadores="
                + listaIdentificadores + "]";
    }

}
