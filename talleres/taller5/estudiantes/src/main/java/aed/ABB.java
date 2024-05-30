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

        //establecemos maximo y minimo
        actualizarMaximoYMinimo(elem);
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
            }

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
        Nodo actual = buscarNodoPorElemento(_min);
        String cadena = "{" + actual._valor  + ",";

        actual = buscarSucesor(actual);

        while (buscarSucesor(actual) != null) {
            cadena = cadena + actual._valor + ",";
            actual = buscarSucesor(actual);
        }

        return cadena + actual._valor + "}";
    
    }

    private class ABB_Iterador implements Iterador<T> {
        private Nodo _actual = buscarNodoPorElemento(_min);

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

        //maximo no tiene sucesor
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

        return sucesor;
    }

    public Nodo buscarAntecesor(Nodo actual) {
        Nodo antecesor;

        //minimo no tiene antecesor
        if (actual._valor == _min) {
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
}
