# *Battle* **Tetris**

## Description:
BattleTetris is a simple, no-frills, two-player Tetris clone. The premise is simple. You and an opponent battle against 
each other head-to-head. Each time a player makes a two or four-line combo, two or four lines of junk are added to the 
opponent's pile. The first pile to the top loses.

## Running the Program:
Double-click on the `.jar` file or type:

```shell
java -jar BattleTetris.jar
```

at the command line.

## Establishing a Connection:
When the program is started, a dialog will ask if you want to be the client or the server; one of each is needed for a 
game. Whoever chooses to be the server will see a waiting window, while the client will see a host selection box. The 
client should enter the IP address listed by the server in order to play. As soon as the connection is established, 
play will ensue.

## Game Play:
Use the arrow keys to play.
- The left and right arrows move the falling piece left and right.
- The up arrow rotates the piece 90 degrees clockwise.
- The down arrow drops the falling piece.
