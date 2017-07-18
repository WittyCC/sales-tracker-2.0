import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Shoe {
  private String name;
  private int customerId;
  private int price;
  private int size;
  private int id;
  private Timestamp purchased;

  public Shoe(String name, int customerId, int price, int size) {
    this.name = name;
    this.customerId = customerId;
    this.price = price;
    this.size = size;
    // this.purchased = 0000-00-00 00-00-00;
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

  public void setPurchased(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE shoes SET purchased = now() WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public Timestamp getPurchased(){
    return purchased;
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
      String sql = "INSERT INTO shoes (name, customerid, price, size, purchased) VALUES (:name, :customerId, :price, :size, :purchased)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("customerId", this.customerId)
        .addParameter("price", this.price)
        .addParameter("size", this.size)
        .addParameter("purchased", this.purchased)
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

  public static Shoe findBySize(int size) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where size=:size";
      Shoe shoe = con.createQuery(sql)
        .addParameter("size", size)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  public static Shoe findByPrice(int price) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where price<=:price";
      Shoe shoe = con.createQuery(sql)
        .addParameter("price", price)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  public static Shoe findByCustomerId(int customerId) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where customerId=:customerId";
      Shoe shoe = con.createQuery(sql)
        .addParameter("customerId", customerId)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  public static List<Shoe> findHistory() {
    String sql = "SELECT * FROM shoes WHERE purchased IS NOT NULL;";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Shoe.class);
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
