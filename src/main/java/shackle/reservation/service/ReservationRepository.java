package shackle.reservation.service;

import java.util.Collection;

import shackle.reservation.grpc.MatchingService.MatchReservationsRequest;
import shackle.reservation.grpc.ReservationServiceOuterClass.Reservation;

public interface ReservationRepository {
  public Collection<Reservation> findByMatchRequest(MatchReservationsRequest request);
}
