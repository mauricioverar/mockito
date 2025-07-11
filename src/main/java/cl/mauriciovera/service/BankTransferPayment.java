package cl.mauriciovera.service;

import cl.mauriciovera.model.User;

public class BankTransferPayment implements PaymentMethod {
  @Override
  public boolean process(double amount, User user) {
    // Aquí iría la lógica real de procesamiento por transferencia
    // En los tests, este método es simulado por el mock
    return true;
  }

  @Override
  public String getMethodName() {
    return "BankTransfer";
  }

}
