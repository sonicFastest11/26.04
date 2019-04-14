package com.collection.classdemo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class ComparePerformanceForLoop {
	ArrayList<Integer> list = new ArrayList<>();

	// Created list with 100 elements
	public ComparePerformanceForLoop() {
		for (int i = 0; i < 100; i++) {
			list.add(i);
		}
	}

	// Method calculate sum from 1 to 100
	public int sum() {
		int result = 0;
		for (int i = 0; i < 100; i++) {
			result += i;
		}

		return result;
	}

	public int forLoop(int numLoop) {
		int temp = 0;
		for (int i = 0; i < numLoop; i++) {
			for (int j = 0; j < list.size(); j++) {
				temp = list.get(j);
			}
		}
		return temp;
	}

	public int foreachLoop(int numLoop) {
		int temp = 0;
		for (int i = 0; i < numLoop; i++) {
			for (Integer integer : list) {
				temp = integer;
			}
		}

		return temp;
	}

	public int iteratorLoop(int numLoop) {
		int temp = 0;
		Iterator<Integer> integers = list.iterator();
		for (int i = 0; i < numLoop; i++) {
			while (integers.hasNext()) {
				temp = integers.next();
			}
		}

		return temp;
	}

	public int listIteratorLoop(int numLoop) {
		int temp = 0;
		ListIterator<Integer> listIterator = list.listIterator();
		for (int i = 0; i < numLoop; i++) {
			while (listIterator.hasNext()) {
				temp = listIterator.next();
			}
		}

		return temp;
	}

	public static void main(String[] args) {
		ComparePerformanceForLoop test = new ComparePerformanceForLoop();

		long start = System.currentTimeMillis();
		test.forLoop(10000000);
		long end = System.currentTimeMillis();

		System.out.println("Using FOR: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		test.foreachLoop(10000000);
		end = System.currentTimeMillis();

		System.out.println("Using FOR EACH: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		test.iteratorLoop(10000000);
		end = System.currentTimeMillis();

		System.out.println("Using ITERATOR: " + (end - start) + " ms");

		start = System.currentTimeMillis();
		test.listIteratorLoop(10000000);
		end = System.currentTimeMillis();

		System.out.println("Using LIST ITERATOR: " + (end - start) + " ms");
	}
}
