import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
//import java.util.*;
import java.sql.Timestamp;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class ShoeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void shoe_instantiatesCorrectly_true() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    assertEquals(true, testShoe instanceof Shoe);
  }

  @Test
  public void Shoe_instantiatesWithPrice_int() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    assertEquals(50, testShoe.getPrice());
  }

  @Test
  public void Shoe_instantiatesWithSize_int() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    assertEquals(6, testShoe.getSize());
  }

  @Test
  public void isPurchased_isFalseAfterInstantiation_false() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    assertEquals(false, testShoe.getPurchased());
  }

  @Test
  public void setPurchased_setsPurchasedToTrue_true() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    testShoe.save();
    testShoe.setPurchased();
    Shoe purchasedShoe = Shoe.all().get(0);
    assertEquals(true, purchasedShoe.getPurchased());
  }

  @Test
  public void setCustomerId_setCustomerId_true() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    testCustomer.save();
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    testShoe.save();
    testShoe.setCustomerId(testCustomer.getId());
    assertEquals(testShoe.getCustomerId(), testCustomer.getId());
  }

  // @Test
  // public void findByCustomerId_returnsShoesWithSameCustomerId_secondShoe() {
  //   Shoe firstShoe = new Shoe("Sneakers", 50, 6);
  //   firstShoe.save();
  //   Shoe secondShoe = new Shoe("Sandals", 30, 8);
  //   secondShoe.save();
  //   assertEquals(Shoe.findByCustomerId(secondShoe.getCustomerId()), secondShoe);
  // }

  @Test
  public void equals_returnsTrueIfNameAndPriceandSizeAreSame_true() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    Shoe anotherShoe = new Shoe("Sneakers", 50, 6);
    assertTrue(testShoe.equals(anotherShoe));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    testShoe.save();
    assertTrue(Shoe.all().get(0).equals(testShoe));
  }

  @Test
  public void save_assignsIdToShoe() {
    Shoe testShoe = new Shoe("Sneakers", 50, 6);
    testShoe.save();
    Shoe savedShoe = Shoe.all().get(0);
    assertEquals(savedShoe.getId(), testShoe.getId());
  }

  @Test
  public void all_returnsAllInstancesOfShoe_true() {
    Shoe firstShoe = new Shoe("Sneakers", 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 30, 8);
    secondShoe.save();
    assertEquals(true, Shoe.all().get(0).equals(firstShoe));
    assertEquals(true, Shoe.all().get(1).equals(secondShoe));
  }

  @Test
  public void all_returnsAllInstancesOfShoePuchased_true() {
    Shoe firstShoe = new Shoe("Sneakers", 50, 6);
    firstShoe.save();
    firstShoe.allShoePurchased();
    Shoe secondShoe = new Shoe("Sandals", 30, 8);
    secondShoe.save();
    secondShoe.allShoePurchased();
    assertEquals(true, Shoe.all().get(0).equals(firstShoe));
    assertEquals(true, Shoe.all().get(1).equals(secondShoe));
  }


  @Test
  public void find_returnsShoeWithSameId_secondShoe() {
    Shoe firstShoe = new Shoe("Sneakers", 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 30, 8);
    secondShoe.save();
    assertEquals(Shoe.find(secondShoe.getId()), secondShoe);
  }

  @Test
  public void findByName_returnsShoesWithSameName_secondShoe() {
    Shoe firstShoe = new Shoe("Sneakers", 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 30, 8);
    secondShoe.save();
    assertEquals(Shoe.findByName(secondShoe.getName()), secondShoe);
  }

  @Test
  public void findBySize_returnsShoesWithSameSize_secondShoe() {
    Shoe firstShoe = new Shoe("Sneakers", 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 30, 8);
    secondShoe.save();
    assertEquals(Shoe.findBySize(secondShoe.getSize()), secondShoe);
  }

  @Test
 public void update_updatesShoeDetails_true() {
   Shoe testShoe = new Shoe("Sneakers", 50, 6);
   testShoe.save();
   testShoe.update("Sandals", 30, 8);
   assertEquals("Sandals", Shoe.find(testShoe.getId()).getName());
   assertEquals(30, Shoe.find(testShoe.getId()).getPrice());
   assertEquals(8, Shoe.find(testShoe.getId()).getSize());
 }

 @Test
 public void delete_deletesShoe_true() {
   Shoe testShoe = new Shoe("Sneakers", 50, 6);
   testShoe.save();
   int testShoeId = testShoe.getId();
   testShoe.delete();
   assertEquals(null, Shoe.find(testShoeId));
 }

 @Test
 public void findByPrice_returnsShoes_secondShoe() {
   Shoe firstShoe = new Shoe("Sneakers", 50, 6);
   firstShoe.save();
   Shoe secondShoe = new Shoe("Sandals", 30, 8);
   secondShoe.save();
   assertEquals(Shoe.findByPrice(secondShoe.getPrice()), secondShoe);
 }

 @Test
 public void getTotalSales_returnSumTotalOfShoesSold_int() {
   Shoe firstShoe = new Shoe("Sneakers", 50, 6);
   firstShoe.save();
   firstShoe.setPurchased();
   Shoe secondShoe = new Shoe("Sandals", 30, 8);
   secondShoe.save();
   secondShoe.setPurchased();
   assertEquals(80, Shoe.getTotalSales());
 }

 @Test
 public void setTimePurchased_recordsTimePurchasedInDatabase() {
   Shoe testShoe = new Shoe("Sneakers", 50, 6);
   testShoe.save();
   testShoe.setTimePurchased();
   Timestamp savedTimePurchased = Shoe.find(testShoe.getId()).getTimePurchased();
   Timestamp rightNow = new Timestamp(new Date().getTime());
   assertEquals(DateFormat.getDateTimeInstance().format(rightNow), DateFormat.getDateTimeInstance().format(savedTimePurchased));
 }

 // @Test//same test as the top one - it passes/don't delete!!!
 //  public void setTimePurchased_recordsTimePurchasedInDatabase() {
 //    Shoe testShoe = new Shoe("Sneakers", 50, 6);
 //    testShoe.save();
 //    testShoe.setTimePurchased();
 //    Timestamp savedShoePurchased = testShoe.getTimePurchased();
 //
 //    Timestamp rightNow = new Timestamp(new Date().getTime());
 //    System.out.println("purchased shoe time:" + savedShoePurchased);
 //    System.out.println("time:" + rightNow);
 //    String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rightNow);
 //    String shoeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(savedShoePurchased);
 //    assertEquals(currentDate, shoeDate);
 //  }


  // @Test
  // public void findHistory_returnsShoes_secondShoe() {
  //   Shoe firstShoe = new Shoe("Sneakers", 1, 50, 6);
  //   firstShoe.save();
  //   firstShoe.setPurchased();
  //   Shoe secondShoe = new Shoe("Sandals", 1, 30, 8);
  //   secondShoe.save();
  //   System.out.println("time:" + secondShoe.getPurchased());
  //   secondShoe.setPurchased();
  //   System.out.println("time:" + secondShoe.getPurchased());
  //   List<Shoe> newShoeList = Arrays.asList(secondShoe);
  //   System.out.println("Purchased shoe array: " + Shoe.findHistory());
  //   System.out.println("newShoeList: " + newShoeList);
  //   Date date = new Date();
  //   System.out.println("date: " + date);
  //   assertEquals(true, Shoe.findHistory().get(1).equals(secondShoe));
  // }



}
