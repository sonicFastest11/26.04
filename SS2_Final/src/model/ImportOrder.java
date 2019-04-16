package model;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.util.Tuple;

public class ImportOrder implements Comparable {

	@DAttr(name = "id", id = true, auto = true, type = Type.String, length = 6, mutable = false, optional = false)
	private String id;

	@DAttr(name = "date", type = Type.String, length = 30, optional = false)
	private String date;

	@DAttr(name = "brandOfCoffee", type = Type.String, length = 30, optional = false)
	private String brandOfCoffee;

	@DAttr(name = "typeOfCoffee", type = Type.Domain, optional = false, length = 6)
	@DAssoc(ascName = "order-has-typeOfCoffee", ascType = AssocType.One2Many, endType = AssocEndType.Many, role = "order", associate = @Associate(cardMax = 25, cardMin = 1, type = TypeOfCoffee.class))
	private TypeOfCoffee typeOfCoffee;

	@DAttr(name = "importer", type = Type.Domain, optional = false, length = 6)
	@DAssoc(ascName = "order-has-import", ascType = AssocType.One2Many, endType = AssocEndType.Many, role = "order", associate = @Associate(cardMax = 25, cardMin = 1, type = Importer.class))
	private Importer importer;

	@DAttr(name = "quantity", length = 10, optional = false, type = Type.Integer)
	private Integer quantity;
	@DAttr(name = "unitPrice", length = 10, optional = false, type = Type.Integer)
	private Integer unitPrice;

	private static int idCounter = 0;
	
	 @DAttr(name = "coffee", type = Type.Domain, length = 5, optional = false)
	  @DAssoc(ascName = "coffee-has-importOrders", role = "importOrder", 
	    ascType = AssocType.One2Many, endType = AssocEndType.Many, 
	    associate = @Associate(type = Coffee.class, cardMin = 1, cardMax = 1), dependsOn = true)
	  private Coffee coffee;
	
	@DOpt(type=DOpt.Type.DataSourceConstructor)
	public ImportOrder(String id, String date, String brandOfCoffee, TypeOfCoffee typeOfCoffee, Importer importer,
			Integer quantity, Integer unitPrice, Coffee coffee) {
		this.id = nextID(id);
		this.date = date;
		this.brandOfCoffee = brandOfCoffee;
		this.typeOfCoffee = typeOfCoffee;
		this.importer = importer;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.coffee = coffee;

	}
	
//	@DOpt(type=DOpt.Type.ObjectFormConstructor)
//	  @DOpt(type=DOpt.Type.RequiredConstructor)
//	  public ImportOrder(@AttrRef("coffee") Coffee coffee 
//	      ) throws ConstraintViolationException {
//	    this(null, null ,null , null, null, null, null, coffee);
//	  }
	
	@DOpt(type=DOpt.Type.ObjectFormConstructor)
	 @DOpt(type=DOpt.Type.RequiredConstructor)
	public ImportOrder(@AttrRef("date") String date, @AttrRef("brandOfCoffee") String brandOfCoffee,
			@AttrRef("typeOfCoffee") TypeOfCoffee typeOfCoffee, @AttrRef("importer") Importer importer,
			@AttrRef("quantity") Integer quantity, @AttrRef("unitPrice") Integer unitPrice,@AttrRef("coffee") Coffee coffee ) {
		this(null, date, brandOfCoffee, typeOfCoffee, importer, quantity, unitPrice,coffee);
	}

	public String getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBrandOfCoffee() {
		return brandOfCoffee;
	}

	public void setBrandOfCoffee(String brandOfCoffee) {
		this.brandOfCoffee = brandOfCoffee;
	}

	public TypeOfCoffee getTypeOfCoffee() {
		return typeOfCoffee;
	}

	public void setTypeOfCoffee(TypeOfCoffee typeOfCoffee) {
		this.typeOfCoffee = typeOfCoffee;
	}

	public Importer getImporter() {
		return importer;
	}

	public void setImporter(Importer importer) {
		this.importer = importer;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	


	public void setCoffee(Coffee coffee) {
		this.coffee = coffee;
	}
	public Coffee getCoffee() {
		return coffee;
	}

	@Override
	public String toString() {
		return "Order(" + id + "," + date + "," + brandOfCoffee + "," + typeOfCoffee + "," + importer + "," + quantity
				+ "," + unitPrice + "," + coffee + ")";
	}

	public String nextID(String id) throws ConstraintViolationException {
		if (id == null) { // generate a new id
			idCounter++;

			return "OI" + idCounter;
		} else {
			// update id
			int num;
			try {
				num = Integer.parseInt(id.substring(2));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { id });
			}

			if (num > idCounter) {
				idCounter = num;
			}

			return id;
		}
	}

	/**
	 * @requires minVal != null /\ maxVal != null
	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
	 */
	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
			throws ConstraintViolationException {

		if (minVal != null && maxVal != null) {
			// TODO: update this for the correct attribute if there are more than one auto
			// attributes of this class

			String maxId = (String) maxVal;

			try {
				int maxIdNum = Integer.parseInt(maxId.substring(2));

				if (maxIdNum > idCounter) // extra check
					idCounter = maxIdNum;

			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { maxId });
			}
		}
	}
	public int compareTo(Object o) {
	    if (o == null || (!(o instanceof ImportOrder)))
	      return -1;

	    ImportOrder i = (ImportOrder) o;

	    return this.coffee.getId().compareTo(i.coffee.getId());
	  }
}
