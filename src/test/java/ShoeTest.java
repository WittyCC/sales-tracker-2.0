import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class ShoeTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void shoe_instantiatesCorrectly_true() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    assertEquals(true, testShoe instanceof Shoe);
  }

  @Test
  public void Shoe_instantiatesWithCustomerId_int() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    assertEquals(1, testShoe.getCustomerId());
  }

  @Test
  public void Shoe_instantiatesWithPrice_int() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    assertEquals(50, testShoe.getPrice());
  }

  @Test
  public void Shoe_instantiatesWithSize_int() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    assertEquals(6, testShoe.getSize());
  }

  @Test
  public void equals_returnsTrueIfNameAndCustomerIdandPriceandSizeAreSame_true() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    Shoe anotherShoe = new Shoe("Sneakers", 1, 50, 6);
    assertTrue(testShoe.equals(anotherShoe));
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    testShoe.save();
    assertTrue(Shoe.all().get(0).equals(testShoe));
  }

  @Test
  public void save_assignsIdToShoe() {
    Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
    testShoe.save();
    Shoe savedShoe = Shoe.all().get(0);
    assertEquals(savedShoe.getId(), testShoe.getId());
  }

  @Test
  public void all_returnsAllInstancesOfShoe_true() {
    Shoe firstShoe = new Shoe("Sneakers", 1, 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 1, 30, 8);
    secondShoe.save();
    assertEquals(true, Shoe.all().get(0).equals(firstShoe));
    assertEquals(true, Shoe.all().get(1).equals(secondShoe));
  }

  @Test
  public void find_returnsShoeWithSameId_secondShoe() {
    Shoe firstShoe = new Shoe("Sneakers", 1, 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 1, 30, 8);
    secondShoe.save();
    assertEquals(Shoe.find(secondShoe.getId()), secondShoe);
  }

  @Test
  public void findByName_returnsShoesWithSameName_secondShoe() {
    Shoe firstShoe = new Shoe("Sneakers", 1, 50, 6);
    firstShoe.save();
    Shoe secondShoe = new Shoe("Sandals", 1, 30, 8);
    secondShoe.save();
    assertEquals(Shoe.findByName(secondShoe.getName()), secondShoe);
  }

  @Test
  public void save_savesCustomerIdIntoDB_true() {
    Customer testCustomer = new Customer("Jane Doe", "101 W Olympic Pl, Seattle", "janedoe@janedoe.com");
    testCustomer.save();
    Shoe testShoe = new Shoe("Sneakers", testCustomer.getId(), 50, 6);
    testShoe.save();
    Shoe savedShoe = Shoe.find(testShoe.getId());
    assertEquals(savedShoe.getCustomerId(), testCustomer.getId());
  }

  @Test
 public void update_updatesShoeDetails_true() {
   Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
   testShoe.save();
   testShoe.update("Sandals", 30, 8);
   assertEquals("Sandals", Shoe.find(testShoe.getId()).getName());
   assertEquals(30, Shoe.find(testShoe.getId()).getPrice());
   assertEquals(8, Shoe.find(testShoe.getId()).getSize());
 }

 @Test
 public void delete_deletesShoe_true() {
   Shoe testShoe = new Shoe("Sneakers", 1, 50, 6);
   testShoe.save();
   int testShoeId = testShoe.getId();
   testShoe.delete();
   assertEquals(null, Shoe.find(testShoeId));
 }
}
