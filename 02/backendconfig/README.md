# backendconfig

## Development

To start your application in the dev profile, run:

1. Run mongodb docker container, before running the backend application else the application would fail to start.

```
npm run docker:db:up
```
2. Now, start the backend application with the below given command:

```
./mvnw
```