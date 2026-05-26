package musicbandlab.client.consoleui;

public class UdpNetworkException extends Exception {

    public UdpNetworkException(String message) {
        super(message);
    }

    public UdpNetworkException(String message, Throwable cause) {
        super(message, cause);
    }
}