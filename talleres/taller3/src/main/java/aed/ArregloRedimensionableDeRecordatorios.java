package aed;

class ArregloRedimensionableDeRecordatorios implements SecuenciaDeRecordatorios {
    private Recordatorio[] _data;

    public ArregloRedimensionableDeRecordatorios() { //osea que podemos tener dos funciones con el mismo nombre que reciban distintas cosas?
        _data = new Recordatorio[0];
    }

    public ArregloRedimensionableDeRecordatorios(ArregloRedimensionableDeRecordatorios vector) {
        Recordatorio[] copia = new Recordatorio[vector.longitud()];

        for (int i = 0; i < copia.length; i++) {
            copia[i] = vector.obtener(i);
        }

        _data = copia;
        
    }

    public int longitud() {
        return _data.length;
    }

    public void agregarAtras(Recordatorio i) {
        int new_length = _data.length + 1;
        Recordatorio[] new_data = new Recordatorio[new_length];

        for (int j = 0; j < _data.length; j++) {
            new_data[j] = _data[j];
        }

        new_data[_data.length] = i;

        _data = new_data;
    }

    public Recordatorio obtener(int i) {
        return _data[i];
    }

    public void quitarAtras() {
        int new_length = _data.length - 1;
        Recordatorio[] new_data = new Recordatorio[new_length];

        for (int i = 0; i < new_length; i++) {
            new_data[i] = _data[i];
        }

        _data = new_data;
    }

    public void modificarPosicion(int indice, Recordatorio valor) {
        _data[indice] = valor;
    }

    public ArregloRedimensionableDeRecordatorios copiar() {
        ArregloRedimensionableDeRecordatorios copia = new ArregloRedimensionableDeRecordatorios();

        for (int i = 0; i < _data.length; i++) {
            copia.agregarAtras(_data[i]);
        }

        return copia;
    }

}
