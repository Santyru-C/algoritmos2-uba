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

        //actualizamos de entrada los nuevos valores de _min y _max en caso necesario.
        if (a_eliminar == _min) {
            _min = buscarSucesor(a_eliminar)._valor;
        }

        if (a_eliminar == _max) {
            _max = buscarAntecesor(a_eliminar)._valor;
        }


        if (a_eliminar == null) { //si no esta...
            ;
        }
        else if (a_eliminar._hijos == 0) { //no tiene hijos
            //podria hacer una funcion que anule algun nodo descendiente si este existe
            if (a_eliminar._padre != null) {
                if (a_eliminar._valor.compareTo(a_eliminar._padre._valor) < 0) {
                    a_eliminar._padre._izq = null;
                }
                else {
                    a_eliminar._padre._der = null;
                }
            }
        }
        else if (a_eliminar._hijos == 1) {  
            Nodo hijo = a_eliminar._der == null? a_eliminar._izq : a_eliminar._der;

            if (a_eliminar._padre != null) { //evitamos errores en el caso de que sea raiz la eliminada
                a_eliminar._padre.agregarHijo(hijo);
            }
            else {
                _raiz = hijo;
            }

            hijo._padre = a_eliminar._padre;
        }
        else if (a_eliminar._hijos == 2) {
            Nodo sucesor = buscarSucesor(a_eliminar);

            //nos aseguramos de pasar una referencia de EL hijo del sucesor al padre de este.
            //"cortamos" la referencia de el sucesor a su anterior padre
            //con este if else nos aseguramos que podamos utilizar tanto el sucesor como el antecesor inmmediato
            sucesor._padre._izq = sucesor._der;

/*             if (sucesor._valor.compareTo(sucesor._padre._valor) < 0) {
                sucesor._padre._izq = sucesor._der;
            }
            else {
                sucesor._padre._der = sucesor._izq;
            } */
            
            //establecemos el nuevo padre al sucesor
            sucesor._padre = a_eliminar._padre;

            sucesor._izq = a_eliminar._izq;
            sucesor._der = a_eliminar._der;

            if (a_eliminar._izq != null) { //establecemos el nuevo nodo como padre de los hijos anteriores.
                a_eliminar._izq._padre = sucesor;
            }

            if (a_eliminar._der !=null) {
                a_eliminar._der._padre = sucesor;
            }

            if (a_eliminar._padre != null) {
                a_eliminar._padre.agregarHijo(sucesor);
            } 
            else {
                _raiz = sucesor;
            }
            //estoy perdiendo referencias, solucionar eso.
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
