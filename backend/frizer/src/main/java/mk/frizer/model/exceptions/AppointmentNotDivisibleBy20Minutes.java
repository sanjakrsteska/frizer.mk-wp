package mk.frizer.model.exceptions;

public class AppointmentNotDivisibleBy20Minutes extends RuntimeException{
    public AppointmentNotDivisibleBy20Minutes(String message){
        super(message);
    }
}
