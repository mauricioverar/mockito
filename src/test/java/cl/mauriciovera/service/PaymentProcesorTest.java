package cl.mauriciovera.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
//Mockito
import static org.mockito.BDDMockito.*; // BDDMockito para usar given(...).willReturn(...)
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import cl.mauriciovera.interfaces.PaymentMethod;//
import cl.mauriciovera.model.Payment;
import cl.mauriciovera.model.PaymentHistory;
import cl.mauriciovera.model.User;

// activación de Mockito
@ExtendWith(MockitoExtension.class) // Extender con MockitoExtension para usar Mockito
public class PaymentProcesorTest {

  @Mock // objeto simulado (mock) de PaymentMethod
  private PaymentMethod creditCardPayment; // no importar interface, solo el mock

  @Mock
  private PaymentMethod bankTransferPayment;

  @Mock // objeto simulado (mock) de PaymentHistory
  private PaymentHistory paymentHistory;

  @Captor // captor para capturar el argumento pasado al método add de PaymentHistory
  // creará un ArgumentCaptor
  private ArgumentCaptor<Payment> paymentCaptor; // capturar el pago agregado ********

  @InjectMocks // PaymentProcessor es la clase que se va a probar, y se inyectan los mocks
  private PaymentProcessor paymentProcessor;


  private User user;

  @BeforeEach
  void setUp() {
    user = new User("Pedro Perez"); // us de prueba
    paymentProcessor = new PaymentProcessor(creditCardPayment, bankTransferPayment, paymentHistory);
  }

  @Test
  void testProcesoDePago_MontoInvalido() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      paymentProcessor.processPayment(0, user, "CreditCard"); // monto inválido
    });

    // assertTrue(exception.getMessage().contains("Invalid amount or user")); //
    // contiene el mensaje de error

    assertEquals("Invalid amount or user", exception.getMessage()); // exactamente
  }

  @Test
  void testProcesoDePago_UsuarioInvalido() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      paymentProcessor.processPayment(100, null, "CreditCard");
    });

    assertTrue(exception.getMessage().contains("Invalid amount or user"));
  }

  @Test
  void testProcesoDePago_TarjetaDeCredito_Exitoso() {
    given(creditCardPayment.process(100.0, user)).willReturn(true); // llamar a la data simulada
    boolean result = paymentProcessor.processPayment(100, user, "CreditCard"); // metodo CreditCard
    assertTrue(result); // verificar que el resultado sea verdadero


    verify(creditCardPayment).process(100.0, user); // verificar que se haya llamado al mock con los parámetros
                                                    // correctos
    //verify(paymentHistory).add(any(Payment.class)); // verificar solo que se haya llamado el método
    verify(paymentHistory).add(paymentCaptor.capture()); // capturar y verificar
    // el valor real del argumento
  }

  @Test
  void testProcesoDePago_TransferenciaBancaria_Exitoso() {
    given(bankTransferPayment.process(200.0, user)).willReturn(true); // llamar a la data simulada
    boolean result = paymentProcessor.processPayment(200, user, "BankTransfer"); // metodo BankTransfer
    assertTrue(result); // verificar que el resultado sea verdadero

    verify(bankTransferPayment).process(200.0, user); // verificar que se haya llamado al mock con los parámetros correctos
    verify(paymentHistory).add(any(Payment.class)); // verificar solo que se haya llamado el método
  }

  @Test
  void testProcesoDePago_MetodoDesconocido() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      paymentProcessor.processPayment(100, user, "UnknownMethod"); // método desconocido
    });

    assertTrue(exception.getMessage().contains("Unknown payment method")); // verificar mensaje de error
  }

  @Test
  void testProcesoDePago_NoDeberiaAgregarPago_CuandoElResultadoEsFalso() {
    boolean result = false;
    // Ejecuta el método que contiene el if
    // Verifica que paymentHistory no haya cambiado
    given(creditCardPayment.process(100.0, user)).willReturn(result);
    boolean paymentResult = paymentProcessor.processPayment(100, user, "CreditCard");
    assertTrue(!paymentResult); // Verifica que el resultado sea falso
    verify(paymentHistory, never()).add(any(Payment.class)); // Verifica que no se haya agregado un pago al PaymentHistory, 
    // verify para indicar que el metodo del mock es llamado con parámetros específicos
    // never para indicar que no se debe llamar al método
    // any para indicar que no importa el argumento, cualquier Payment
  }


  @Test
  void testObtenerHistorialDePago_RetornarInstanciaInyectada() {
    PaymentHistory returnedHistory = paymentProcessor.getPaymentHistory();
    assertSame(paymentHistory, returnedHistory); // Verifica que es la misma instancia
  }
}

// mvn clean test
// LiveServer target/site/jacoco/index.html