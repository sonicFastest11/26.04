package software;


import domainapp.basics.exceptions.NotPossibleException;
import domainapp.basics.software.DomainAppToolSoftware;
import model.Address;
import model.Coffee;
import model.Customer;
import model.Importer;
import model.ImportOrder;
import model.Supplier;
import model.TypeOfCoffee;



/**
 * @overview 
 *  Encapsulate the basic functions for setting up and running a software given its domain model.  
 *  
 * @author dmle
 *
 * @version 
 */
public class CoffeeSoftware extends DomainAppToolSoftware {
  
  // the domain model of software
  private static final Class[] model = {
      Coffee.class, 
      Customer.class,
      TypeOfCoffee.class,
      Address.class,
      Supplier.class, 
      ImportOrder.class, 
      Importer.class

      // reports
 
  };
  
  /* (non-Javadoc)
   * @see vn.com.courseman.software.Software#getModel()
   */
  /**
   * @effects 
   *  return {@link #model}.
   */
  @Override
  protected Class[] getModel() {
    return model;
  }

  /**
   * The main method
   * @effects 
   *  run software with a command specified in args[0] and with the model 
   *  specified by {@link #getModel()}. 
   *  
   *  <br>Throws NotPossibleException if failed for some reasons.
   */
  public static void main(String[] args) throws NotPossibleException {
    new CoffeeSoftware().exec(args);
  }
}

