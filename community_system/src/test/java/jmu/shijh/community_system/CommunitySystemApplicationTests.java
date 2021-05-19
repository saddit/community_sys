package jmu.shijh.community_system;


import jmu.shijh.community_system.mapper.RecordMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.MessageFormat;
import java.util.Date;

@SpringBootTest
class CommunitySystemApplicationTests {

    @Autowired
    private RecordMapper recordMapper;

    @Test
    void test1() throws Exception {
        boolean matches = "".matches("^[\\s]*[a-zA-Z_]+");
        System.out.println(matches);
    }

    @Test
    void compareLongStringTime() throws Exception {

    }

    /**
     * 得出结论：
     * 64位下 长整型平均比较速度高于字符串
     * 极限情况下 字符串的时间是长整形的 7-10 倍左右 如下 l1 l3/s1 s3
     */
    public static void main(String[] args) {
        String s = "jfad$";
        String rs;
        long t1 = System.nanoTime();
        rs = "{" + s + "}";
        long t2 = System.nanoTime();
        StringBuilder builder = new StringBuilder();
        rs = builder.append("{").append(s).append("}").toString();
        long t3 = System.nanoTime();
        long t4 = System.nanoTime();
        long t5 = System.nanoTime();
        System.out.println("直接:" + (t2-t1));
        System.out.println("builder:" + (t3-t2));
        System.out.println("toCharArray然后new:" + (t4-t3));
        System.out.println("StringUtils:" + (t5-t4));
        //long t6 = System.nanoTime();
    }
}
