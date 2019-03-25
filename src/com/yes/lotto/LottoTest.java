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
				catch( InputMismatchException e ) {	// 예외 처리
					System.out.println("1, 2, 3만 입력 가능합니다.\n");
					System.out.println("원하는 옵션을 숫자로 선택하세요.");
					System.out.println("1.자동 선택, 2.숫자 선택, 3. 끝내기");
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
					
					int re;
					
					while(true) {	// 메뉴 선택
						try {
							re = lottoTest.checkRepeat();
							if(re == 1) {	// 1.reset
								break outerLoop;	
							}
							else if(re == 2) {	// 2. 로또 번호 출력하기
								break;
							}
							else {
								System.out.println("1과 2만 입력 가능합니다.");
							}
						}
						catch(InputMismatchException e) {
							System.out.println("1과 2만 입력 가능합니다.");
							scan = new Scanner(System.in);
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
				String split[];
				int splitInt[] = null;
				String number = "";

				System.out.println("1~45까지의 숫자 중 좋아하는 숫자(정수)를 ,(쉼표)로 구분하여 모두 입력해주세요.(중복 불가)");
				
				while(check == 0) {	// 숫자나 쉼표 외의 문자들이 들어가면 다시 입력하게 만드는 코드(예외 처리)
					check = 1;
					
					while(checkException == 0) {
						try {
						
							checkException = 1;
							number = scan.nextLine();
							
							while(checkSpace == 0) {
								checkSpace = 1;
								
								String tmp = number.replaceAll("(\r\n|\r|\n|\n\r)", "");	// 개행 제거
								tmp = tmp.replaceAll(" " , "");
								tmp = tmp.replaceAll("\\p{Z}", "");	// 공백 제거

								if(tmp.trim().length() == 0) {
									System.out.println("잘못 입력했습니다. 다시 입력해주세요.(1~45 사이의 정수와 공백만 입력)");
									checkSpace = 0; checkException = 0;
									break;
								}
								else {
									split = tmp.split(",");	// ,로 문자열 구분
									splitInt = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();	// string 배열을 int 배열로 변환
								}
							}
						}
						catch(NumberFormatException e) {
							System.out.println("잘못 입력했습니다. 다시 입력해주세요.(1~45 사이의 정수와 공백만 입력)");
							checkException = 0; checkSpace = 0;
						}
					}
					
					outerLoop2 :
						for(int i=0; i<lottoTest.length(splitInt); i++) {
							if(splitInt[i]<1 || splitInt[i]>45) {	// 숫자 범위 1~45 체크
								System.out.println("범위를 넘는 숫자가 있습니다. 다시 입력해주세요.");
								check = 0; checkException = 0; checkSpace = 0;
								break;
							}
							for(int j=i+1; j<lottoTest.length(splitInt); j++)
								if(splitInt[i] == splitInt[j]) {	// 중복된 숫자 체크
									System.out.println("중복된 숫자가 있습니다. 다시 입력해주세요.");
									check = 0; checkException = 0; checkSpace = 0;
									break outerLoop2;
								}
						}
				}
				
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
						
						int re;
						
						while(true) {	// 메뉴 선택
							try {
								re = lottoTest.checkRepeat();
								if(re == 1) {	// 1.reset
									break outerLoop3;	
								}
								else if(re == 2) {	// 2. 로또 번호 출력하기
									break;
								}
								else {
									System.out.println("1과 2만 입력 가능합니다.");
								}
							}
							catch(InputMismatchException e) {
								System.out.println("1과 2만 입력 가능합니다.");
								scan = new Scanner(System.in);
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
				System.out.println("1, 2, 3만 입력 가능합니다.\n");
			}
		}
	}
	
	public int checkRepeat() {	// 반복 체크
		int repeat;
		System.out.println("1.reset, 2.로또 번호 출력하기");

		repeat = scan.nextInt();
		scan.nextLine();	// 에러 방지

		return repeat;
	}
	
	public int length(int[] splitInt){	// splitInt.length에서 생기는 nullpointerexception 예방
		return splitInt == null ? 0 : splitInt.length;
	}

}
