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
            setStartingNode(newNode);
        } else {
            _firstNode.prev = newNode; //enlaza ambos eslabones de la cadena
            newNode.next = _firstNode;
            _firstNode = newNode; //change reference
        }

        _size++;
    }

    public void agregarAtras(T elem) {
        Nodo newNode = new Nodo(elem);

        if (_size == 0) {
            setStartingNode(newNode);
        } else {
            _lastNode.next = newNode;
            newNode.prev = _lastNode;
            _lastNode = newNode;
        }

        System.out.println("----");
        System.out.println(_lastNode.prev);
        System.out.println(_lastNode);
        System.out.println(_lastNode.value);
        System.out.println(_lastNode.next);
        _size++;
    }

    public T obtener(int i) {
        return getNodeByIndex(i).value;
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

    private void setStartingNode(Nodo newNode) {
        _firstNode = newNode;
        _lastNode = newNode;
    }

    private Nodo getNodeByIndex(int i) {
        Nodo currentNode = _firstNode;
        
        for (int j = 0; j < i; j++) {
            currentNode = currentNode.next;
        }

        return currentNode;
    }
}
