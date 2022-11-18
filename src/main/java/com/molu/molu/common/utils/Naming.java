package com.molu.molu.common.utils;

public class Naming {
    private static final String[] FRONT = {"어쩔","저쩔","참나","안물","안궁","우짤","뇌절","저짤"};
    private static final String[] BACK = {"빌드 에러", "접속 에러", "404 에러", "500 에러", "커밋 오류", "깃 충돌", "유효성 검사", "파라미터 없음"};

    public static String getName(){
        int frontLength = FRONT.length;
        int backLength = BACK.length;

        int frontRandom = (int) (Math.random() * frontLength);
        int backRandom = (int) (Math.random() * backLength);

        return String.format("%s %s", FRONT[frontRandom], BACK[backRandom]);
    }
}
