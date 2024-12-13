package ReservationHotels.grpc;

import ReservationHotels.models.Reservation;
import ReservationHotels.service.ReservationService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@GrpcService
public class ReservationGrpcService extends ReservationServiceGrpc.ReservationServiceImplBase {

    @Autowired
    private ReservationService reservationService;

    @Override
    public void getReservation(GetReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = reservationService.getReservation(request.getId()).orElse(null);
        if (reservation != null) {
            ReservationResponse response = ReservationResponse.newBuilder()
                    .setId(reservation.getId())
                    .setClientName(reservation.getClient().getNom() + " " + reservation.getClient().getPrenom())
                    .setRoomType(reservation.getChambre().getType())
                    .setCheckInDate(reservation.getDateDebut().toString())
                    .setCheckOutDate(reservation.getDateFin().toString())
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onNext(ReservationResponse.getDefaultInstance());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void createReservation(CreateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setClient(new ReservationHotels.models.Client());
        String[] nameParts = request.getClientName().split(" ", 2);
        reservation.getClient().setNom(nameParts[0]);
        reservation.getClient().setPrenom(nameParts.length > 1 ? nameParts[1] : "");
        reservation.setChambre(new ReservationHotels.models.Chambre());
        reservation.getChambre().setType(request.getRoomType());
        reservation.setDateDebut(LocalDate.parse(request.getCheckInDate()));
        reservation.setDateFin(LocalDate.parse(request.getCheckOutDate()));

        Reservation createdReservation = reservationService.createReservation(reservation);
        ReservationResponse response = ReservationResponse.newBuilder()
                .setId(createdReservation.getId())
                .setClientName(createdReservation.getClient().getNom() + " " + createdReservation.getClient().getPrenom())
                .setRoomType(createdReservation.getChambre().getType())
                .setCheckInDate(createdReservation.getDateDebut().toString())
                .setCheckOutDate(createdReservation.getDateFin().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    @Override
    public void updateReservation(UpdateReservationRequest request, StreamObserver<ReservationResponse> responseObserver) {
        Reservation reservation = new Reservation();
        reservation.setId(request.getId());
        reservation.setClient(new ReservationHotels.models.Client());
        String[] nameParts = request.getClientName().split(" ", 2);
        reservation.getClient().setNom(nameParts[0]);
        reservation.getClient().setPrenom(nameParts.length > 1 ? nameParts[1] : "");
        reservation.setChambre(new ReservationHotels.models.Chambre());
        reservation.getChambre().setType(request.getRoomType());
        reservation.setDateDebut(LocalDate.parse(request.getCheckInDate()));
        reservation.setDateFin(LocalDate.parse(request.getCheckOutDate()));

        Reservation updatedReservation = reservationService.updateReservation(request.getId(), reservation);
        if (updatedReservation != null) {
            ReservationResponse response = ReservationResponse.newBuilder()
                    .setId(updatedReservation.getId())
                    .setClientName(updatedReservation.getClient().getNom() + " " + updatedReservation.getClient().getPrenom())
                    .setRoomType(updatedReservation.getChambre().getType())
                    .setCheckInDate(updatedReservation.getDateDebut().toString())
                    .setCheckOutDate(updatedReservation.getDateFin().toString())
                    .build();
            responseObserver.onNext(response);
        } else {
            responseObserver.onNext(ReservationResponse.getDefaultInstance());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void deleteReservation(DeleteReservationRequest request, StreamObserver<DeleteReservationResponse> responseObserver) {
        reservationService.deleteReservation(request.getId());
        DeleteReservationResponse response = DeleteReservationResponse.newBuilder()
                .setSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}

