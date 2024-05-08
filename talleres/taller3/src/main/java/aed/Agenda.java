package aed;

public class Agenda {
    private Fecha _fecha;
    private ArregloRedimensionableDeRecordatorios _recordatorios = new ArregloRedimensionableDeRecordatorios();

    public Agenda(Fecha fechaActual) {
        _fecha = fechaActual;
    }

    public void agregarRecordatorio(Recordatorio recordatorio) {
        _recordatorios.agregarAtras(recordatorio);
    }

    @Override
    public String toString() {
        String new_string = "";
        new_string = new_string.concat(_fecha.toString() + "\n").concat("=====\n");

        for (int i = 0; i < _recordatorios.longitud(); i++) {

            if (_fecha.equals(_recordatorios.obtener(i).fecha())) {
                new_string = new_string.concat(_recordatorios.obtener(i).toString()).concat("\n");
            }
        }

        return new_string;
    }

    public void incrementarDia() {
        _fecha.incrementarDia();
    }

    public Fecha fechaActual() {
        return new Fecha(_fecha.dia(), _fecha.mes());
    }

}
