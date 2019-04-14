package com.collection.classdemo;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {
	public static void main(String[] args) {
		Set<String> linkedset = new LinkedHashSet<String>();
		// Adding element to LinkedHashSet
		linkedset.add("Maruti");
		linkedset.add("BMW");
		linkedset.add("Honda");
		linkedset.add("Audi");
		linkedset.add("Maruti"); // This will not add new element as Maruti already exists
		linkedset.add("WalksWagon");
		System.out.println("Size of LinkedHashSet=" + linkedset.size());
		System.out.println("Original LinkedHashSet:" + linkedset);
		System.out.println("Removing Audi from LinkedHashSet: " + linkedset.remove("Audi"));
		System.out.println("Trying to Remove Z which is not present: " + linkedset.remove("Z"));
		System.out.println("Checking if Maruti is present=" + linkedset.contains("Maruti"));
		System.out.println("Updated LinkedHashSet: " + linkedset);
	}
}
