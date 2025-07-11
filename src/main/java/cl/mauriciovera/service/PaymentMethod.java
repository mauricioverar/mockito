package cl.mauriciovera.service;

import cl.mauriciovera.model.User;

// Esta interfaz es clave para la inyección de dependencias y el uso de mocks
public interface PaymentMethod {
  // Método para procesar un pago. En los tests, el mock de este método es
  // configurado con given(...).willReturn(...)
  boolean process(double amount, User user);

  // Nombre del método de pago
  String getMethodName();
}
