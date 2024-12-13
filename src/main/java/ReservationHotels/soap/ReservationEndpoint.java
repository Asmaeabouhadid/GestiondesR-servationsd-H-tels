package ReservationHotels.soap;

import ReservationHotels.models.Reservation;
import ReservationHotels.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Endpoint
public class ReservationEndpoint {
    private static final String NAMESPACE_URI = "http://reservationhotels.com/soap";

    @Autowired
    private ReservationService reservationService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getReservationRequest")
    @ResponsePayload
    public <GetReservationResponse> GetReservationResponse getReservation(@RequestPayload GetReservationRequest request) {
        GetReservationResponse response = new GetReservationResponse();
        Reservation reservationEntity = reservationService.getReservation(request.getId()).orElse(null);

        if (reservationEntity != null) {
            ReservationHotels.soap.Reservation reservation = new ReservationHotels.soap.Reservation();
            reservation.setId(reservationEntity.getId());
            reservation.setClientName(reservationEntity.getClient().getNom() + " " + reservationEntity.getClient().getPrenom());
            reservation.setRoomType(reservationEntity.getChambre().getType());

            try {
                XMLGregorianCalendar checkInDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        GregorianCalendar.from(reservationEntity.getDateDebut().atStartOfDay(ZoneId.systemDefault())));
                XMLGregorianCalendar checkOutDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                        GregorianCalendar.from(reservationEntity.getDateFin().atStartOfDay(ZoneId.systemDefault())));

                reservation.setCheckInDate(checkInDate);
                reservation.setCheckOutDate(checkOutDate);
            } catch (Exception e) {
                // Handle the exception (e.g., log it)
                e.printStackTrace();
            }

            response.setReservation(reservation);
        }

        return response;
    }
}

