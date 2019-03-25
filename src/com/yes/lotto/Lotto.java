package com.yes.lotto;

import java.util.Arrays;

public class Lotto {
	
	private int arr[] = new int[6];	// 추첨할 로또 번호
	private int splitInt[];	// split한 문자열을 정수형으로 변환하여 담을 배열
	
	// 자동 선택
	public void selectAuto() {
		for(int i=0; i<arr.length; i++) {
			arr[i] = (int)(Math.random()*45+1);	// 1~45까지의 정수 랜덤으로 생성 후 삽입
			
			for(int j=0; j<i; j++) {	// 추첨된 로또 번호 중복 제거
				if(arr[j] == arr[i]) {
					i--;
					break;
				}
			}
		}
		Arrays.sort(arr);	// 오름차순 배열 정리
	}

	public Lotto() {}	// 디폴트 생성자(자동 선택시 사용)
	
	public Lotto(int[] splitIntTemp) {	// 생성자(숫자 선택시 사용)
		splitInt = splitIntTemp;
	}
	
	// 숫자 선택
	public void selectNumber() {
		
		if(length(splitInt) < 6) {	// 선택한 숫자가 5개 이하일 때
			for(int i=0; i<length(splitInt); i++) {
				arr[i] = splitInt[i];
			}
			for(int i=length(splitInt); i<arr.length; i++) {
				arr[i] = (int)(Math.random()*45+1);	// 1~45까지의 정수 랜덤으로 삽입
				
				for(int j=0; j<i; j++) {	// 중복 제거
					if(arr[j] == arr[i]) {
						i--;
						break;
					}
				}
			}
		}
		else if(length(splitInt) == 6) {	// 선택한 숫자가 6개일 때
			for(int i=0; i<length(splitInt); i++) {
				arr[i] = splitInt[i];
			}
		}
		else {	// 선택한 숫자가 7개 이상일 때
			int temp, seed;
			
			for(int i=0; i<length(splitInt); i++) {	// splitInt 배열 섞기
				seed = (int)(Math.random()*length(splitInt));	// 배열 index 랜덤 추첨
				temp = splitInt[i];
				splitInt[i] = splitInt[seed];
				splitInt[seed] = temp;
			}
				
			for(int i=0; i<arr.length; i++) {	// 7개 이상의 숫자 중에 6개만 뽑기
				arr[i] = splitInt[i];
			}
		}
		Arrays.sort(arr);	//오름차순 정렬
	}

	public int[] getArr() {
		return arr;
	}
	
	public int length(int[] splitInt){	// splitInt.length에서 생기는 nullpointerexception 예방
		return splitInt == null ? 0 : splitInt.length;
	}
}
