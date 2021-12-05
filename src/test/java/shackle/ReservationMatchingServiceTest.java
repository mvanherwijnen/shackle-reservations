package shackle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import shackle.reservation.ReservationModule;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc;
import shackle.reservation.grpc.MatchingService.MatchReservationResponse;
import shackle.reservation.grpc.MatchingService.MatchReservationsRequest;
import shackle.reservation.grpc.MatchingService.MatchReservationResponse.Status;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc.ReservationMatchingServiceBlockingStub;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc.ReservationMatchingServiceImplBase;
import shackle.reservation.grpc.ReservationServiceGrpc.ReservationServiceImplBase;
import shackle.reservation.grpc.ReservationServiceOuterClass.Empty;
import shackle.reservation.grpc.ReservationServiceOuterClass.GuestDetails;
import shackle.reservation.grpc.ReservationServiceOuterClass.Reservation;
import java.util.Collection;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.util.Modules;

import org.junit.Test;
import io.grpc.testing.GrpcCleanupRule;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.stub.StreamObserver;

import org.junit.Before;
import org.junit.Rule;

/**
 * These tests use a (in process) FakeReservationStreamServer. You can pass multiple 
 * reservations to its serveReservations function, so the ReservationRepository gets 
 * populated by these. This happens since the Channel injected in the 
 * ReservationRepository is overridden by the TestReservationModule.
 * 
 * Possible improvements:
 * - not 100% happy with readibility
 * - (nit) the InprocessChannel name for the FakeReservationStreamServer is defined in two places
 */
public class ReservationMatchingServiceTest {

  private ReservationMatchingServiceBlockingStub matchingServiceStub;
  private FakeReservationStreamServer fakeReservationStreamServer;

  @Rule
  public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

  @Test
  public void should_return_no_matches_when_no_matches_can_be_found() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setGuestDetails(GuestDetails.newBuilder()
            .setEmail("hello@bye.com"))
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setGuestDetails(GuestDetails.newBuilder()
                .setEmail("does.not.exists@email.com"))
            .build());

    assertEquals(Status.STATUS_NO_MATCH, response.getStatus());
  }

  @Test
  public void should_return_no_matches_when_missing_field_is_already_provided() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setGuestDetails(GuestDetails.newBuilder()
            .setEmail("hello@bye.com"))
        .setWebConfirmationCode("Web-code")
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setGuestDetails(GuestDetails.newBuilder()
                .setEmail("hello@bye.com"))
            .setConfirmationCode("Non-matching-code")
            .build());

    assertEquals(Status.STATUS_NO_MATCH, response.getStatus());
  }

  @Test
  public void should_return_no_matches_when_only_first_name_matches() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setGuestDetails(GuestDetails.newBuilder()
            .setFirstName("John")
            .setLastName("Peterson"))
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setGuestDetails(GuestDetails.newBuilder()
                .setFirstName("John"))
            .build());

    assertEquals(Status.STATUS_NO_MATCH, response.getStatus());
  }

  @Test
  public void should_return_more_info_needed_when_both_names_match() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setGuestDetails(GuestDetails.newBuilder()
            .setFirstName("John")
            .setLastName("Peterson"))
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setGuestDetails(GuestDetails.newBuilder()
                .setFirstName("John")
                .setLastName("Peterson"))
            .build());

    assertEquals(Status.STATUS_NEED_MORE_INFO, response.getStatus());
    assertTrue(response.getMissingInformation().getFieldNameList().contains("confirmation_code"));
  }

  @Test
  public void should_return_more_info_needed_when_only_email_matches() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setGuestDetails(GuestDetails.newBuilder()
            .setEmail("hello@bye.com"))
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setGuestDetails(GuestDetails.newBuilder()
                .setEmail("hello@bye.com"))
            .build());

    assertEquals(Status.STATUS_NEED_MORE_INFO, response.getStatus());
    assertTrue(response.getMissingInformation().getFieldNameList().contains("confirmation_code"));
  }

  @Test
  public void should_return_more_info_needed_when_only_confirmation_code_matches() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setBookingConfirmationNumber("booking-number")
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setConfirmationCode("booking-number")
            .build());

    assertEquals(Status.STATUS_NEED_MORE_INFO, response.getStatus());
    Collection<String> list = response.getMissingInformation().getFieldNameList();
    assertTrue(list.contains("first_name"));
    assertTrue(list.contains("last_name"));
    assertTrue(list.contains("email"));
    assertTrue(list.contains("phone_number"));
  }

  @Test
  public void should_return_reservations_when_confirmation_code_and_a_guest_detail_matches() {
    fakeReservationStreamServer.serveReservations(Reservation.newBuilder()
        .setBookingConfirmationNumber("booking-number")
        .setGuestDetails(GuestDetails.newBuilder()
            .setFirstName("John")
            .setLastName("Maverick")
            .setEmail("john@maverick.com")
            .setPhoneNumber("(123)4567-891"))
        .build());

    MatchReservationResponse response = matchingServiceStub
        .matchReservation(MatchReservationsRequest.newBuilder()
            .setConfirmationCode("booking-number")
            .setGuestDetails(GuestDetails.newBuilder()
                .setFirstName("John")
                .setLastName("Maverick"))
            .build());

    assertEquals(Status.STATUS_FOUND, response.getStatus());

    response = matchingServiceStub.matchReservation(MatchReservationsRequest.newBuilder()
        .setConfirmationCode("booking-number")
        .setGuestDetails(GuestDetails.newBuilder()
            .setPhoneNumber("(123)4567-891"))
        .build());

    assertEquals(Status.STATUS_FOUND, response.getStatus());

    response = matchingServiceStub.matchReservation(MatchReservationsRequest.newBuilder()
        .setConfirmationCode("booking-number")
        .setGuestDetails(GuestDetails.newBuilder()
            .setEmail("john@maverick.com"))
        .build());

    assertEquals(Status.STATUS_FOUND, response.getStatus());
  }

  @Before
  public void init() throws Exception {
    fakeReservationStreamServer = new FakeReservationStreamServer();
    grpcCleanup.register(InProcessServerBuilder
        .forName("test")
        .directExecutor()
        .addService(fakeReservationStreamServer)
        .build()
        .start());

    String serverName = InProcessServerBuilder.generateName();
    Injector injector = Guice
        .createInjector(Modules.override(new ReservationModule())
            .with(new TestReservationModule()));

    grpcCleanup.register(InProcessServerBuilder
        .forName(serverName)
        .directExecutor()
        .addService(injector.getInstance(ReservationMatchingServiceImplBase.class))
        .build()
        .start());

    matchingServiceStub = ReservationMatchingServiceGrpc.newBlockingStub(
        grpcCleanup.register(
            InProcessChannelBuilder.forName(serverName).directExecutor().build()));
  }

  private class FakeReservationStreamServer extends ReservationServiceImplBase {
    private StreamObserver<Reservation> responseObserver;

    public void serveReservations(Reservation... reservations) {
      for (Reservation reservation : reservations) {
        responseObserver.onNext(reservation);
      }
      responseObserver.onCompleted();
    }

    @Override
    public void streamReservations(Empty request, StreamObserver<Reservation> responseObserver) {
      this.responseObserver = responseObserver;
    }
  }
}
