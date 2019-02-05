/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AnalizadorSintactico;

import AnalizadorLexico.Token;
import java.util.ArrayList;


/**
 *
 * @author andre
 */
public class Funcion {
    private Token identificador;
    private Token tipoRetorno;
    private ArrayList<Parametro> parametros;
    private ArrayList<Sentencia> sentencias;

    public Funcion(Token identificador, Token tipoRetorno, ArrayList<Parametro> parametros, ArrayList<Sentencia> sentencias) {
        this.identificador = identificador;
        this.tipoRetorno = tipoRetorno;
        this.parametros = parametros;
        this.sentencias = sentencias;
    }

    public Token getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Token identificador) {
        this.identificador = identificador;
    }

    public Token getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(Token tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public ArrayList<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(ArrayList<Parametro> parametros) {
        this.parametros = parametros;
    }

    public ArrayList<Sentencia> getSentencias() {
        return sentencias;
    }

    public void setSentencias(ArrayList<Sentencia> sentencias) {
        this.sentencias = sentencias;
    }
    
    
    
}
