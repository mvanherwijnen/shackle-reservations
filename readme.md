# Usage

## Installation
Run `mvn clean package` in the root dir.

## Tests
Run `mvn test` in the root dir.

# Considerations

## Solution

To strike a balance between easy-to-use and security, we consider a match exact if:
* it matches on external id. (BookingConfirmationNumber, TravelAgentConfirmationCode or WebConfirmationCode)
* and it matches on one on of:
  * email
  * phone number
  * names (first and last name)

This means we ignore the other fields, as they are not as 'identifying'.

## Implementation
* Considered to drop the dependency injection container, a bit over-engineered for a solution this size
  * Does provide a bit more confidence that wiring is A-OK, since we use the same module in our tests (only overriding things that need to be different for tests)
* Considered to add a configuration solution which could contain configs for dev/stg/prod
* The connection to the ReservationStreamServer can be a bit more resilient against network issues
* Matching can be done a bit less rigorous to improve easu-of-use, e.g. case insensitive email matching and matching on canonical phone numbers
* A DockerFile with a multi-stage build would have been nice to add


