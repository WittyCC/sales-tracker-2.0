import org.sql2o.*;
import java.util.ArrayList;
import java.util.List;

public class Customer {
  private String name;
  private String address;
  private String email;
  private int id;

  public Customer(String name, String address, String email) {
    this.name = name;
    this.address = address;
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getEmail() {
    return email;
  }

  public int getId() {
    return id;
  }

  public static List<Customer> all() {
    String sql = "SELECT * FROM customers";
    try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Customer.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO customers (name, address, email) VALUES (:name, :address, :email)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("address", this.address)
        .addParameter("email", this.email)
        .executeUpdate()
        .getKey();
    }
  }

  public static Customer find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM customers where id=:id";
      Customer customer = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Customer.class);
      return customer;
    }
  }

  @Override
  public boolean equals(Object otherCustomer){
    if (!(otherCustomer instanceof Customer)) {
      return false;
    } else {
      Customer newCustomer = (Customer) otherCustomer;
      return this.getName().equals(newCustomer.getName()) &&
             this.getAddress().equals(newCustomer.getAddress()) &&
             this.getEmail().equals(newCustomer.getEmail());
    }
  }

  public List<Shoe> getShoes() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where customerId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Shoe.class);
    }
  }

  public void update(String name, String address, String email) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE customers SET name = :name, address = :address, email = :email WHERE id = :id";
      con.createQuery(sql)
        .addParameter("name", name)
        .addParameter("address", address)
        .addParameter("email", email)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM customers WHERE id = :id;";
    con.createQuery(sql)
      .addParameter("id", id)
      .executeUpdate();
    }
  }
}
