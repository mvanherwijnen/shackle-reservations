package shackle.reservation.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * This is the single RPC that you need to implement. It has one method for matching
 * a reservation.
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: matching_service.proto")
public final class ReservationMatchingServiceGrpc {

  private ReservationMatchingServiceGrpc() {}

  public static final String SERVICE_NAME = "shackle.reservation.ReservationMatchingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shackle.reservation.grpc.MatchingService.MatchReservationsRequest,
      shackle.reservation.grpc.MatchingService.MatchReservationResponse> getMatchReservationMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "MatchReservation",
      requestType = shackle.reservation.grpc.MatchingService.MatchReservationsRequest.class,
      responseType = shackle.reservation.grpc.MatchingService.MatchReservationResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<shackle.reservation.grpc.MatchingService.MatchReservationsRequest,
      shackle.reservation.grpc.MatchingService.MatchReservationResponse> getMatchReservationMethod() {
    io.grpc.MethodDescriptor<shackle.reservation.grpc.MatchingService.MatchReservationsRequest, shackle.reservation.grpc.MatchingService.MatchReservationResponse> getMatchReservationMethod;
    if ((getMatchReservationMethod = ReservationMatchingServiceGrpc.getMatchReservationMethod) == null) {
      synchronized (ReservationMatchingServiceGrpc.class) {
        if ((getMatchReservationMethod = ReservationMatchingServiceGrpc.getMatchReservationMethod) == null) {
          ReservationMatchingServiceGrpc.getMatchReservationMethod = getMatchReservationMethod = 
              io.grpc.MethodDescriptor.<shackle.reservation.grpc.MatchingService.MatchReservationsRequest, shackle.reservation.grpc.MatchingService.MatchReservationResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "shackle.reservation.ReservationMatchingService", "MatchReservation"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shackle.reservation.grpc.MatchingService.MatchReservationsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shackle.reservation.grpc.MatchingService.MatchReservationResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ReservationMatchingServiceMethodDescriptorSupplier("MatchReservation"))
                  .build();
          }
        }
     }
     return getMatchReservationMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReservationMatchingServiceStub newStub(io.grpc.Channel channel) {
    return new ReservationMatchingServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReservationMatchingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ReservationMatchingServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReservationMatchingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ReservationMatchingServiceFutureStub(channel);
  }

  /**
   * <pre>
   * This is the single RPC that you need to implement. It has one method for matching
   * a reservation.
   * </pre>
   */
  public static abstract class ReservationMatchingServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void matchReservation(shackle.reservation.grpc.MatchingService.MatchReservationsRequest request,
        io.grpc.stub.StreamObserver<shackle.reservation.grpc.MatchingService.MatchReservationResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMatchReservationMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getMatchReservationMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                shackle.reservation.grpc.MatchingService.MatchReservationsRequest,
                shackle.reservation.grpc.MatchingService.MatchReservationResponse>(
                  this, METHODID_MATCH_RESERVATION)))
          .build();
    }
  }

  /**
   * <pre>
   * This is the single RPC that you need to implement. It has one method for matching
   * a reservation.
   * </pre>
   */
  public static final class ReservationMatchingServiceStub extends io.grpc.stub.AbstractStub<ReservationMatchingServiceStub> {
    private ReservationMatchingServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationMatchingServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationMatchingServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationMatchingServiceStub(channel, callOptions);
    }

    /**
     */
    public void matchReservation(shackle.reservation.grpc.MatchingService.MatchReservationsRequest request,
        io.grpc.stub.StreamObserver<shackle.reservation.grpc.MatchingService.MatchReservationResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getMatchReservationMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   * This is the single RPC that you need to implement. It has one method for matching
   * a reservation.
   * </pre>
   */
  public static final class ReservationMatchingServiceBlockingStub extends io.grpc.stub.AbstractStub<ReservationMatchingServiceBlockingStub> {
    private ReservationMatchingServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationMatchingServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationMatchingServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationMatchingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public shackle.reservation.grpc.MatchingService.MatchReservationResponse matchReservation(shackle.reservation.grpc.MatchingService.MatchReservationsRequest request) {
      return blockingUnaryCall(
          getChannel(), getMatchReservationMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * This is the single RPC that you need to implement. It has one method for matching
   * a reservation.
   * </pre>
   */
  public static final class ReservationMatchingServiceFutureStub extends io.grpc.stub.AbstractStub<ReservationMatchingServiceFutureStub> {
    private ReservationMatchingServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationMatchingServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationMatchingServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationMatchingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<shackle.reservation.grpc.MatchingService.MatchReservationResponse> matchReservation(
        shackle.reservation.grpc.MatchingService.MatchReservationsRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getMatchReservationMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_MATCH_RESERVATION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReservationMatchingServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReservationMatchingServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_MATCH_RESERVATION:
          serviceImpl.matchReservation((shackle.reservation.grpc.MatchingService.MatchReservationsRequest) request,
              (io.grpc.stub.StreamObserver<shackle.reservation.grpc.MatchingService.MatchReservationResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ReservationMatchingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReservationMatchingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return shackle.reservation.grpc.MatchingService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReservationMatchingService");
    }
  }

  private static final class ReservationMatchingServiceFileDescriptorSupplier
      extends ReservationMatchingServiceBaseDescriptorSupplier {
    ReservationMatchingServiceFileDescriptorSupplier() {}
  }

  private static final class ReservationMatchingServiceMethodDescriptorSupplier
      extends ReservationMatchingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReservationMatchingServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ReservationMatchingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReservationMatchingServiceFileDescriptorSupplier())
              .addMethod(getMatchReservationMethod())
              .build();
        }
      }
    }
    return result;
  }
}
