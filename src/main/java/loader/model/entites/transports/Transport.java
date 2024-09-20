package loader.model.entites.transports;

public interface Transport {

    int DEFAULT_BODY_WIDTH = 6;
    int DEFAULT_BODY_HEIGHT = 6;

    char[][] getBody();

    String toString();

}
