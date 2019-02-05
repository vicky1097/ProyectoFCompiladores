package AnalizadorSintactico;

import java.util.ArrayList;

public class UnidadDeCompilacion {

    private ArrayList<DeclaracionCampo> declaracionCampo;

    public UnidadDeCompilacion(ArrayList<DeclaracionCampo> declaracionCampo) {
        this.declaracionCampo = declaracionCampo;
    }

    @Override
    public String toString() {
        return "UnidadDeCompilacion [declaracionCampo=" + declaracionCampo + "]";
    }

}
