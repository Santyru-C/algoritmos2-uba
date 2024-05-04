package aed;

public class Recordatorio {

    private String _mensaje;
    private Fecha _fecha;
    private Horario _horario;

    public Recordatorio(String mensaje, Fecha fecha, Horario horario) {
        this._mensaje = mensaje;
        this._fecha = new Fecha(fecha.dia(), fecha.mes());
        this._horario = new Horario(horario.hora(), horario.minutos());
    }

    public Horario horario() {
        return _horario;
    }

    public Fecha fecha() {
        return new Fecha(this._fecha.dia(), this._fecha.mes()); // preguntar sobre esto
    }

    public String mensaje() {
        return _mensaje;
    }

    @Override
    public String toString() {
        String formated = String.format("%s @ %s %s", _mensaje, _fecha.toString(), _horario.toString());
        return formated;
    }

    @Override
    public boolean equals(Object otro) {
        boolean oen = (otro == null);
        boolean cd = otro.getClass() != this.getClass();

        if (oen || cd) {
            return false;
        }

        Recordatorio otroRecordatorio = (Recordatorio) otro;

        return (this._horario.equals(otroRecordatorio.horario()) && 
                this._fecha.equals(otroRecordatorio.fecha()) &&
                this._mensaje == otroRecordatorio.mensaje());
    }

}
