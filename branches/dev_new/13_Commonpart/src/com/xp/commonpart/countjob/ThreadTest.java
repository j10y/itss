package com.xp.commonpart.countjob;

public class ThreadTest implements Runnable{
		String C1Name;
		public ThreadTest(String C32Name){
			this.C1Name=C32Name;
		}
		public void run(){
			try {
				Thread.sleep(Math.round(Math.random()*1000));
			}catch(InterruptedException e){
			}
			System.out.println("在run方法中 : "+C1Name);
		}
		public static void main(String args[]){
			ThreadTest in1=new ThreadTest("实例1");
			ThreadTest in2=new ThreadTest("实例2");
			Thread t1=new Thread(in1);
			Thread t2=new Thread(in2);
			t1.start();
			System.out.println("实例1创建了新线程");
			t2.start();
			System.out.println("实例2创建了新线程");
		}
}
