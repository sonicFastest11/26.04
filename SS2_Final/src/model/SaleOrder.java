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

public class SaleOrder {
	private static int idCounter = 0;
	@DAttr(name = "id", id = true, auto = true, type = Type.String, length = 6, mutable = false, optional = false)
	private String id;

	@DAttr(name = "customer", type = Type.Domain, length = 30, optional = false)
	private Customer customer;

	@DAttr(name = "seller", type = Type.Domain, length = 30, optional = false)
	private Seller seller;

	@DAttr(name = "date", type = Type.String, length = 30, optional = false)
	private String date;

//	@DAttr(name = "quantity", type = Type.Integer, length = 30, optional = false)
//	private Integer quantity;
//
//	@DAttr(name = "totalPrice", type = Type.Integer, auto = true, mutable = false, optional = true, serialisable = false, derivedFrom = {
//			"unitPrice", "quantity" })
//	private Integer totalPrice;

	@DAttr(name = "detailExOrders", type = Type.Collection, optional = false, 
			serialisable = false, filter = @Select(clazz = DetailExOrder.class))
	@DAssoc(ascName = "saleOrder-has-detailExOrders", role = "saleOrder", 
	ascType = AssocType.One2Many, endType = AssocEndType.One, 
	associate = @Associate(type = DetailExOrder.class, cardMin = 0, cardMax = 30))
	private Collection<DetailExOrder> detailExOrders;

	private int count;

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public SaleOrder(String id, Customer customer, Seller seller, String date) {
		this.id = nextID(id);
		this.customer = customer;
		this.seller = seller;
		this.date = date;
//		this.quantity = quantity;
		detailExOrders = new ArrayList<>();
//		calTotal();
		count = 0;
	}

//	public void calTotal() {
//		totalPrice = unitPrice * quantity;
//	}

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public SaleOrder(@AttrRef("customer") Customer customer, @AttrRef("seller") Seller seller,
			@AttrRef("date") String date) {
		this(null, customer, seller, date);
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	

//	public String getProduct() {
//		return product;
//	}
//
//	public void setProduct(String product) {
//		this.product = product;
//	}
//
//	public Integer getUnitPrice() {
//		return unitPrice;
//	}
//
//	public void setUnitPrice(Integer unitPrice) {
//		this.unitPrice = unitPrice;
//		calTotal();
//	}
//
//	public Integer getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//		calTotal();
//	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getId() {
		return id;
	}

//	public Integer getTotalPrice() {
//		return totalPrice;
//	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addDetailExOrder(DetailExOrder d) {
		if (!detailExOrders.contains(d))
			detailExOrders.add(d);

		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDetailExOrder(DetailExOrder d) {
		detailExOrders.add(d);

		count++;

		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	// @MemberRef(name="enrolments")
	public boolean addDetailExOrder(Collection<DetailExOrder> deos) {
		boolean added = false;
		for (DetailExOrder d : deos) {
			if (!detailExOrders.contains(d)) {
				if (!added)
					added = true;
				detailExOrders.add(d);
			}
		}
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDetailExOrder(Collection<DetailExOrder> deos) {
		detailExOrders.addAll(deos);
		count += deos.size();

		return false;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	public boolean removeDetailExOrder(DetailExOrder d) {
		boolean removed = detailExOrders.remove(d);

		if (removed) {
			count--;

		}
		return false;
	}

	public void setDetailExOrder(Collection<DetailExOrder> deo) {
		this.detailExOrders = deo;
		count = deo.size();

	}

	public Collection<DetailExOrder> getDetailExOrders() {
		return detailExOrders;
	}

	@DOpt(type = DOpt.Type.LinkCountGetter)
	public int getCount() {
		return count;
		// return enrolments.size();
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setCount(int count1) {
		count = count1;
	}

	@Override
	public String toString() {
		return "SaleOrder(" + id + "," + customer + "," + seller + "," + date + ")";
	}

	public String nextID(String id) throws ConstraintViolationException {
		if (id == null) { // generate a new id
			idCounter++;

			return "SO" + idCounter;
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

}
