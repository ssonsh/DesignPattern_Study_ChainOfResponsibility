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
