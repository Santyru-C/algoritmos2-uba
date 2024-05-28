package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;
    private int _altura;
    private T _max;
    private T _min;

    public class Nodo {
        // Agregar atributos privados del Nodo
        private Nodo _padre;
        private Nodo _izq;
        private Nodo _der;
        private T _valor;
        private int _hijos;

        // Crear Constructor del nodo

        public Nodo(T e) {
            _padre = null;
            _izq = null;
            _der = null;
            _valor = e;
            _hijos = 0;
        }

        public void agregarHijo (Nodo hijo) {
            int comparacion = _valor.compareTo(hijo._valor);

            if (comparacion > 0) {
                _izq = hijo;
            }
            else {
                _der = hijo;
            }

            _hijos++;
        }

        public T valor() {
            return _valor;
        }
    }

    public ABB() {
        _raiz = null;
        _cardinal = 0;
        _altura = 0;
        _max = null;
        _min = null;
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        return _min;
    }

    public T maximo(){
        return _max;
    }

    public void insertar(T elem){
        if (pertenece(elem)) {
            return;
        }

        Nodo nuevo_nodo = new Nodo(elem);
        Nodo actual = _raiz;
        Nodo padre_actual = null;

        while (actual != null) {

            padre_actual = actual._padre;
            if (elem.compareTo(actual._valor) < 0) {
                padre_actual = actual;
                actual = actual._izq;
            } 
            else {
                padre_actual = actual;
                actual = actual._der;
            }
        }

        nuevo_nodo._padre = padre_actual;

        if (padre_actual == null) {
            _raiz = nuevo_nodo;
        }
        else {
            padre_actual.agregarHijo(nuevo_nodo);
        }

        //establecemos maximo y minimo
        actualizarMaximoYMinimo(elem);
        _cardinal++;

    }

    public boolean pertenece(T elem){
        return buscarNodoPorElemento(elem) != null;
    }

    public void eliminar(T elem){
        // 3 casos o 4 si tomamos en cuenta cuando el arbol es nulo
        Nodo a_eliminar = buscarNodoPorElemento(elem);

            //si elimino maximo actualizar valor
            //si elimino minimo actualizar valor
            // tener cuenta si el eliminado es la raiz
        
        if (a_eliminar == null) {
            ;
        }
        else if (a_eliminar._hijos == 1) {  
            Nodo hijo = a_eliminar._der == null? a_eliminar._izq : a_eliminar._der;
            a_eliminar._padre.agregarHijo(hijo);
            hijo._padre = a_eliminar._padre;
        }
        else if (a_eliminar._hijos == 2) {

        }

        _cardinal--;
    }

    public String toString(){
        Nodo actual = buscarNodoPorElemento(_min);
        String cadena = "{" + actual._valor  + ",";
        System.out.println(actual);

        actual = buscarSucesor(actual);

        while (buscarSucesor(actual) != null) {
            System.out.println(actual._valor);
            cadena = cadena + actual._valor + ",";
            actual = buscarSucesor(actual);
        }

        return cadena + actual._valor + "}";
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual;

        public boolean haySiguiente() {            
            throw new UnsupportedOperationException("No implementada aun");
        }
    
        public T siguiente() {
            throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
    }

    private void actualizarMaximoYMinimo(T elem) {
        if (_cardinal == 0) {
            _max = elem;
            _min = elem;
        }
        else if (_max.compareTo(elem) < 0) {
            _max = elem;
        }
        else if (_min.compareTo(elem) > 0) {
            _min = elem;
        }
    }

    public Nodo buscarNodoPorElemento(T elem) { // devuelve el nodo nulo si no se encuentra
        Nodo actual = _raiz;

        while (actual != null) {
            int comparacion = elem.compareTo(actual._valor);

            if (comparacion == 0) {
                return actual;
            }
            else if (comparacion < 0) {
                actual = actual._izq;
            }
            else {
                actual = actual._der;
            }
        }

        return actual;
    }

    public Nodo buscarSucesor(Nodo actual) {
        Nodo sucesor;

        if (actual._valor == _max) {
            sucesor = null;
        }
        else if (actual._der != null) {
            // uno a la derecha y todos a la izquiera
            sucesor = actual._der;

            while (sucesor._izq != null) {
                sucesor = sucesor._izq;
            }
        }
        else {
            sucesor = actual._padre;

            while (sucesor != null && sucesor._valor.compareTo(actual._valor) < 0){
                sucesor = sucesor._padre;
            }
        }

        //y el caso en el que no hay sucesor?

        return sucesor;
    }
}
