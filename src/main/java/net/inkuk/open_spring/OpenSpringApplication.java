package net.inkuk.open_spring;

import jakarta.annotation.PostConstruct;
import net.inkuk.open_spring.util.Log;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.awt.*;


@SpringBootApplication
@EnableScheduling
public class OpenSpringApplication implements ApplicationRunner {

	public static void main(String[] args) {

        final String env = System.getenv("ENV");

        if(env != null && env.equals("DEV"))
            System.setProperty("server.servlet.context-path", "/api");

        ConfigurableApplicationContext context = SpringApplication.run(OpenSpringApplication.class, args);

        final String applicationName = context.getEnvironment().getProperty("spring.application.name");

        boolean isOpenSpring = applicationName == null || applicationName.equals("open-spring");

        final String infoLogPath = isOpenSpring ? "/home/ubuntu/open-spring/log/info" : "/home/ubuntu/private-spring/log/info";
        final String errorLogPath = isOpenSpring ? "/home/ubuntu/open-spring/log/error" : "/home/ubuntu/private-spring/log/error";

        Log.init(infoLogPath, errorLogPath);
        Log.info("info is green");
        Log.error("error is red");
        Log.debug("debug is yellow");

        Package pkg = OpenSpringApplication.class.getPackage();
        String version = (pkg != null) ? pkg.getImplementationVersion() : null;

        if(version != null)
            Log.info("version : " + version);
	}


    @Override
    public void run(@NotNull ApplicationArguments args) throws Exception {

    }


    @PostConstruct
    public void init() {

        System.out.println("init");
    }

    @Scheduled(fixedRate = 1000000)
    public void test() {

    }
}
