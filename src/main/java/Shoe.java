import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class Shoe {
  private String name;
  private int customerId;
  private int price;
  private int size;
  private int id;

  public Shoe(String name, int customerId, int price, int size) {
    this.name = name;
    this.customerId = customerId;
    this.price = price;
    this.size = size;
  }

  public String getName(){
    return name;
  }

  public int getCustomerId(){
    return customerId;
  }

  public int getPrice(){
    return price;
  }

  public int getSize(){
    return size;
  }

  public int getId(){
    return id;
  }

  @Override
  public boolean equals(Object otherShoe){
    if (!(otherShoe instanceof Shoe)) {
      return false;
    } else {
      Shoe newShoe = (Shoe) otherShoe;
      return this.getName().equals(newShoe.getName()) &&
             this.getCustomerId() == newShoe.getCustomerId() &&
             this.getPrice() == newShoe.getPrice() &&
             this.getSize() == newShoe.getSize();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO shoes (name, customerid, price, size) VALUES (:name, :customerId, :price, :size)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("customerId", this.customerId)
        .addParameter("price", this.price)
        .addParameter("size", this.size)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Shoe> all() {
   String sql = "SELECT * FROM shoes";
   try(Connection con = DB.sql2o.open()) {
     return con.createQuery(sql).executeAndFetch(Shoe.class);
   }
 }

  public static Shoe find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where id=:id";
      Shoe shoe = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  public static Shoe findByName(String name) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where name=:name";
      Shoe shoe = con.createQuery(sql)
        .addParameter("name", name)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  public void update(String name, int price, int size) {
   try(Connection con = DB.sql2o.open()) {
     String sql = "UPDATE shoes SET name = :name, price = :price, size = :size WHERE id = :id";
     con.createQuery(sql)
       .addParameter("name", name)
       .addParameter("price", price)
       .addParameter("size", size)
       .addParameter("id", id)
       .executeUpdate();
   }
  }

public void delete() {
   try(Connection con = DB.sql2o.open()) {
   String sql = "DELETE FROM shoes WHERE id = :id;";
   con.createQuery(sql)
     .addParameter("id", id)
     .executeUpdate();
   }
  }
}
