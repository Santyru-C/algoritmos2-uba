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

    public T obtener(int i) { //O(n)
        return getNodeByIndex(i).value;
    }

    public void eliminar(int i) { // O(n) en el peor caso donde los nodos a eliminar no sean extremos o sea el unico. 
        
        if (_size != 1) { //estoy totalmente seguro que esto puede ser mas lindo
            Nodo toDelete;

            if (i == 0) { //total del bloque 0(1)
                toDelete = _firstNode; //0(1)
                toDelete.next.prev = toDelete.prev; //0(1)
                _firstNode = toDelete.next; //actualizamos el puntero 0(1)
            }
            else if (i == _size - 1) { //total del bloque O(1)
                toDelete = _lastNode; //O(1)
                toDelete.prev.next = toDelete.next; //O(1)
                _lastNode = toDelete.prev; //O(1)
            }
            else { //total de bloque O(n)
                toDelete = getNodeByIndex(i); // O(n)
                toDelete.next.prev = toDelete.prev; // link current's adyacent nodes with eachother.O(1)
                toDelete.prev.next = toDelete.next; // O(1)
            }

        }    
        else {
            _firstNode = null; // O(1)
            _lastNode = null; // O(1)
        }

        _size--;
    }

    public void eliminarUltimo() { // acceso rapido O(1) ya que representa uno de los 3 mejores casos de eliminar
        eliminar(_size - 1); //siempre eliminamos el ultimo.
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
    public String toString() { //O(n)
        // devuelve la conversion a string de un array que contiene todos los valores
        // correspondientes a cada eslabon de la lista.
        //me encanta que java no te deje crear arrays genericos de tipo T...

        Nodo actual = _firstNode;

        String values = "[" + actual.value + ","; // agregamos el primero

        for (int i = 1; i < _size - 1; i++) { //O(n) se va a repetir la longitud - 2 que representa los extremos. Sigue siendo tiempo lineal
            actual = actual.next;
            values = values + " " + actual.value + ","; //agregamos los del medio
        }

        actual = actual.next; //O(1)
        return values + " " + actual.value + "]"; //O(1) y por ultimo el final

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
            T value = obtener(_position - 1); //guarda el elemento anterior
            _position--;

            return value;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

    private void setStartingNode(Nodo newNode) {
        _firstNode = newNode;
        _lastNode = newNode;
    }

    private Nodo getNodeByIndex(int i) { //O(n) en el peor caso queremos obtener el ultimo nodo
        Nodo currentNode = _firstNode; //O(1)
        
        for (int j = 0; j < i; j++) { //numero de ejecuciones O(n) ya que siempre recorre del primero al nodo a obtener 
            currentNode = currentNode.next; //O(1)
        }

        return currentNode;
    }
}
