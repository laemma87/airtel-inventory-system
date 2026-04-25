package com.inventory.airtel;

import com.inventory.airtel.ui.LoginFrame; 
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import java.awt.EventQueue;

@SpringBootApplication
public class AirtelApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AirtelApplication.class)
                .headless(false)
                .run(args);

        
        EventQueue.invokeLater(() -> {
            
            LoginFrame loginFrame = context.getBean(LoginFrame.class);
            loginFrame.init();
        });
    }
}