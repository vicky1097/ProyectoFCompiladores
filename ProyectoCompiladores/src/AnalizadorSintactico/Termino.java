package AnalizadorSintactico;

import AnalizadorLexico.Token;
import javax.swing.tree.DefaultMutableTreeNode;

public class Termino {

    private Token termino;

    public Termino(Token termino) {
        super();
        this.termino = termino;
    }

    public Token getTermino() {
        return termino;
    }

    public DefaultMutableTreeNode getArbolVisual() {

        DefaultMutableTreeNode nodo = new DefaultMutableTreeNode("Termino");
        String valor = "ERROR";

        if (termino != null) {
            valor = termino.getLexema() + ":" + termino.getCategoria();
        }

        nodo.add(new DefaultMutableTreeNode(valor));
        return nodo;
    }

    @Override
    public String toString() {
        return "Termino [termino=" + termino + "]";
    }

}
