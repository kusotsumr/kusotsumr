package musicbandlab.common.contracts.packets;

public record PacketResponse(
        String responseType,
        boolean success,
        String payload,
        String error
) { }