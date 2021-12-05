package shackle.reservation.service;

import shackle.reservation.grpc.ReservationServiceGrpc;
import shackle.reservation.grpc.MatchingService.MatchReservationsRequest;
import shackle.reservation.grpc.ReservationServiceGrpc.ReservationServiceStub;
import shackle.reservation.grpc.ReservationServiceOuterClass.Empty;
import shackle.reservation.grpc.ReservationServiceOuterClass.Reservation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.google.inject.name.Named;

import org.apache.logging.log4j.Logger;

import io.grpc.Channel;
import io.grpc.stub.StreamObserver;

import org.apache.logging.log4j.LogManager;

/**
 * This repository opens a reservation stream upon initialization. The Channel is injected.
 * It implements the findByMatchRequest by exact string matching on phone number, email, 
 * names or confirmation code. Other fields in the matching request are ignored since they 
 * are too generic.
 * 
 * Possible improvements:
 * - reconnecting to server upon network problems
 * - make matching a bit more forgiving, e.g. case insensitive email matching and mathcing on canonical phone numbers
 */
public class ReservationRepositoryImpl implements ReservationRepository {
  private static final Logger logger = LogManager.getLogger(ReservationRepositoryImpl.class);

  private Collection<Reservation> reservations;

  private ReservationServiceStub stub;

  @Inject
  public ReservationRepositoryImpl(@Named("reservation-stream") Channel channel) {
    reservations = new ArrayList<Reservation>();
    stub = ReservationServiceGrpc.newStub(channel);
    stub.streamReservations(Empty.newBuilder().build(), new StreamObserver<Reservation>() {

      @Override
      public void onNext(Reservation value) {
        logger.debug(String.format("Received reservation=%s", value));
        reservations.add(value);
      }

      @Override
      public void onError(Throwable t) {
        logger.error(String.format("Reservation stream failed: %s", t.getMessage()));
      }

      @Override
      public void onCompleted() {
        logger.info("Reservation stream finished");
      }
    });
  }

  @Override
  public Collection<Reservation> findByMatchRequest(MatchReservationsRequest request) {
    logger.info(String.format("Searched on: %s", request));

    String email = request.getGuestDetails().getEmail();
    String phone = request.getGuestDetails().getPhoneNumber();
    String firstName = request.getGuestDetails().getFirstName();
    String lastName = request.getGuestDetails().getLastName();
    String confirmationCode = request.getConfirmationCode();

    Predicate<Reservation> matchesRequestPredicate = reservation -> this
        .notEmptyEquals(reservation.getGuestDetails().getEmail(), email) ||
        this.notEmptyEquals(reservation.getGuestDetails().getPhoneNumber(), phone) ||
        this.notEmptyEquals(reservation.getBookingConfirmationNumber(), confirmationCode) ||
        this.notEmptyEquals(reservation.getTravelAgentConfirmationCode(), confirmationCode) ||
        this.notEmptyEquals(reservation.getWebConfirmationCode(), confirmationCode) ||
        this.notEmptyEquals(reservation.getGuestDetails().getFirstName(), firstName) &&
            this.notEmptyEquals(reservation.getGuestDetails().getLastName(), lastName);

    return reservations.stream().filter(matchesRequestPredicate).collect(Collectors.toList());
  }

  private boolean notEmptyEquals(String a, String b) {
    return a != null && !a.equals("") && a.equals(b);
  }
}