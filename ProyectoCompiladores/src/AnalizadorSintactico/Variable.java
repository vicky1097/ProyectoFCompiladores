package AnalizadorSintactico;

import AnalizadorLexico.Token;

public class Variable {

    private Token identificador;
    private ExpresionAritmetica expresion;
    

    public Variable(Token identificador) {
        super();
        this.identificador = identificador;
    }

    public Variable(Token identificador, ExpresionAritmetica expresion) {
        super();
        this.identificador = identificador;
        this.expresion = expresion;
    }

    @Override
    public String toString() {
        return "Variable [identificador=" + identificador + ", expresion=" + expresion + "]";
    }

}
