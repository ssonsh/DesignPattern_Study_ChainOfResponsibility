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
