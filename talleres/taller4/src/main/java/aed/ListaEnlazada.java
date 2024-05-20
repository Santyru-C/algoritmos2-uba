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

        _size++;
    }

    public T obtener(int i) {
        return getNodeByIndex(i).value;
    }

    public void eliminar(int i) {
        Nodo toDelete = getNodeByIndex(i);
        if (_size != 1) { //estoy totalmente seguro que esto puede ser mas lindo
            
            if (toDelete == _firstNode) {
                toDelete.next.prev = toDelete.prev;
                _firstNode = toDelete.next; //actualizamos el puntero
            }
            else if (toDelete == _lastNode) {
                toDelete.prev.next = toDelete.next;
                _lastNode = toDelete.prev;
            }
            else {
                toDelete.next.prev = toDelete.prev; // link current's adyacent nodes with eachother.
                toDelete.prev.next = toDelete.next;
            }
        }    
        _size--;
    }

    public void modificarPosicion(int indice, T elem) {
        getNodeByIndex(indice).value = elem;

    }
    
    public ListaEnlazada<T> copiar() {
        /*tenemos que devolver una lista que sea igual a la pasada pero obviamente
         * tenemos que evitar el aliassing. Para eso, cada vez que tengamos que copiar
         * uno de los eslabones de la cadena, vamos a crear un nuevo eslabon con
         * que tenga el mismo valor como atributo
         */

        ListaEnlazada<T> copia = new ListaEnlazada<T>();

        for (int i = 0; i < this._size; i++) {
            copia.agregarAtras(this.obtener(i));
        }

        return copia;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        //básicamente generar una nueva linked list a partir de otra
        // tener cuidado con el aliasing.

        ListaEnlazada<T> newLinkedList = lista.copiar();

        this._firstNode = newLinkedList._firstNode;
        this._lastNode = newLinkedList._lastNode;
        this._size = newLinkedList._size;

    }
    
    @Override
    public String toString() {
        // devuelve la conversion a string de un array que contiene todos los valores
        // correspondientes a cada eslabon de la lista.
        //me encanta que java no te deje crear arrays genericos de tipo T...

        String values = "[" + this.obtener(0) + ","; // agregamos el primero

        for (int i = 1; i < _size - 1; i++) {
            values = values + " " + this.obtener(i) + ","; //agregamos los del medio
        }

        return values + " " + this.obtener(_size - 1) + "]"; //y por ultimo el final

        //seguramente esto sea un poco mas "sencillo" importando listas.
    }

    private class ListaIterador implements Iterador<T> { //va a implementar todos los metodos vacios de iterador
    	private int _position = 0;

        public boolean haySiguiente() {
            return _position != _size;
        }
        
        public boolean hayAnterior() {
            return _position != 0;
        }

        public T siguiente() {
            T value = obtener(_position);
            _position++;

            return value;
        }
        

        public T anterior() {
	        throw new UnsupportedOperationException("No implementada aun");
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

    private void setStartingNode(Nodo newNode) {
        _firstNode = newNode;
        _lastNode = newNode;
    }

    private Nodo getNodeByIndex(int i) {
        Nodo currentNode = _firstNode;
        
        for (int j = 0; j < i; j++) {
            System.out.println("----");
            System.out.println(currentNode.prev);
            System.out.println(currentNode);
            System.out.println(currentNode.value);
            System.out.println(currentNode.next);
            currentNode = currentNode.next;
        }

        return currentNode;
    }
}
