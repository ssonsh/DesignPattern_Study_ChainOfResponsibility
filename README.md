# DesignPattern_Study_ChainOfResponsibility

# Notion Link
https://five-cosmos-fb9.notion.site/Chain-of-Responsibility-63840b88975d40d499e7913f4d042315


### 의도

메세지를 보내는 **객체와 받는 객체의 결합도를 없애기 위한 패턴**이다.

어떤 요청을 핸들러의 체인에 넘기면, 각 핸들러는 해당 요청을 처리할 지 다음 핸들러로 넘길 지를 결정하게 된다.

### 활용성

- 하나 이상의 객체가 요청을 처리해야 하고, 그 요청 처리자 중 어떤 것이 선행자인 지 모를 때, 
처리자가 자동으로 확정되어야 한다.
- 메세지를 받을 객체를 명시하지 않은 채 여러 객체 중 하나에게 처리를 요청하고 싶을 때
- 요청을 처리할 수 있는 객체 집합이 동적으로 정의되어야 할 때

### 결과

- 객체 간의 행동적 결합도가 낮아진다.
- 객체에서 책임을 할당하는 데 유연성을 높일 수 있다.
- 메시지 수신이 보장되지는 않는다.

### 구조

![image](https://user-images.githubusercontent.com/18654358/157387648-6fea168d-670e-4fa3-8225-9e85eae613ef.png)

---

### Chain of Responsibility 가 적용되지 않은 간단한 예

![image](https://user-images.githubusercontent.com/18654358/157387677-ff163d2a-9e44-40f0-ba72-ca64f6387109.png)

### Chain of Responsibility 가 적용된 예

![image](https://user-images.githubusercontent.com/18654358/157387682-3a47ec5e-474e-4604-ba4a-1ffc75a58e19.png)

- 결제에 대한 각각의 모듈들이 존재하고 있다.
    - Cash, Credit Card, Debit Card, etc
- 결제가 들어오게 되면 결제 Type에 따라 (ex. Cash) 결제 처리 Handler Chain으로 요청되게 되고
- ***Handler Chain은 본인이 처리할 수 있는 결제 Type에 따라 결제를 수행하거나 다음 Handler로 전달하게 된다.***
- 여기서 우리가 흔히 알고 있는 Linked List의 형태를 띄고 있는 것을 알 수 있다.

<aside>
💡 새로운 결제 방법이 추가된다면? 그 모듈을 만들고 기존에 있던 Chain 형태에 연결만 시켜주면 원하는 비즈니스 요구사항을 쉽게 해결해낼 수 있다.

</aside>

![image](https://user-images.githubusercontent.com/18654358/157387722-10a4c42c-84d5-4870-9309-79dddae9c720.png)

- Base Handler를 기반으로 각각의 구현 Handler를 구성해낸다.

<aside>
💡 항상 첫번째일 필요 없다.

처리되었다고 끝낼 필요도 없다. 

1차원 구조의 체인일 필요가 없다. (Tree, Graph 가능하다.)

</aside>

### 포인트

- ***요청을 처리하는 모듈을 만들어 Chain 형태로 구성한다.***
- 요청 처리의 순서를 제어할 수 있다.

---

### 활용

**웹 서비스 Request에 대한 여러 필터를 사용하는 경우**

```java
javax.servlet.Filter.doFilter();
```

- **doFilter**는 **Chain of Responsibility Pattern**을 따른다.
- **doFilter** 필터의 메서드는 체인 끝에서 리소스에 대한 클라이언트 요청으로 인하여 요청 및 응답이 Chain을 통해 전달될 때마다 컨테이너에 의해 호출된다.
- 이 메서드에 전달된 **FilterChain**을 사용하면 필터가 요청을 전달하고 체인의 다음 엔티티에 응답할 수 있다.

![image](https://user-images.githubusercontent.com/18654358/157387759-3480c824-30c2-4213-8372-68fc8e49bdbe.png)

**Spring Security → SecurityFilterChain**

SecurityFilterChain은 **인증을 처리하는 여러 개의 시큐리티 필터를 담는 필터 체인**이다.

필터체인 프록시를 통해 서블릿 필터와 연결되고 어떤 시큐리티 필터를 통해 인증을 수행할지 결정하는 역할을 수행한다.

![image](https://user-images.githubusercontent.com/18654358/157387773-e6be748d-43ea-4d5d-aeb5-5fc42ca39dfa.png)

- **DelegatingFilterProxy**
    - 사용자의 요청이 서블릿에 전달되어 자원에 접근하기 전에, 스프링 시큐리티는 필터 생명주기를 이용해서 인증과 권한 작업을 수행한다.
    - 서블릿 필터의 생명주기 시점에서 스프링 시큐리티에서 동작하는 인증과 권한 작업을 수행해야 하는데, **서블릿 컨테이너에서는 스프링 컨테이너에 등록된 빈을 인식할 수 없다.**
    - 그렇기 때문에 **스프링 시큐리티는 DelegatingFilterProxy 라는 서블릿 필터의 구현체**를 제공한다.
    - DelegatingFilterProxy는 서블릿 매커니즘을 통해 서블릿의 필터로 등록 가능하며
    - 스프링에 등록된 빈을 가져와 의존성을 주입할 수 있다.
        
        <aside>
        🎈 즉, Filter에서 스프링 빈을 사용할 수 있는 필터 Proxy 객체라고 볼 수 있다.
        
        </aside>
        
        ```java
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
            // Lazily get Filter that was registered as a Spring Bean
            // For the example in DelegatingFilterProxy delegate is an instance of Bean Filter0
            Filter delegate = getFilterBean(someBeanName);
            // delegate work to the Spring Bean
            delegate.doFilter(request, response);
        }
        ```
        
        → **getFilterBean(..)** 을 통해 ApplicationContext에 등록된 스프링 Bean을 가져올 수 있다. 
        

---

### 예제 코드

[PaymentHandler.java](http://PaymentHandler.java) (abstract)

```java
public abstract class PaymentHandler {
    private PaymentHandler next;

    public void chainHandle(PaymentHandler handler){
        this.next = handler;
    }

    public abstract String handle(PaymentRequest paymentRequest);

    protected String nextHandle(PaymentRequest paymentRequest) {
        if(this.next != null){
            return this.next.handle(paymentRequest);
        }else{
            return "Handle Complete";
        }
    }
}
```

CardHandler.java

```java
public class CardHandler extends PaymentHandler {

    @Override
    public String handle(PaymentRequest paymentRequest) {

        System.out.println("processing [CardHandler] Start");
        if(paymentRequest.getType() == PaymentType.CARD){
            System.out.println("processing [CardHandler] END");
            return "Payments Card ! = " + paymentRequest.getAmount();
        }

        System.out.println("NONE Processing [CardHandler] Pass Next Handler");
        return super.nextHandle(paymentRequest);
    }
}
```

CashHandler.java

```java
public class CashHandler extends PaymentHandler {

    @Override
    public String handle(PaymentRequest paymentRequest) {
        System.out.println("processing [CashHandler] Start");
        if(paymentRequest.getType() == PaymentType.CASH){
            System.out.println("processing [CashHandler] End");
            return "Payments Cash ! = " + paymentRequest.getAmount();
        }

        System.out.println("NONE Processing [CashHandler] Pass Next Handler");
        return super.nextHandle(paymentRequest);
    }
}
```

DirectCardHandler.java

```java
public class DirectCardHandler extends PaymentHandler {

    @Override
    public String handle(PaymentRequest paymentRequest) {
        System.out.println("processing [DirectCardHandler] Start");
        if(paymentRequest.getType() == PaymentType.DIRECT_CARD){
            System.out.println("processing [DirectCardHandler] End");
            return "Payments Direct Card ! = " + paymentRequest.getAmount();
        }

        System.out.println("NONE Processing [DirectCardHandler] Pass Next Handler");
        return super.nextHandle(paymentRequest);
    }
}
```

Test

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("hi");

        CashHandler cashHandler = new CashHandler();
        CardHandler cardHandler = new CardHandler();
        DirectCardHandler directCardHandler = new DirectCardHandler();

        cashHandler.chainHandle(cardHandler);
        cardHandler.chainHandle(directCardHandler);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setType(PaymentType.CARD);
        paymentRequest.setAmount(1000000L);

        String result = cashHandler.handle(paymentRequest);
        System.out.println(String.format("card paymentRequest result = %s", result));

        System.out.println("#############################################");

        paymentRequest.setType(PaymentType.DIRECT_CARD);
        paymentRequest.setAmount(7770L);

        String result2 = cashHandler.handle(paymentRequest);
        System.out.println(String.format("direct card paymentRequest result = %s", result2));
    }
}
```

```java
processing [CashHandler] Start
NONE Processing [CashHandler] Pass Next Handler
processing [CardHandler] Start
processing [CardHandler] END
card paymentRequest result = Payments Card ! = 1000000
#############################################
processing [CashHandler] Start
NONE Processing [CashHandler] Pass Next Handler
processing [CardHandler] Start
NONE Processing [CardHandler] Pass Next Handler
processing [DirectCardHandler] Start
processing [DirectCardHandler] End
direct card paymentRequest result = Payments Direct Card ! = 7770
```

---
