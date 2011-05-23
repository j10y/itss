package com.zsgj.dcit.util;

public class Random {

	public int[] createRandom(){
		
		int[] random = new int[10];
		for(int i = 0 ; i < 10; i++){
			Double randomValue = Math.random()*1000000;
			int randomValueInt =  randomValue.intValue();
			random[i]=randomValueInt;
		}
		return random;
	}
	public int selectMinRandom(int[] random){
		int randomValue = 0;
		int temp = 0 ;
		for(int i = 0 ; i < random.length; i++ ){
			for(int j = 0 ; j < i; j ++){
				if(random[i]<random[j]){
					temp = random[i];
					random[i] = random[j];
					random[j] = temp;
				}
			}
		}
		randomValue = random[0];
		return randomValue;
	}
	public static void main(String[] args) {		
		Random r = new Random();
		int [] i = r.createRandom();
		
		for(int j = 0 ; j < i.length; j++){
			System.out.println("********************"+(j+1)+"******************:begin");
			System.out.println(+i[j]);
			System.out.println("********************"+(j+1)+"******************:end");
		}
		
		int s = r.selectMinRandom(i);
		
		System.out.println("==========================:"+s);
		
		
		
	}

}
