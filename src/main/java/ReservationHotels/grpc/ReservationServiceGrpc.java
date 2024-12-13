package ReservationHotels.grpc;

import io.grpc.stub.StreamObserver;

public final class ReservationServiceGrpc {
    private ReservationServiceGrpc() {}

    public static final String SERVICE_NAME = "ReservationHotels.grpc.ReservationService";

    public abstract class ReservationServiceImplBase {
        public abstract void getReservation(GetReservationRequest request, StreamObserver<ReservationResponse> responseObserver);
    }

    // gRPC stub methods (e.g., getReservation, createReservation, etc.)
}
