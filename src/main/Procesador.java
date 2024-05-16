package main;

public class Procesador {
    private String id_procesador;
    private String codigo;
    private Boolean refrigerado;
    private Integer anio;

    public Procesador(String id_procesador, String codigo, Boolean refrigerado, Integer anio) {
        this.id_procesador = id_procesador;
        this.codigo = codigo;
        this.refrigerado = refrigerado;
        this.anio = anio;
    }

    public String getId_procesadores() {
        return id_procesador;
    }

    public void setId_procesadores(String id_procesadores) {
        this.id_procesador = id_procesadores;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getRefrigerado() {
        return refrigerado;
    }

    public void setRefrigerado(Boolean refrigerado) {
        this.refrigerado = refrigerado;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "Procesador [id_procesador=" + id_procesador + ", codigo=" + codigo + ", refrigerado="
                + refrigerado + ", anio=" + anio + "]";
    }
}
