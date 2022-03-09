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
