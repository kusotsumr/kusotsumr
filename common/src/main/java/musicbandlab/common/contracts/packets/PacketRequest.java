package musicbandlab.common.contracts.packets;

public record PacketRequest(
        String requestType,
        String payload
) {}