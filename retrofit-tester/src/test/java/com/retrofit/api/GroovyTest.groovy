package com.retrofit.api

import org.junit.Test

/**
 * GroovyTest
 * Created by SCWANG on 2016/8/3.
 */
class GroovyTest {

    @Test
    void testRange(){

        def hash = [name:"Andy", "VPN-#":45]
        hash.abc = "sss"
        hash["222"] = "2222"
        println hash;

        "Guillaume".ext()
    }

}
