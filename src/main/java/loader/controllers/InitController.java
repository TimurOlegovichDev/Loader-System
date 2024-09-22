package loader.controllers;

import java.util.List;

public interface InitController {

    void initializeCargos(List<String> forms);

    void initializeTransport(String filePath);

    void initializeTransport(int numberOfTransport);
}