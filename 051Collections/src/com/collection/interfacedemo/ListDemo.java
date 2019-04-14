package com.collection.interfacedemo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {
	public static void main(String[] args) {
	      List<String> a1 = new ArrayList<String>();
	      a1.add("Zara");
	      a1.add("Mahnaz");
	      a1.add("Zara");
	      a1.add("Ayan");      
	      System.out.println(" ArrayList Elements");
	      System.out.print("\t" + a1);
	      System.out.println("Index of: "+ a1.indexOf("Zara"));
	      
	      List l1 = new LinkedList();
	      l1.add("Zara");
	      l1.add("Mahnaz");
	      l1.add("Ayan");
	      System.out.println();
	      System.out.println(" LinkedList Elements");
	      System.out.print("\t" + l1);
	   }
}
