package shackle;

import java.io.IOException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import shackle.reservation.ReservationModule;
import shackle.reservation.grpc.ReservationMatchingServiceGrpc.ReservationMatchingServiceImplBase;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * This server uses a basic DI container (Guice) to create the required instances.
 * It adds a ProtoReflectionService for convenient testing with grpcurl.
 * 
 * Possible improvements:
 * - not hard coding the port
 */
public class GrpcServer {

	private static final Logger logger = LogManager.getLogger(GrpcServer.class);

	public static void main(String args[]) throws IOException, InterruptedException {
		logger.trace("starting GRPC Server");
		Injector injector = Guice.createInjector(new ReservationModule());
		Server server = ServerBuilder
				.forPort(9090)
				.addService(injector.getInstance(ReservationMatchingServiceImplBase.class))
				.addService(ProtoReflectionService.newInstance())
				.build();
		server.start();
		logger.trace("server started at " + server.getPort());
		server.awaitTermination();
	}
}
