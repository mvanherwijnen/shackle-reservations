package shackle;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import io.grpc.Channel;
import io.grpc.inprocess.InProcessChannelBuilder;

public class TestReservationModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Channel.class).annotatedWith(Names.named("reservation-stream")).toInstance(InProcessChannelBuilder.forName("test").usePlaintext().build());
  }

}
