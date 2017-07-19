import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class Shoe {
  private String name;
  private int price;
  private int size;
  private int id;
  private boolean purchased;
  private Timestamp timePurchased;
  private int customerId;
  //private static int totalSales = 50;



  public Shoe(String name, int price, int size) {
    this.name = name;
    this.price = price;
    this.size = size;
    this.purchased = false;
    this.customerId = -1;
  }

  public String getName(){
    return name;
  }

  public int getCustomerId(){

    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes WHERE customerId = :customerId";
      Shoe shoe = con.createQuery(sql)
      .addParameter("customerId", customerId)
      .executeAndFetchFirst(Shoe.class);
      return customerId;
    }
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

  public boolean getPurchased() {
    return purchased;
  }

  public static int getTotalSales() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT SUM (price) FROM shoes where purchased = true";
      return (int) con.createQuery(sql)
      .executeScalar(Integer.class);
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

  public void setPurchased(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE shoes SET purchased = true WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void setCustomerId(int customerid){
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE shoes SET customerid = :customerid WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .addParameter("customerid", customerid)
        .executeUpdate();
    }
  }

  // public Timestamp getPurchased(){
  //   return purchased;
  // }

  @Override
  public boolean equals(Object otherShoe){
    if (!(otherShoe instanceof Shoe)) {
      return false;
    } else {
      Shoe newShoe = (Shoe) otherShoe;
      return this.getName().equals(newShoe.getName()) &&
             this.getPrice() == newShoe.getPrice() &&
             this.getSize() == newShoe.getSize();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO shoes (name, price, size, purchased, customerid) VALUES (:name, :price, :size, :purchased, :customerid)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("price", this.price)
        .addParameter("size", this.size)
        .addParameter("purchased", purchased)
        .addParameter("customerid", customerId)
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

 public static List<Shoe> allShoePurchased() {
  String sql = "SELECT * FROM shoes WHERE purchased = true";
  try(Connection con = DB.sql2o.open()) {
    return con.createQuery(sql).executeAndFetch(Shoe.class);
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
//add list
  public static Shoe findBySize(int size) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where size=:size";
      Shoe shoe = con.createQuery(sql)
        .addParameter("size", size)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }
//add list
  public static Shoe findByPrice(int price) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM shoes where price<=:price";
      Shoe shoe = con.createQuery(sql)
        .addParameter("price", price)
        .executeAndFetchFirst(Shoe.class);
        return shoe;
    }
  }

  // public static Shoe findByCustomerId(int customerId) {
  //   try(Connection con = DB.sql2o.open()) {
  //     String sql = "SELECT * FROM shoes where customerId=:customerId";
  //     Shoe shoe = con.createQuery(sql)
  //       .addParameter("customerId", customerId)
  //       .executeAndFetchFirst(Shoe.class);
  //       return shoe;
  //   }
  // }

  // public static List<Shoe> findHistory() {
  //   String sql = "SELECT * FROM shoes WHERE purchased IS NOT NULL;";
  //   try(Connection con = DB.sql2o.open()) {
  //     return con.createQuery(sql).executeAndFetch(Shoe.class);
  //   }
  // }


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
