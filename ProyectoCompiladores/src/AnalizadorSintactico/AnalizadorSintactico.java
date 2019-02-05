package AnalizadorSintactico;

import AnalizadorLexico.Categoria;
import AnalizadorLexico.Token;
import java.util.ArrayList;

public class AnalizadorSintactico {

    private ArrayList<Token> tablaSimbolos;
    private ArrayList<ErrorSintactico> tablaErrores;
    private int posicionActual;
    private Token tokenActual;
    private UnidadDeCompilacion unidadDeCompilacion;

    public AnalizadorSintactico(ArrayList<Token> tablaSimbolos) {
        this.tablaSimbolos = tablaSimbolos;
        this.tokenActual = tablaSimbolos.get(posicionActual);
        this.tablaErrores = new ArrayList<>();
    }

    public void analizar() {
        this.unidadDeCompilacion = esUnidadDeCompilacion();
    }

    /**
     * <UnidadCompilación> ::= <ListaDeclaracion>
     *
     * @return
     */
    public UnidadDeCompilacion esUnidadDeCompilacion() {

        ArrayList<DeclaracionCampo> ea = esListaDeclaraciones();

        if (ea != null) {
            return new UnidadDeCompilacion(ea);
        }

        return null;
    }

    /**
     * <ListaDeclaracion> ::= <DeclaracionCampo>[<ListaDeclaracion>]
     *
     * @return
     */
    public ArrayList<DeclaracionCampo> esListaDeclaraciones() {
        ArrayList<DeclaracionCampo> lista = new ArrayList<>();

        DeclaracionCampo declaracionCampo = esDeclaracionCampo();

        while (declaracionCampo != null) {
            lista.add(declaracionCampo);
            declaracionCampo = esDeclaracionCampo();
        }

        return lista;
    }

    /**
     * <EA> ::= <Termino>[op.Arit<EA>] | "("<EA>")"[op.Arit<EA>]
     *
     * @return
     */
    public ExpresionAritmetica esExpresionAritmetica() {

        Termino termino = esTermino();

        if (termino != null) {
            obtenerSiguienteToken();

            if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
                Token operadorAritmetico = tokenActual;
                obtenerSiguienteToken();

                ExpresionAritmetica exp = esExpresionAritmetica();

                if (exp != null) {
                    return new ExpresionAritmetica(termino, operadorAritmetico, exp);
                } else {
                    reportarError("Falta una expresión aritmética");
                }

            } else {
                return new ExpresionAritmetica(termino);
            }

        }

        if (tokenActual.getCategoria() == Categoria.PARENTESIS_ABRIR) {
            obtenerSiguienteToken();

            ExpresionAritmetica ea = esExpresionAritmetica();

            if (ea != null) {

                if (tokenActual.getCategoria() == Categoria.PARENTESIS_CERRAR) {
                    obtenerSiguienteToken();

                    if (tokenActual.getCategoria() == Categoria.OPERADOR_ARITMETICO) {
                        Token operadorAritmetico = tokenActual;
                        obtenerSiguienteToken();

                        ExpresionAritmetica ea2 = esExpresionAritmetica();

                        if (ea2 != null) {
                            return new ExpresionAritmetica(ea, operadorAritmetico, ea2);
                        } else {
                            reportarError("Falta expresión aritmética");
                        }

                    } else {
                        return new ExpresionAritmetica(ea);
                    }

                } else {
                    reportarError("Falta paréntesis derecho");
                }

            } else {
                reportarError("Falta expresión aritmética");
            }

        }

        return null;

    }

    /**
     * <Termino> ::= entero | real | identificador
     *
     * @return
     */
    public Termino esTermino() {

        if (tokenActual.getCategoria() == Categoria.ENTERO
                || tokenActual.getCategoria() == Categoria.REAL
                || tokenActual.getCategoria() == Categoria.IDENTIFICADOR
                || tokenActual.getCategoria() == Categoria.BOOLEAN
                || tokenActual.getCategoria() == Categoria.CADENA_CARACTERES
                || tokenActual.getCategoria() == Categoria.CARACTER
                || esInvocacion(tokenActual.getCategoria())) {
            return new Termino(tokenActual);
        }

        return null;
    }

    /**
     * <DeclaracionCampo> ::= [<visibilidad>] <tipoDato> <ListaVariables> ";"
     *
     * @return
     */
    public DeclaracionCampo esDeclaracionCampo() {

        Token visibilidad = esVisibilidad();

        if (visibilidad != null) {
            obtenerSiguienteToken();
        }

        Token tipoDato = esTipoDato();

        if (tipoDato != null) {
            obtenerSiguienteToken();
            ArrayList<Variable> listaIdent = esListaVariables();

            if (listaIdent != null) {

                if (tokenActual.getCategoria() == Categoria.FIN_SENTENCIA) {
                    obtenerSiguienteToken();
                    return new DeclaracionCampo(visibilidad, tipoDato, listaIdent);
                } else {
                    reportarError("Falta fin de sentencia");
                }
            } else {
                reportarError("Debe escribir al menos un identificador");
            }
        }

        return null;
    }

    /**
     * <ListaVariables> :== <variable> [","<ListaVariables>]
     *
     * @return
     */
    public ArrayList<Variable> esListaVariables() {

        ArrayList<Variable> lista = new ArrayList<>();
        Variable variable = esVariable();

        if (variable != null) {

            lista.add(variable);

            if (tokenActual.getCategoria() == Categoria.SEPARADOR) {
                obtenerSiguienteToken();
                lista.addAll(esListaVariables());
            }

        }

        return lista;
    }

    /**
     * <variable> ::= identificador["="<Expresion>]
     *
     * @return
     */
    public Variable esVariable() {

        if (tokenActual.getCategoria() == Categoria.IDENTIFICADOR) {
            Token identificador = tokenActual;
            obtenerSiguienteToken();

            if (tokenActual.getLexema().equals(":")) {
                obtenerSiguienteToken();

                ExpresionAritmetica e = esExpresionAritmetica();

                if (e != null) {
                    return new Variable(identificador, e);
                } else {
                    reportarError("Falta expresion");
                }

            } else {
                return new Variable(identificador);
            }
        }

        return null;
    }

    /**
     * <Asignacion> ::= identificador operadorAsignacion <Expresion>
     *
     * @return
     */
    public String esAsignacion() {
        return null;
    }

    /**
     * <visibilidad> ::= public | private
     *
     * @return
     */
    public Token esVisibilidad() {

        if (tokenActual.getLexema().equals("public") || tokenActual.getLexema().equals("private")) {
            return tokenActual;
        }

        return null;
    }

    /**
     * <tipoDato> ::= int | double | identificador | char
     *
     * @return
     */
    public Token esTipoDato() {
        if (tokenActual.getLexema().equals("entero") || tokenActual.getLexema().equals("doble")
                || tokenActual.getLexema().equals("caracter") || tokenActual.getLexema().equals("cadena")) {
            return tokenActual;
        }

        return null;
    }

    public Token esTipoRetorno() {
        if (tokenActual.getLexema().equals(esTipoDato().getLexema()) || tokenActual.getLexema().equals("")) {
            return tokenActual;
        }

        return null;
    }

    public Token esFuncion() {
        if (tokenActual.getLexema().equals("F")) {
            obtenerSiguienteToken();

        }
        return null;
    }

    public void obtenerSiguienteToken() {

        if (posicionActual < tablaSimbolos.size() - 1) {
            posicionActual++;
            tokenActual = tablaSimbolos.get(posicionActual);
        } else {
            tokenActual = new Token("", Categoria.FIN_CODIGO, tokenActual.getFila(), tokenActual.getColumna());
        }

    }

    public void reportarError(String mensaje) {
        tablaErrores.add(new ErrorSintactico(mensaje, tokenActual.getFila(), tokenActual.getColumna()));
    }

    public void hacerBacktracking(int posInicial) {
        posicionActual = posInicial;
        tokenActual = tablaSimbolos.get(posicionActual);
    }

    public ArrayList<ErrorSintactico> getTablaErrores() {
        return tablaErrores;
    }

    public UnidadDeCompilacion getUnidadDeCompilacion() {
        return unidadDeCompilacion;
    }

    private boolean esInvocacion(Categoria categoria) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
