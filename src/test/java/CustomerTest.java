import org.junit.*;
import static org.junit.Assert.*;

public class CustomerTest {

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
}
