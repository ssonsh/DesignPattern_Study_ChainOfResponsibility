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
