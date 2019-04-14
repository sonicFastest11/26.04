package com.collection.classdemo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Vector;

public class ComparePerformanceClassCollection {
	
	public ComparePerformanceClassCollection(ArrayList<Integer> arrayList, long l){
		for (int i = 0; i< l; i++){
			arrayList.add(i);
		}
	}
	public ComparePerformanceClassCollection(LinkedList<Integer> linkedList, long l){
		for (int i = 0; i< l; i++){
			linkedList.add(i);
		}
	}
	public ComparePerformanceClassCollection(Vector<Integer> vector, long l){
		for (int i = 0; i< l; i++){
			vector.add(i);
		}
	}
	public ComparePerformanceClassCollection(Stack<Integer> stack, long l){
		for (int i = 0; i< l; i++){
			stack.add(i);
		}
	}


	public static void main(String[] args) {
		ArrayList<Integer> arrayList = new ArrayList<>();
		LinkedList< Integer> linkedList = new LinkedList<Integer>();
		Vector<Integer> vector = new Vector<Integer>();
		Stack<Integer> stack = new Stack<Integer>();
		final long NUMBER_LOOP_EXECUTE = 1000000; 

		long start = System.currentTimeMillis();
		new ComparePerformanceClassCollection(arrayList, NUMBER_LOOP_EXECUTE);
		long end = System.currentTimeMillis();

		System.out.println("Using ArrayList: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		new ComparePerformanceClassCollection(linkedList, NUMBER_LOOP_EXECUTE);
		end = System.currentTimeMillis();

		System.out.println("Using LinkedList: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		new ComparePerformanceClassCollection(vector, NUMBER_LOOP_EXECUTE);
		end = System.currentTimeMillis();

		System.out.println("Using Vector: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		new ComparePerformanceClassCollection(stack, NUMBER_LOOP_EXECUTE);
		end = System.currentTimeMillis();

		System.out.println("Using Stack: " + (end - start) + " ms");
	}
}
