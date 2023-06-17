package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.example.demo.listener.ApplicationEventListener;


/**
1. Spring Boot 특징: IOC, Inversion Of Control - 개발자는 new 연산자, 인터페이스 호출, 팩토리 호출 방식으로 객체를 생성하고 소멸하지만, 스프링 컨테이너를 사용하면 해당 역할을 대신 해준다.
2. Bean Type1: 개발자가 직접 컨트롤이 가능한 클래스들의 경우 - 컴포넌트 스캔은 Component을 명시하여 빈을 추가하는 방법이다. 클래스 위에 Component를 붙이면 스프링이 알아서 스프링 컨테이너에 빈을 등록한다.
3. Bean Type2: 외부 라이브러리들을 Bean으로 등록하고 싶은 경우 - Java 코드로 빈을 등록할 수 있다. 이때, 빈을 등록하기 위해 인스턴스를 생성하는 메소드 위에 @Bean을 명시하면 된다.
4. Bean Type3: ApplicationContext를 호출하여 수동으로 설정 파일을 이용하여 빈을 수동 등록할 수도 있다.
public static void main(String[] args){
    final ApplicationContext beanFactory = new AnnotationConfigApplicationContext(appConfig.class);
    final AppConfig bean = beanFactory.getBean("appConfig", AppConfig.class);
}
*/


@SpringBootApplication
@EnableEurekaServer
@EnableFeignClients
public class DemoApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
//	}

    public static void main(String[] args) {
    
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.addListeners(new ApplicationEventListener());
        app.run(args);
        
    }
}



//톰캣에 web.xml에 내장된 전통적인 War 파일방식일 경우
//@SpringBootApplication
//public class Application extends SpringBootServletInitializer {
// 
// public static void main(String[] args) {
//     new SpringApplicationBuilder(Application.class).listeners(new ApplicationEventListener())
//     .addCommandLineProperties(true).build().run(args);
// }
// 
// @Override
// protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//     return application.sources(Application.class);
// }
//}
