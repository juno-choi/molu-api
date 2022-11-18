package com.molu.molu.common;

import com.molu.molu.common.utils.Naming;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class NamingTest {

    @Test
    @DisplayName("랜덤한 이름을 뽑는데 성공한다.")
    public void getNameSuccess() throws Exception {
        //given
        //when
        for(int i=0; i<100; i++){
            String name = Naming.getName();
            System.out.println(name);
        }
        //then
    }
}