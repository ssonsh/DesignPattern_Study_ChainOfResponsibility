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
