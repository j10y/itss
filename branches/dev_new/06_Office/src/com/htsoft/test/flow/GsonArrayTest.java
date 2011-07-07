package com.htsoft.test.flow;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonArrayTest {
	private String name;
	private int age;

	public String getName() {
		/* 19 */return this.name;
	}

	public void setName(String name) {
		/* 27 */this.name = name;
	}

	public int getAge() {
		/* 35 */return this.age;
	}

	public void setAge(int age) {
		/* 41 */this.age = age;
	}

	public String toString() {
		/* 45 */return "name:" + this.name + " age:" + this.age;
	}

	public static void main(String[] args) {
		/* 56 */Gson gson = new GsonBuilder().serializeNulls().create();

		/* 58 */String obj2 = "[{name:'ding',age:''},{name:'king',age:'1'}]";

		/* 60 */GsonArrayTest[] test2 = gson.fromJson(obj2,
				GsonArrayTest[].class);
		/* 61 */for (GsonArrayTest a : test2)
			/* 62 */System.out.println("a:" + a.toString());
	}
}
