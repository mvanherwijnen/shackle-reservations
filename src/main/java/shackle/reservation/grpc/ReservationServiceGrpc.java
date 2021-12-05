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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.15.0)",
    comments = "Source: reservation_service.proto")
public final class ReservationServiceGrpc {

  private ReservationServiceGrpc() {}

  public static final String SERVICE_NAME = "shackle.reservation.ReservationService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<shackle.reservation.grpc.ReservationServiceOuterClass.Empty,
      shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> getStreamReservationsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "StreamReservations",
      requestType = shackle.reservation.grpc.ReservationServiceOuterClass.Empty.class,
      responseType = shackle.reservation.grpc.ReservationServiceOuterClass.Reservation.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<shackle.reservation.grpc.ReservationServiceOuterClass.Empty,
      shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> getStreamReservationsMethod() {
    io.grpc.MethodDescriptor<shackle.reservation.grpc.ReservationServiceOuterClass.Empty, shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> getStreamReservationsMethod;
    if ((getStreamReservationsMethod = ReservationServiceGrpc.getStreamReservationsMethod) == null) {
      synchronized (ReservationServiceGrpc.class) {
        if ((getStreamReservationsMethod = ReservationServiceGrpc.getStreamReservationsMethod) == null) {
          ReservationServiceGrpc.getStreamReservationsMethod = getStreamReservationsMethod = 
              io.grpc.MethodDescriptor.<shackle.reservation.grpc.ReservationServiceOuterClass.Empty, shackle.reservation.grpc.ReservationServiceOuterClass.Reservation>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "shackle.reservation.ReservationService", "StreamReservations"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shackle.reservation.grpc.ReservationServiceOuterClass.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  shackle.reservation.grpc.ReservationServiceOuterClass.Reservation.getDefaultInstance()))
                  .setSchemaDescriptor(new ReservationServiceMethodDescriptorSupplier("StreamReservations"))
                  .build();
          }
        }
     }
     return getStreamReservationsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReservationServiceStub newStub(io.grpc.Channel channel) {
    return new ReservationServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReservationServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ReservationServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReservationServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ReservationServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class ReservationServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void streamReservations(shackle.reservation.grpc.ReservationServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> responseObserver) {
      asyncUnimplementedUnaryCall(getStreamReservationsMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getStreamReservationsMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                shackle.reservation.grpc.ReservationServiceOuterClass.Empty,
                shackle.reservation.grpc.ReservationServiceOuterClass.Reservation>(
                  this, METHODID_STREAM_RESERVATIONS)))
          .build();
    }
  }

  /**
   */
  public static final class ReservationServiceStub extends io.grpc.stub.AbstractStub<ReservationServiceStub> {
    private ReservationServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationServiceStub(channel, callOptions);
    }

    /**
     */
    public void streamReservations(shackle.reservation.grpc.ReservationServiceOuterClass.Empty request,
        io.grpc.stub.StreamObserver<shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getStreamReservationsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ReservationServiceBlockingStub extends io.grpc.stub.AbstractStub<ReservationServiceBlockingStub> {
    private ReservationServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<shackle.reservation.grpc.ReservationServiceOuterClass.Reservation> streamReservations(
        shackle.reservation.grpc.ReservationServiceOuterClass.Empty request) {
      return blockingServerStreamingCall(
          getChannel(), getStreamReservationsMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ReservationServiceFutureStub extends io.grpc.stub.AbstractStub<ReservationServiceFutureStub> {
    private ReservationServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ReservationServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReservationServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ReservationServiceFutureStub(channel, callOptions);
    }
  }

  private static final int METHODID_STREAM_RESERVATIONS = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ReservationServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ReservationServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_STREAM_RESERVATIONS:
          serviceImpl.streamReservations((shackle.reservation.grpc.ReservationServiceOuterClass.Empty) request,
              (io.grpc.stub.StreamObserver<shackle.reservation.grpc.ReservationServiceOuterClass.Reservation>) responseObserver);
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

  private static abstract class ReservationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReservationServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return shackle.reservation.grpc.ReservationServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReservationService");
    }
  }

  private static final class ReservationServiceFileDescriptorSupplier
      extends ReservationServiceBaseDescriptorSupplier {
    ReservationServiceFileDescriptorSupplier() {}
  }

  private static final class ReservationServiceMethodDescriptorSupplier
      extends ReservationServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReservationServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ReservationServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReservationServiceFileDescriptorSupplier())
              .addMethod(getStreamReservationsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
