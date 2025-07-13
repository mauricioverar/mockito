package cl.mauriciovera.model;

public class Payment {
  private final double amount;
  private final User user;
  private final String method;
  private final java.time.LocalDateTime date;

  // Constructor para inicializar los campos de Payment
  public Payment(double amount, User user, String method, java.time.LocalDateTime date) {
    this.amount = amount;
    this.user = user;
    this.method = method;
    this.date = date;
  }

  // Métodos para obtener los valores de los campos
  public double getAmount() {
    return amount;
  }

  public User getUser() {
    return user;
  }

  public String getMethod() {
    return method;
  }

  public java.time.LocalDateTime getDate() {
    return date;
  }
}
