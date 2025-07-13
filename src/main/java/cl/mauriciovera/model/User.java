package cl.mauriciovera.model;

public class User {
  private String name;

  // Constructor para inicializar el nombre del usuario
  public User(String name) {
    this.name = name;
  }

  // Método para obtener el nombre del usuario
  public String getName() {
    return name;
  }
}
