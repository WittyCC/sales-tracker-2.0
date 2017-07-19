import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class CustomerTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void customer_instantiatesCorrectly_true() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    assertEquals(true, testCustomer instanceof Customer);
  }

  @Test
  public void getName_customerInstantiatesWithName_JohnDoe() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    assertEquals("John Doe", testCustomer.getName());
  }

  @Test
  public void getAddress_customerInstantiatesWithAddress_String() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    assertEquals("101 W Olympic Pl, Seattle", testCustomer.getAddress());
  }

  @Test
  public void getEmail_customerInstantiatesWithEmail_String() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    assertEquals("jdoe@jdoe.com", testCustomer.getEmail());
  }

  @Test
  public void equals_returnsTrueIfNameAddressAndEmailAreSame_true() {
    Customer firstCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    Customer secondCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    assertTrue(firstCustomer.equals(secondCustomer));
  }

  @Test
  public void save_insertsObjectIntoDatabase_Customer() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    testCustomer.save();
    assertTrue(Customer.all().get(0).equals(testCustomer));
  }

  @Test
  public void all_returnsAllInstancesOfCustomer_true() {
    Customer firstCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    firstCustomer.save();
    Customer secondCustomer = new Customer("Jane Doe", "102 W Olympic Pl, Seattle", "janedoe@janedoe.com");
    secondCustomer.save();
    assertEquals(true, Customer.all().get(0).equals(firstCustomer));
    assertEquals(true, Customer.all().get(1).equals(secondCustomer));
  }

  @Test
  public void save_assignsIdToObject() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    testCustomer.save();
    Customer savedCustomer = Customer.all().get(0);
    assertEquals(testCustomer.getId(), savedCustomer.getId());
  }

  @Test
  public void find_returnsCustomerWithSameId_secondCustomer() {
    Customer firstCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    firstCustomer.save();
    Customer secondCustomer = new Customer("Jane Doe", "102 W Olympic Pl, Seattle", "janedoe@janedoe.com");
    secondCustomer.save();
    assertEquals(Customer.find(secondCustomer.getId()), secondCustomer);
  }

  // @Test
  // public void getShoes_retrievesAllShoesFromDatabase_shoesList() {
  //   Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
  //   testCustomer.save();
  //   Shoe firstShoe = new Shoe("Sneakers", testCustomer.getId(), 50, 6);
  //   firstShoe.save();
  //   Shoe secondShoe = new Shoe("Sandals", testCustomer.getId(), 30, 8);
  //   secondShoe.save();
  //   Shoe[] shoes = new Shoe[] { firstShoe, secondShoe };
  //   assertTrue(testCustomer.getShoes().containsAll(Arrays.asList(shoes)));
  // }

  @Test
  public void update_updatesCustomerDetails_true() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    testCustomer.save();
    testCustomer.update("Jane Doe", "102 W Olympic Pl, Seattle", "janedoe@janedoe.com");
    assertEquals("Jane Doe", Customer.find(testCustomer.getId()).getName());
    assertEquals("102 W Olympic Pl, Seattle", Customer.find(testCustomer.getId()).getAddress());
    assertEquals("janedoe@janedoe.com", Customer.find(testCustomer.getId()).getEmail());
  }

  @Test
  public void delete_deletesCustomer_true() {
    Customer testCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    testCustomer.save();
    int testCustomerId = testCustomer.getId();
    testCustomer.delete();
    assertEquals(null, Customer.find(testCustomerId));
  }

  @Test
  public void findByName_returnsCustomersWithSameName_secondShoe() {
    Customer firstCustomer = new Customer("John Doe", "101 W Olympic Pl, Seattle", "jdoe@jdoe.com");
    firstCustomer.save();
    Customer secondCustomer = new Customer("Jane Doe", "102 W Olympic Pl, Seattle", "janedoe@janedoe.com");
    secondCustomer.save();
    assertEquals(Customer.findByName(secondCustomer.getName()), secondCustomer);
  }
}
