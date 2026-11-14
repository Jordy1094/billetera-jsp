package pe.edu.upn.billetera.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Movimiento implements Serializable {

    private static final long serialVersionUID = 1L;

    private final TipoMovimiento tipo;
    private final BigDecimal monto;

    public Movimiento(TipoMovimiento tipo, BigDecimal monto) {
        this.tipo = tipo;
        this.monto = monto;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getMontoTexto() {
        return monto.setScale(2, java.math.RoundingMode.HALF_UP).toPlainString();
    }

    public boolean isRecarga() {
        return tipo == TipoMovimiento.RECARGA;
    }
}
