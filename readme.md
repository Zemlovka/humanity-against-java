# Humanity Against Java

4IT353 (Klient/Server v Jave) - První semestrální úloha 

This project is an online implementation of the board game “Cards Against Humanity” written in pure Java, featuring a
server and client using JavaFX. The communication between the client and server is managed through sockets with
CompletableFuture and polling, utilizing multiple threads.

## Table of Contents

1. [Basic Description](#basic-description)
2. [Game Flow](#game-flow)
3. [Unresolved Issues](#unresolved-issues)
4. [Running the Server](#running-the-server)
5. [Running the Client](#running-the-client)
6. [Technical Details](#technical-details)
7. [Dependencies](#dependencies)
8. [Contributing](#contributing)
9. [License](#license)

## Basic Description

The client application provides a user interface where players can see their cards, the cards on the table, the active "
black card," and other game information. It allows players to interact with the game, including selecting cards and
submitting responses. The client connects to the server via sockets, establishing a connection to send messages (via the
GUI) such as selecting cards or responding to the "black card," and to receive messages from the server like game state
updates and new cards.

The server manages the game flow, including when cards are played and who wins each round. It maintains the game state,
including the list of players, their cards, and the current cards on the table. Upon receiving a message from a client,
the server responds according to the game rules and updates the game state. The server features “lobbies” that players
can create and join. Clients and the server communicate using text messages in JSON format, which are
serialized/deserialized to/from objects for the internal game logic to process.

## Game Flow

A player connects to the server by entering the IP address and port of a server, then provides a username. The player can then
create a new lobby or join an existing one. Once a sufficient number of participants join, the game starts
automatically. The game flow is the same for all participants:

1. Players are randomly assigned several white cards (with text to fill in the black cards) that are displayed to them.
2. The server starts the first round by displaying a black card with a prompt and a blank space.
3. Participants have unlimited time to choose the funniest response from their white cards.
4. Once all participants have selected their cards, all choices are displayed for everyone to see, and each player can
   vote for the best response.
5. The response with the most votes wins the round, and points are awarded to the player who submitted it.

The rounds continue until the end of the game, with the player having the most points declared the winner.

## Unresolved Issues

- Password protection for lobbies
- Timer for player responses
- Restriction on voting for one's own card
- Unification of the polling mechanism on both server and client sides, as well as between commands

## Running the Server

To start the server, execute the following command:

```sh
java -jar haj-server-1.0.jar
```

You will be prompted to enter port (e.g., 8080) or press Enter to use the default values.
The server will then start, waiting for clients to connect.

## Running the Client

To start the client, execute the following command:

```sh
java -jar haj-client-1.0.jar
```

You will be prompted to enter the host (e.g., 127.0.0.1) and port (e.g., 8080) or press Enter to use the default values.
The application will then launch, allowing you to enter a username, create or join a lobby, and start playing.

## Technical Details

### Server

- Written in pure Java
- Uses sockets for communication
- Manages multiple clients using multiple threads
- Game logic and state management
- Uses CompletableFuture for asynchronous tasks

### Client

- Written in pure Java
- Uses JavaFX for the graphical user interface
- Connects to the server via sockets
- Displays game state and allows user interaction

#### Dependencies

- Java SE Development Kit 11 or higher
- JavaFX SDK

## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a new branch

```sh 
git checkout -b feature-branch
```

3. Commit your changes

```sh
git commit -m 'Add new feature'
 ```

4. Push to the branch

```sh 
git push origin feature-branch
 ```

5. Open a pull request
## Licence
This project is licensed under the MIT License. See the LICENSE file for more details.
