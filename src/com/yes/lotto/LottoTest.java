package com.yes.lotto;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LottoTest {

	static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LottoTest lottoTest = new LottoTest();
		int option;
		
		// 5개의 객체 생성(5개의 예시)
		Lotto[] lotto = new Lotto[5];
		
		System.out.println("로또 번호 추첨기를 시작합니다.");
		System.out.println();
		
		while(true) {
			System.out.println("원하는 옵션을 숫자로 선택하세요.");
			System.out.println("1.자동 선택, 2.숫자 선택, 3. 끝내기");
			
			while(true) {
				try {
					option = scan.nextInt();	// 선택 모드 
					scan.nextLine();	// 에러 방지
					break;
				}
				catch( InputMismatchException e ) {
					System.out.println("잘못 입력했습니다. 정수만 입력 가능합니다.");
					scan = new Scanner(System.in);
				}
			}
			
			// 자동 선택시
			if(option == 1) {
				outerLoop :
				while(true) {
					for(int i=0; i<lotto.length; i++) {
						lotto[i] = new Lotto();
						lotto[i].selectAuto();	// 자동 선택
						
						int ascii = 65+i;	
						char asciiStr = (char)ascii;	// 알파벳 아스키코드로 할당
						
						String str = Character.toString(asciiStr) + "  ";
						for(int j=0; j<lotto[i].getArr().length; j++) {
							str += String.valueOf(lotto[i].getArr()[j])+",";	// 정수 문자열로 변환
						}					
						str = str.substring(0, str.length()-1);	// 마지막 , 지우기
						System.out.println(str);
					}
					
					System.out.println();
					
					while(true) {	// 메뉴 선택
						int re = lottoTest.checkRepeat();
						if(re == 1) {	// 1.reset
							break outerLoop;	
						}
						else if(re == 2) {	// 2. 로또 번호 출력하기
							break;
						}
						else {
							System.out.println("다시 입력해주세요.");
						}
					}
				}
			}
			// 2. 숫자 선택
			else if(option == 2) {
				//
				int check = 0;
				int checkException = 0;
				int checkSpace = 0;
				String split[] = null;
				int splitInt[] = null;

				System.out.println("1~45까지의 숫자 중 좋아하는 숫자(정수)를 공백으로 구분하여 모두 입력해주세요.(중복 불가)");
				
				while(check == 0) {
					check = 1;
					
					while(checkException == 0) {	// 예외 발생시 반복
					try {
						checkException = 1;
						String number = scan.nextLine();

						while(checkSpace == 0) {	// 공백 입력시 예외 처리, 예외 발생시 반복
							checkSpace = 1;
							
							while(true) {
								try {
									if(number.charAt(0) == ' ') {
										checkSpace = 0;
										System.out.println("잘못 입력했습니다. 다시 입력해주세요.(1~45 사이의 정수와 공백만 입력)");
										number = scan.nextLine();	// 예외 발생 구간
									}
									break;
								}
								catch(StringIndexOutOfBoundsException e) {
									System.out.println("잘못 입력했습니다. 다시 입력해주세요.(1~45 사이의 정수와 공백만 입력)");
									number = scan.nextLine();
								}
							}
						}
						// 공백으로 입력 숫자 구분 및 배열 자료형 변환
						split = number.split("\\s+");
						splitInt = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();	// 예외 발생 구간
						
						break;
					}
					catch(NumberFormatException e) {
						scan = new Scanner(System.in);
						System.out.println("잘못 입력했습니다. 다시 입력해주세요.(1~45 사이의 정수와 공백만 입력)");
						checkException = 0;
						}
					}
					
					outerLoop2 :
					for(int i=0; i<splitInt.length; i++) {
						if(splitInt[i]<1 || splitInt[i]>45) {
							System.out.println("범위를 넘는 숫자가 있습니다. 다시 입력해주세요.");
							check = 0; checkException = 0;
							break;
						}
						for(int j=i+1; j<splitInt.length; j++)
							if(splitInt[i] == splitInt[j]) {
								System.out.println("중복된 숫자가 있습니다. 다시 입력해주세요.");
								check = 0; checkException = 0;
								break outerLoop2;
							}
					}			
				}
				//
				
				outerLoop3 :
					while(true) {
						for(int i=0; i<lotto.length; i++) {
							lotto[i] = new Lotto(splitInt);
							lotto[i].selectNumber();	// 숫자 선택
							
							int ascii = 65+i;
							char asciiStr = (char)ascii;
					
							String str = Character.toString(asciiStr) + "  ";
							for(int j=0; j<lotto[i].getArr().length; j++) {
								str += String.valueOf(lotto[i].getArr()[j])+",";
							}					
							str = str.substring(0, str.length()-1);
							System.out.println(str);
						}
						
						System.out.println();
						
						while(true) {	// 메뉴 선택
							int re = lottoTest.checkRepeat();
							if(re == 1) {	// 1.reset
								break outerLoop3;
							}
							else if(re == 2) {	// 2.로또
								break;
							}
							else {
								System.out.println("다시 입력해주세요.");
							}
						}
					}
			}
			// 3. 프로그램 끝내기
			else if(option == 3) {
				System.out.println();
				System.out.println("프로그램을 종료합니다.");
				break;
			}
			else {
				System.out.println();
				System.out.println("숫자를 다시 입력해주세요.");
			}
		}
	}
	
	public int checkRepeat() {
		int repeat;
		System.out.println("1.reset, 2.로또 번호 출력하기");
		repeat = scan.nextInt();
		scan.nextLine();	// 에러 방지
		
		return repeat;
	}


}
