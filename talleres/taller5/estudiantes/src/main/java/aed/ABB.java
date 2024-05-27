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

    private class Nodo {
        // Agregar atributos privados del Nodo
        private Nodo _padre;
        private Nodo _izq;
        private Nodo _der;
        private T _valor;

        // Crear Constructor del nodo

        public Nodo(T e) {
            _padre = null;
            _izq = null;
            _der = null;
            _valor = e;
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
        else if (elem.compareTo(padre_actual._valor) < 0) {
            padre_actual._izq = nuevo_nodo;
        }
        else {
            padre_actual._der = nuevo_nodo;
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
    }

    public String toString(){
        throw new UnsupportedOperationException("No implementada aun");
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

    private Nodo buscarNodoPorElemento(T elem) { // devuelve el nodo nulo si no se encuentra
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
}
