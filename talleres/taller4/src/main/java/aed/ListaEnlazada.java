package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {
    // Completar atributos privados
    private Nodo _firstNode;
    private Nodo _lastNode;
    private int _size;

    private class Nodo {
        T value;
        Nodo next;
        Nodo prev;

        public Nodo(T e) {
            value = e;
        }
    }

    public ListaEnlazada() {
        _size = 0;
    }

    public int longitud() {
        return _size;
    }
    //remember... atomic commits..
    public void agregarAdelante(T elem) {
        Nodo newNode = new Nodo(elem);
        
        if (_size == 0) {
            _firstNode = newNode;
            _lastNode = newNode;
        } else {
            _firstNode.prev = newNode; //enlaza ambos eslabones de la cadena
            newNode.next = _firstNode;
            _firstNode = newNode;
        }

        _size++;
    }

    public void agregarAtras(T elem) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public T obtener(int i) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void eliminar(int i) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public void modificarPosicion(int indice, T elem) {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public ListaEnlazada<T> copiar() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        throw new UnsupportedOperationException("No implementada aun");
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados

        public boolean haySiguiente() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
        
        public boolean hayAnterior() {
	        throw new UnsupportedOperationException("No implementada aun");
        }

        public T siguiente() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
        

        public T anterior() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
	    throw new UnsupportedOperationException("No implementada aun");
    }

}
