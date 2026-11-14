package pe.edu.upn.billetera.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Billetera implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final BigDecimal SALDO_INICIAL = new BigDecimal("250.00");

    private BigDecimal saldo = SALDO_INICIAL;
    private final List<Movimiento> movimientos = new ArrayList<>();

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Movimiento> getMovimientos() {
        return Collections.unmodifiableList(movimientos);
    }

    public String getSaldoTexto() {
        return saldo.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public int getCantidadMovimientos() {
        return movimientos.size();
    }

    public String registrar(String tipoTexto, String montoTexto) {
        TipoMovimiento tipo = TipoMovimiento.desde(tipoTexto);
        if (tipo == null) {
            return "Seleccione un tipo de movimiento válido (Recarga o Gasto).";
        }

        if (montoTexto == null || montoTexto.trim().isEmpty()) {
            return "Ingrese un monto.";
        }

        BigDecimal monto;
        try {
            monto = new BigDecimal(montoTexto.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return "El monto debe ser un número válido.";
        }

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return "El monto debe ser mayor que cero.";
        }
        if (tipo == TipoMovimiento.GASTO && monto.compareTo(saldo) > 0) {
            return "Saldo insuficiente";
        }

        saldo = (tipo == TipoMovimiento.RECARGA) ? saldo.add(monto) : saldo.subtract(monto);
        movimientos.add(0, new Movimiento(tipo, monto));
        return null;
    }
}
