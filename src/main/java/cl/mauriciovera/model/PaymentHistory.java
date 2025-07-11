package cl.mauriciovera.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PaymentHistory {
private final List<Payment> payments = new ArrayList<>();

    public void add(Payment payment) { // metodo add con tipado extricto(Payment payment)
        payments.add(payment);
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }
}
