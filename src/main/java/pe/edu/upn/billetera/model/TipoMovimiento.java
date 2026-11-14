package pe.edu.upn.billetera.model;

public enum TipoMovimiento {

    RECARGA("Recarga"),
    GASTO("Gasto");

    private final String etiqueta;

    TipoMovimiento(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public static TipoMovimiento desde(String valor) {
        if (valor == null) {
            return null;
        }
        for (TipoMovimiento tipo : values()) {
            if (tipo.name().equalsIgnoreCase(valor.trim())) {
                return tipo;
            }
        }
        return null;
    }
}
