package com.docimax.qualityinspection;

import cn.hutool.extra.spring.SpringUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

/**
 * @author xufubiao
 */
@Import(SpringUtil.class)
@SpringBootApplication(scanBasePackages ={"com.docimax.qualityinspection"})
@MapperScan(basePackages = {"com.docimax.qualityinspection.mapper.dao"})
@EnableFeignClients
public class QualityInspectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(QualityInspectionApplication.class, args);

        System.out.println("\n" +
                "  启动成功      .-. .-')   \n" +
                "             \\  ( OO )  \n" +
                " .-'),-----. ,--. ,--.  \n" +
                "( OO'  .-.  '|  .'   /  \n" +
                "/   |  | |  ||      /,  \n" +
                "\\_) |  |\\|  ||     ' _) \n" +
                "  \\ |  | |  ||  .   \\   \n" +
                "   `'  '-'  '|  |\\   \\  \n" +
                "     `-----' `--' '--'  ");
        System.out.println("http://localhost:7309/quality-inspection/springdoc/swagger-ui/index.html");
    }
}
