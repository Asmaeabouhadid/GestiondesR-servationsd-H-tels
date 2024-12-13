package ReservationHotels.graphql;

import ReservationHotels.models.Reservation;
import ReservationHotels.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;

import java.time.LocalDate;
import java.util.List;

@Component
public class ReservationResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private ReservationService reservationService;

    public Reservation reservation(Long id) {
        return reservationService.getReservation(id).orElse(null);
    }

    public List<Reservation> allReservations() {
        return reservationService.getAllReservations();
    }

    public Reservation createReservation(ReservationInput input) {
        Reservation reservation = new Reservation();
        reservation.setClient(new ReservationHotels.models.Client());
        String[] nameParts = input.getClientName().split(" ", 2);
        reservation.getClient().setNom(nameParts[0]);
        reservation.getClient().setPrenom(nameParts.length > 1 ? nameParts[1] : "");
        reservation.setChambre(new ReservationHotels.models.Chambre());
        reservation.getChambre().setType(input.getRoomType());
        reservation.setDateDebut(LocalDate.parse(input.getCheckInDate()));
        reservation.setDateFin(LocalDate.parse(input.getCheckOutDate()));
        return reservationService.createReservation(reservation);
    }

    public Reservation updateReservation(Long id, ReservationInput input) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setClient(new ReservationHotels.models.Client());
        String[] nameParts = input.getClientName().split(" ", 2);
        reservation.getClient().setNom(nameParts[0]);
        reservation.getClient().setPrenom(nameParts.length > 1 ? nameParts[1] : "");
        reservation.setChambre(new ReservationHotels.models.Chambre());
        reservation.getChambre().setType(input.getRoomType());
        reservation.setDateDebut(LocalDate.parse(input.getCheckInDate()));
        reservation.setDateFin(LocalDate.parse(input.getCheckOutDate()));
        return reservationService.updateReservation(id, reservation);
    }

    public boolean deleteReservation(Long id) {
        reservationService.deleteReservation(id);
        return true;
    }
}

