package model;

import java.util.ArrayList;
import java.util.Collection;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.Select;
import domainapp.basics.util.Tuple;

public class Coffee {
	public static final String A_id = "id";
	public static final String A_name = "nameCoffee";
	private static int idCounter = 0;

	@DAttr(name = A_id, id = true, auto = true, type = Type.String, length = 6, mutable = false, optional = false)
	private String id;

	@DAttr(name = A_name, type = Type.String, length = 30, optional = false)
	private String nameCoffee;

	@DAttr(name = "typeOfCoffee", type = Type.Domain, optional = false, length = 6)
	@DAssoc(ascName = "coffee-has-typeOfCoffee", ascType = AssocType.One2Many, endType = AssocEndType.Many, role = "coffee", associate = @Associate(cardMax = 25, cardMin = 1, type =TypeOfCoffee.class))
	private TypeOfCoffee typeOfCoffee;

	@DAttr(name = "importOrders", type = Type.Collection, optional = false, serialisable = false, filter = @Select(clazz=ImportOrder.class))
	@DAssoc(ascName = "coffee-has-importOrders", role = "coffee", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type=ImportOrder.class, cardMin = 0, cardMax = 30))
	private Collection<ImportOrder> importOrders;
	
	// derived
	private int importOrderCount;

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Coffee(String id, String nameCoffee, TypeOfCoffee typeOfCoffee) {
		this.id = nextID(id);
		this.nameCoffee = nameCoffee;
		this.typeOfCoffee = typeOfCoffee;
		importOrders = new ArrayList<>();
		importOrderCount = 0;
	}

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Coffee(@AttrRef("nameCoffee") String nameCoffee, @AttrRef("typeOfCoffee") TypeOfCoffee typeOfCoffee) {
		this(null, nameCoffee, typeOfCoffee);
	}

	public String getId() {
		return id;
	}

	public String getNameCoffee() {
		return nameCoffee;
	}

	public void setNameCoffee(String nameCoffee) {
		this.nameCoffee = nameCoffee;
	}

	public TypeOfCoffee getTypeOfCoffee() {
		return typeOfCoffee;
	}

	public void setTypeOfCoffee(TypeOfCoffee typeOfCoffee) {
		this.typeOfCoffee = typeOfCoffee;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addImportOrder(ImportOrder im) {
		if (!importOrders.contains(im))
			importOrders.add(im);

		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewImportOrder(ImportOrder im) {
		importOrders.add(im);

		importOrderCount++;

		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	// @MemberRef(name="enrolments")
	public boolean addImportOrder(Collection<ImportOrder> imps) {
		boolean added = false;
		for (ImportOrder im : imps) {
			if (!importOrders.contains(im)) {
				if (!added)
					added = true;
				importOrders.add(im);
			}
		}
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewImportOrder(Collection<ImportOrder> imps) {
		importOrders.addAll(imps);
		importOrderCount += imps.size();

		return false;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	public boolean removeImportOrder(ImportOrder im) {
		boolean removed = importOrders.remove(im);

		if (removed) {
			importOrderCount--;

		}
		return false;
	}

	public void setImportOrders(Collection<ImportOrder> imp) {
		this.importOrders = imp;
		importOrderCount = imp.size();

	}

	public Collection<ImportOrder> getImportOrders() {
		return importOrders;
	}

	@DOpt(type = DOpt.Type.LinkCountGetter)
	public int getImportOrderCount() {
		return importOrderCount;
		// return enrolments.size();
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setImportOrderCount(int count) {
		importOrderCount = count;
	}

	@Override
	public String toString() {
		return "Coffee (" + id + "," + nameCoffee + " , " + typeOfCoffee + ")";
	}

	// automatically generate the next student id
	private String nextID(String id) throws ConstraintViolationException {
		if (id == null) { // generate a new id
			idCounter++;
			return "C" + idCounter;
		} else {
			// update id
			int num;
			try {
				num = Integer.parseInt(id.substring(1));
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
				int maxIdNum = Integer.parseInt(maxId.substring(1));

				if (maxIdNum > idCounter) // extra check
					idCounter = maxIdNum;

			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { maxId });
			}
		}
	}
}
