package com.boardPractice.demo.domain;

import com.boardPractice.demo.DemoApplication;
import org.springframework.boot.SpringApplication;

import java.util.Scanner;

public class Student {
    private String name;
    private int studentID;
    private String major;

    //생성자
    public Student(String name, int studentID, String major) {
        this.name = name;
        this.studentID = studentID;
        this.major = major;
    }

    public void printData(){
        System.out.println("name = " + name + ", studentID = " + studentID + ", major = " + major);
    }

    public boolean isEven(int n){
         if(n%2==0){
             return true;
         }else{
             return false;
         }

    }

    public static void main(String[] args) {
        Student student = new Student("고영우",201821810, "컴공");
        student.printData();

        for(int i=1;i<10;i++){
            System.out.println(i + "의 짝수 여부 : " + student.isEven(i));
        }

        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();

        try{
            int result = n /10;
            System.out.println("result = " + result);
        }catch (ArithmeticException e){
            System.out.println("Cannot divide by zero");
        }
    }

}
