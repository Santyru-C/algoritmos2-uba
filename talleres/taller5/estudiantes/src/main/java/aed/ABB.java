package aed;

import java.util.*;

// Todos los tipos de datos "Comparables" tienen el método compareTo()
// elem1.compareTo(elem2) devuelve un entero. Si es mayor a 0, entonces elem1 > elem2
public class ABB<T extends Comparable<T>> implements Conjunto<T> {
    private Nodo _raiz;
    private int _cardinal;
    private int _altura;

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
    }

    public int cardinal() {
        return _cardinal;
    }

    public T minimo(){
        Nodo actual = _raiz;

        //caso en el que el árbol está vacío
        if (actual != null) {
            //mientras se pueda ir a la izquierda...
            while (actual._izq != null) {
                //...ir a la izquierda
                actual = actual._izq;
            }
        }

        return actual._valor;
    }

    public T maximo(){
        Nodo actual = _raiz;

        //caso en el que el árbol está vacío
        if (actual != null) {
            //mientras se pueda ir a la derecha...
            while (actual._der != null) {
                //...ir a la derecha
                actual = actual._der;
            }
        }

        return actual._valor;
    }

    public void insertar(T elem){
        if (pertenece(elem)) {
            return;
        }

        Nodo nuevo_nodo = new Nodo(elem);
        Nodo actual = _raiz;
        Nodo padre_actual = null;

        while (actual != null) {
            padre_actual = actual._padre; //quiza esta linea sea redundante

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

        _cardinal++;

    }

    public boolean pertenece(T elem){
        return buscarNodoPorElemento(elem) != null;
    }

    public void eliminar(T elem){
        Nodo a_eliminar = buscarNodoPorElemento(elem);

        //primer caso, el nodo a eliminar no tiene descendientes.
        if (a_eliminar._hijos == 0) {

            //caso de que sea el unico nodo del árbol
            if (a_eliminar == _raiz) {
                _raiz = null;
            } //TESTING ACA
            else {
                //caso en que no...
                //borramos la conexion a su padre
                if (a_eliminar._valor.compareTo(a_eliminar._padre._valor) < 0) {
                    a_eliminar._padre._izq = null; //podria ser una funcion que sea romper enlace padre/izq/der
                    a_eliminar._padre._hijos--; //abstraer esto porque se repite
                }
                else {
                    a_eliminar._padre._der = null;
                    a_eliminar._padre._hijos--;
                }
            }
            //rompemos el enlace de este nodo a su antiguo padre.
            a_eliminar._padre = null;

        }
        else if (a_eliminar._hijos == 1) {
            Nodo hijo = a_eliminar._der == null? a_eliminar._izq : a_eliminar._der;

            //cambiamos la referencia hacia el nuevo padre.
            hijo._padre = a_eliminar._padre;

            //en el caso de que no sea raiz, y por lo tanto tengamos que reasignar referencia de padre a hijo
            if (a_eliminar != _raiz) {
                
                if (a_eliminar._valor.compareTo(a_eliminar._padre._valor) < 0) {
                    a_eliminar._padre._izq = hijo;
                }
                else {
                    a_eliminar._padre._der = hijo;
                }
            }
            else {
                _raiz = hijo;
            }


        }
        else if (a_eliminar._hijos == 2) {
            Nodo sucesor_inmediato = buscarSucesor(a_eliminar);
            T nuevo_valor = sucesor_inmediato._valor;

            //eliminamos el sucesor (lo hacemos primero para evitar que elimine al padre cuando peguemos el valor)
            eliminar(sucesor_inmediato._valor);

            //copiamos el valor del sucesor en el nodo a eliminar;
            a_eliminar._valor = nuevo_valor;

            //chanchada
            _cardinal++;

        }

        _cardinal--;
    }

    public String toString(){
        Nodo actual = buscarNodoPorElemento(this.minimo());
        String cadena = "{" + actual._valor  + ",";

        actual = buscarSucesor(actual);

        while (buscarSucesor(actual) != null) {
            cadena = cadena + actual._valor + ",";
            actual = buscarSucesor(actual);
        }

        return cadena + actual._valor + "}";
    
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = buscarNodoPorElemento(minimo());

        public boolean haySiguiente() {
            return buscarSucesor(_actual)._valor != null;
        }
    
        public T siguiente() {
            T res = _actual._valor;
            _actual = buscarSucesor(_actual);
            return res;
        }
    }

    public Iterador<T> iterador() {
        return new ABB_Iterador();
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

        //maximo no tiene sucesor
        if (actual._valor == maximo()) {
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

        return sucesor;
    }

    public Nodo buscarAntecesor(Nodo actual) {
        Nodo antecesor;

        //minimo no tiene antecesor
        if (actual._valor == minimo()) {
            antecesor = null;
        }

        else if (actual._izq != null) {
            // uno a la izquierda y todos a la derecha
            antecesor = actual._izq;

            while (antecesor._der != null) {
                antecesor = antecesor._der;
            }
        }
        else {
            //caso que el antecesor es a su vez ancestro.
            //primer ancestro izquierdo (el primer ancestro mas chico)
            //devuelve null si actual es raiz.
            antecesor = actual._padre;

            while (antecesor != null && antecesor._valor.compareTo(actual._valor) > 0){
                antecesor = antecesor._padre;
            }
        }
        
        return antecesor;
    }
    
    public Nodo raiz() {
        return _raiz;
    }

    private boolean ordenado(Nodo actual) { // debemos pasar la raiz;
        boolean valido_izq;
        boolean valido_der;

        if (actual == null) {
            return true;
        }
        
        if (actual._izq == null) {
            valido_izq = true;
        }
        else {
            valido_izq = actual._valor.compareTo(actual._izq._valor) > 0;
        }

        if (actual._der == null) {
            valido_der = true;
        }
        else {
            valido_der = actual._valor.compareTo(actual._der._valor) < 0;
        }

        return valido_der && valido_izq && ordenado(actual._izq) && ordenado(actual._der);
    }

    public boolean invariante(Nodo actual) {
        return ordenado(actual);
    }
}
