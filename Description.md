# Reservation Matching Service
For this exercise, you will build a gRPC based server that finds all hotel reservations from a repository for a given user.

## The Problem
Through an integration with a hotel systems, we retrieve all of the reservation data for upcoming reservations for a given hotel. For a user to find their reservation, they have to provide a number of details, which you can use to find the reservations for that user. Details can be things like a booking confirmation number, or an email address, or a phone number, etc. When matching a reservation to a user, there are two competing concerns:

1. We want it to be as easy as possible for a user to find their reservation.
2. We want it to be difficult for a user to claim a reservation that actually belongs to a different user.

For a user to find their reservation, we have to match the reservation to a user based on any number of details of the user. Your service can also ask for more details, if the matching is ambiguous (see service definition below).

### Example
We have an incoming reservation with the following details:
```
internal_id: 201372
web_confirmation_code: 38AE-BB27
guest_details:
  first_name: Ernest
  last_name: Shackleton
  email: ernest@exploreshackle.com
  company_name: Shackleton Exploration Co. Ltd.
arrival_date:
  year: 2021
  month: 10
  day: 8
departure_date:
  year: 2021
  month: 10
  day: 11
```

We then have three users who provide the following information:

User 1:
```
first_name: E.
last_name: Shackleton
email: ernest_s@gmail.com
```

User 2:
```
first_name: Ernest
last_name: Shackleton
email: info@exploreshackle.com
arrival_date:
  year: 2021
  month: 10
  day: 8
```

User 3:
```
first_name: Ernst
last_name: Shackleton
email: explorer1874@hotmail.com
web_confirmation_code: 38AE-BB27
arrival_date:
  year: 2021
  month: 10
  day: 8
```

User 1 may own this reservation, but you might not find the provided information adequate to actually match it. Your service can decide to either respond with no matches at all, or respond with a special response asking for additional fields of information.

User 2 looks like a better match, but still not conclusive.

User 3 clearly has a unique identifier (probably received with the reservation confirmation message), which ties them to this reservation with ambiguity.

## Implementation
For your implementation, you need to do two things:

1. Consume reservations from an existing gRPC service and store them (in memory, no persistence required)
2. Implement a gRPC service based on a given definition, that matches reservations based on user details.

### Consuming Reservations
The service that produces reservations is provided to you as a Docker container, which you can run locally without dependencies (other than Docker). The service implements one single endpoint, which responds with a stream of reservations.

To inspect the service and generate the client code for this service, use the definition provided in the `reservation_service.proto` provided separately.

To run the service locally, use the following:
```
docker run -tp 127.0.0.1:8080:8080/tcp exploreshackle/reservation-service:latest
```

To verify that the service is running correctly, you can use [`grpcurl`](https://github.com/fullstorydev/grpcurl). To install on a Mac, use `brew install grpcurl`. Then try the following:
```
grpcurl -plaintext 127.0.0.1:8080 shackle.reservation.ReservationService.StreamReservations
```

This should show you the output of the stream of reservations. On the shell where you issued the docker run, it should also log the reservations as they are returned to the client.

### Implementing the Service
To implement your service, make a server implementation of the gRPC service definition provided in `matching_service.proto`. Make special note of the return type. If you believe there are improvements to be made to the service definition of the matching service, you can modify the `.proto`.

The required functionality is as follows:

- Request a stream of reservations from the reservation service, and store these in memory in your service. Keep the stream running in the background.
- Upon a matching request from a client, return a response of one of three results:
  - no matching reservation was found
  - a matching reservation might exist, but more information from the guest is needed
  - a matching reservation exists, and is returned

For your implementation, focus on making a minimal implementation work first. Then potentially iterate on your logic for matching, and rules for asking for more information. Finally, consider options for improving on your overall solution. There is likely not enough time to do everything you want; this is fine. Just make a list of proposed improvements that you didn't get to at the end.

## Evaluation
When evaluating a solution, we do so based on the conversation we have with you about your solution. We never base our decisions on just the solution itself. The solution provides a context for your decisions, and considerations when implementing something in a limited amount of time.

When evaluating candidates, we care about the following foundational aspect of software engineering:
- **engineering productivity**: how do you balance the trade off between a deeper understanding of some piece of technology (e.g. a framework, or library) vs. getting something working.
- **software development practices**: within time constraints, how do you apply basic levels of testability, readability, and maintainability in your solution and code.
- **core programming concepts**: do you have valid considerations behind the language constructs, data structures, and lower level implementation details that you apply.
- **system design**: what are your considerations for setting up a solution, with non-functional requirements in mind.
- **productionisation**: (not sure if that's a word) what are your considerations for bringing a solution into a production environment.

Understandably, you might not fully capture all of the above in your solution. Feel free to provide a written list of considerations / recommendations on improvements that you believe should be made to your solution beyond what you had time for yourself.

## Resources
Here is a list of starting points on gRPC to get going.

- [gRPC main website](https://grpc.io/)
- [gRPC basic Java tutorial](https://grpc.io/docs/languages/java/basics/)
- [Protobuf language guide](https://developers.google.com/protocol-buffers/docs/proto3)
- [gRPCurl - command line client for gRPC](https://github.com/fullstorydev/grpcurl)

Final tip:
gRPC relies on code generation to create both client and server side. The code generation can be done from command line, or by integrating it into your build (Maven, Gralde, etc.). While the latter is desirable, if you don't get it up and running quickly, it is perhaps better to focus on the implementation first, and deal with cleaning up the build later.

## Questions?
This is assignment is by no means intended to be tricky, or have hidden quests. If anything is unclear, or if you are stuck, please reach out directly to us (friso@exploreshackle.com and misa@exploreshackle.com).

