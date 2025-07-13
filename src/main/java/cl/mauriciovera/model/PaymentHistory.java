package cl.mauriciovera.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentHistory {
    private final List<Payment> payments = new ArrayList<>();

    // Método para agregar un pago al historial
    public void add(Payment payment) { // metodo add con tipado extricto(Payment payment)
        payments.add(payment);
    }

    // Método para obtener una copia inmutable del historial de pagos
    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }
}
