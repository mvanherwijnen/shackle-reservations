package shackle.reservation.service;

import shackle.reservation.grpc.MatchingService.MatchReservationResponse;
import shackle.reservation.grpc.MatchingService.MatchReservationsRequest;
import shackle.reservation.grpc.MatchingService.MatchResults;
import shackle.reservation.grpc.MatchingService.MissingInformation;
import shackle.reservation.grpc.MatchingService.MatchReservationResponse.Status;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc.ReservationMatchingServiceImplBase;
import shackle.reservation.grpc.ReservationServiceOuterClass.GuestDetails;
import shackle.reservation.grpc.ReservationServiceOuterClass.Reservation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import io.grpc.stub.StreamObserver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * This matching service fetches reservations from the reservation repository.
 * These reservations are checked against two predicates:
 * - guestDetailsPredicate
 * - confirmationCodePredicate
 *
 * The guestDetailsPredicate checks if the email, phone number or the names
 * match.
 * The confirmationCodePredicate checks if the provided confirmation code
 * matches one of the external id's
 * 
 * If both are true, we have return a match response with those reservations
 * If one of them is true, we return a match response with missing fields of the
 * false predicates, iff those fields were not already provided, otherwise no
 * matches
 * If we did not get reservations from the repository, we return a no match
 * response
 * 
 * Possible improvements:
 * - make matching a bit more forgiving, e.g. case insensitive email matching
 * and mathcing on canonical phone numbers
 */
public class MatchingServiceImpl extends ReservationMatchingServiceImplBase {
  private static final Logger logger = LogManager.getLogger(MatchingServiceImpl.class);

  @Inject
  private ReservationRepository reservationRepository;

  @Override
  public void matchReservation(MatchReservationsRequest request,
      StreamObserver<MatchReservationResponse> responseObserver) {
    logger.debug(String.format("Received matching request=%s", request));

    Collection<Reservation> reservations = this.reservationRepository
        .findByMatchRequest(request);
    logger.debug(String.format("Found reservations. Count=%d", reservations.size()));

    MatchReservationResponse response = matchReservations(reservations, request);
    logger.debug(String.format("Response=%s", response));
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }

  private MatchReservationResponse matchReservations(Collection<Reservation> reservations,
      MatchReservationsRequest request) {

    Predicate<Reservation> guestDetailsPredicate = this.guestDetailsPredicate(request);
    Predicate<Reservation> confirmationCodePredicate = this.confirmationCodePredicate(request);

    Collection<Reservation> exactMatchReservations = reservations.stream()
        .filter(guestDetailsPredicate.and(confirmationCodePredicate)).collect(Collectors.toList());

    if (exactMatchReservations.size() > 0) {
      return buildMatchesResponse(exactMatchReservations);
    }

    Collection<Reservation> guestDetailsMatchReservations = reservations.stream()
        .filter(guestDetailsPredicate.and(confirmationCodePredicate.negate())).collect(Collectors.toList());
    Collection<Reservation> confirmationCodeMatchReservations = reservations.stream()
        .filter(guestDetailsPredicate.negate().and(confirmationCodePredicate)).collect(Collectors.toList());

    Collection<String> missingFields = new ArrayList<String>();

    if (guestDetailsMatchReservations.size() > 0) {
      if (isEmpty(request.getConfirmationCode())) {
        missingFields.add("confirmation_code");
      }
    }

    if (confirmationCodeMatchReservations.size() > 0) {
      GuestDetails details = request.getGuestDetails();
      if (isEmpty(details.getFirstName())) {
        missingFields.add("first_name");
      }
      if (isEmpty(details.getLastName())) {
        missingFields.add("last_name");
      }
      if (isEmpty(details.getEmail())) {
        missingFields.add("email");
      }
      if (isEmpty(details.getPhoneNumber())) {
        missingFields.add("phone_number");
      }
    }

    if (missingFields.size() > 0) {
      return buildMissingFieldsResponse(missingFields);
    }

    return buildNoMatchResponse();
  }

  private Predicate<Reservation> guestDetailsPredicate(MatchReservationsRequest request) {

    String email = request.getGuestDetails().getEmail();
    String phone = request.getGuestDetails().getPhoneNumber();
    String firstName = request.getGuestDetails().getFirstName();
    String lastName = request.getGuestDetails().getLastName();

    Predicate<Reservation> predicate = reservation -> notEmptyEquals(reservation.getGuestDetails().getEmail(),
        email) ||
        notEmptyEquals(reservation.getGuestDetails().getPhoneNumber(), phone) ||
        notEmptyEquals(reservation.getGuestDetails().getFirstName(), firstName) &&
            notEmptyEquals(reservation.getGuestDetails().getLastName(), lastName);

    return predicate;
  }

  private Predicate<Reservation> confirmationCodePredicate(MatchReservationsRequest request) {

    String confirmationCode = request.getConfirmationCode();

    Predicate<Reservation> predicate = reservation -> notEmptyEquals(reservation.getBookingConfirmationNumber(),
        confirmationCode) ||
        notEmptyEquals(reservation.getTravelAgentConfirmationCode(), confirmationCode) ||
        notEmptyEquals(reservation.getWebConfirmationCode(), confirmationCode);

    return predicate;
  }

  private MatchReservationResponse buildNoMatchResponse() {
    return MatchReservationResponse.newBuilder()
        .setStatus(Status.STATUS_NO_MATCH).build();
  }

  private MatchReservationResponse buildMissingFieldsResponse(Collection<String> missingFields) {
    return MatchReservationResponse.newBuilder()
        .setStatus(Status.STATUS_NEED_MORE_INFO)
        .setMissingInformation(MissingInformation.newBuilder()
            .addAllFieldName(missingFields)
            .setCount(missingFields.size()))
        .build();
  }

  private MatchReservationResponse buildMatchesResponse(Collection<Reservation> reservations) {
    return MatchReservationResponse.newBuilder()
        .setStatus(Status.STATUS_FOUND)
        .setMatchResults(MatchResults.newBuilder()
            .addAllReservations(reservations)
            .setCount(reservations.size()))
        .build();
  }

  private static boolean notEmptyEquals(String a, String b) {
    return !isEmpty(a) && a.equals(b);
  }

  private static boolean isEmpty(String a) {
    return a == null || a.equals("");
  }
}
