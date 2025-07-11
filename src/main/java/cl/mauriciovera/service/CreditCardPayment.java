package cl.mauriciovera.service;

import cl.mauriciovera.model.User;

public class CreditCardPayment implements PaymentMethod {
  @Override
  public boolean process(double amount, User user) {
    // Aquí iría la lógica real de procesamiento con tarjeta
    // En los tests, este método es simulado por el mock
    return true;
  }

  @Override
  public String getMethodName() {
    return "CreditCard"; // Devuelve el nombre del método de pago
  }
}
