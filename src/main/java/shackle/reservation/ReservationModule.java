package shackle.reservation;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc.ReservationMatchingServiceImplBase;
import shackle.reservation.service.MatchingServiceImpl;
import shackle.reservation.service.ReservationRepository;
import shackle.reservation.service.ReservationRepositoryImpl;

/**
 * Possible improvements:
 * - get the address name and port from a config solution, which defines values for dev/stg/prod
 */
public class ReservationModule extends AbstractModule {
  @Override
  protected void configure() {
    bind(ReservationRepository.class).to(ReservationRepositoryImpl.class).in(Scopes.SINGLETON);
    bind(ReservationMatchingServiceImplBase.class).to(MatchingServiceImpl.class);
    bind(Channel.class).annotatedWith(Names.named("reservation-stream"))
        .toInstance(ManagedChannelBuilder.forAddress("127.0.0.1", 8080).usePlaintext().build());
  }
}
