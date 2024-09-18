package loader.model.exceptions;

public class InvalidCargoInput extends RuntimeException{
    public InvalidCargoInput(String message){
        super(message);
    }
}
