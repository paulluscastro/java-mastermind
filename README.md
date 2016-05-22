# MASTER MIND API v1.0

## Overview

This API allows you to develop your own Mastermind game with HTML5, CSS3 and Javascript. Don't worry about game rules, as they are already implemented in this API. This documentation have all the information you need to make a good use of this API.

This API have 3 gaming modes:

1. Single Player
2. Challenge
3. Multiplayer

## General rules

### The Game Rules Object

Every time a Game is created the player will receive the Game Rules, which can be seen in th example bellow: 

```javascript
{
	  "key" : "81667709EE96491EB04E8A5BD0897690",
	  "allowed" : "ABCDEF",
	  "minutesAlive" : 5,
	  "maximumGuesses" : 0,
	  "passwordSize" : 4,
	  "repeatAllowed" : true
}
```

Where:

- **key** is the game identification in the pool. You need it to make guesses;
- **allowed** are the letters that were used to make the password. You can try to guess with letter that are listed here, no error will be thrown by the server;
- **minutesAlive** is the time, in minutes, this game will be hold in the game pool. After that it will expire and no longer be available;
- **maximumGuesses** is the maximum number of tries allowed to this game: 0 means infinite tries;
- **passwordSize** is the size of the generated password;
- **repeatAllowed** indicates that the password could have been created with repeated letters;

### The Guess Object

Everytime a user makes a guess, the API will respond with a Guess object. Example bellow:
```javascript
{
  "guess" : "ABCD",
  "clue" : "9XXX",
  "dateTime" : "21/05/2016 21:18:41",
  "status" : "WRONG"
}
```

Where:

- **guess** is the guess sent by the user;
- **clue** is the result of the processed guess;
- **dateTime** is the date/hour (dd/mm/yyyy HH:MM:SS) the user made the guess (date and hour taken from the server);
- **status** is the result of the guess;

Games in a the Multiplayer Mode will also return the name of the player who made the guess.

### The Game Object

When the game is over (user discovers the password or game reaches maximum tries), you will receive the game object:
```javascript
{
  "created" : "21/05/2016 21:58:32",
  "closed" : "21/05/2016 21:58:49",
  "key" : "0B300E67C64E494FAB33FBF9FC8A5DF6",
  "password" : "FEBA",
  "rules" : {
    "key" : "0B300E67C64E494FAB33FBF9FC8A5DF6",
    "allowed" : "ABCDEF",
    "minutesAlive" : 5,
    "maximumGuesses" : 0,
    "passwordSize" : 4,
    "repeatAllowed" : true
  },
  "guesses" : [ {
    "guess" : "FEBA",
    "clue" : "1111",
    "dateTime" : "21/05/2016 21:58:49",
    "status" : "CORRECT"
  } ],
  "status" : "BEATEN",
  "expired" : false
}
```

Where

- **created** is the date/hour (dd/mm/yyyy HH:MM:SS) the game was created (date and hour taken from the server);
- **closed** is the date/hour (dd/mm/yyyy HH:MM:SS) the game was finished (date and hour taken from the server);
- **key** unique identification of this game;
- **password** game's password;
- **rules** the Game Rules object previously mentioned;
- **guesses** an array of all guesses made to this game;
- **status** status of the game (BEATEN, LOST, WAIT);
- **expired** is used by the BackEnd server to verify if the game expired it's "minutes alive" or not, you can ignore this information;






## SINGLE PLAYER MODE

To play in the Single Player Mode the base URL is:

```
http://<server-name>/mastermind/api/single
```

Allowed commands are listed bellow:

#### create

- **Command:** create
- **HTTP Method:** GET
- **Example:** `http://server-name/mastermind/api/single/create`
- **Description:** The "create" command will create a new Game and store it in the Games Pool. When successful, it will return a HTTP 200 code and the Game Rules JSON (mentioned in the General Rules topic).

#### guess

- **Command:** guess/{key}/{guess}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/single/guess/81667709EE96491EB04E8A5BD0897690/ABCD`
- **Description:** The "guess" command will try to solve the password. When successful, it will return a HTTP 200 code and an JSON object. The returned object depends on the result of the guess: if the password was not fully discovered then this method will return the Guess object (mentioned in the General Rules topic), else if the password is fully discovered OR the game reaches it's maximum possible tries, it will return the Game object (mentioned in the General Rules topic);
	
##### Important information
- if the user informs an inexistent key, this method will return HTTP Code 204;
- if the password size does not match the Game Rules size, the API will return the biggest one, filling with X the remained size;



## CHALLENGE MODE

To play in the Single Player Mode the base URL is:

```
http://<server-name>/mastermind/api/challenge
```

Allowed commands are listed bellow:

#### create

- **Command:** create
- **HTTP Method:** POST
- **Data:** The Proposed Game Rules, which will be explained bellow
- **Content type:** application/json
- **Example:** `http://<server-name>/mastermind/api/challenge/create`
- **Description:** The "create" command will create a new Game and store it in the Games Pool. When successful, it will return a HTTP 200 code and the Game Rules JSON (mentioned in the General Rules topic). The difference of this create command from the one of Single Player Mode is that this one can receive a Proposed Game Rules Object which has a "password" property, that allows you to inform the password you want to be used in the game. You can see the object description bellow:

```javascript
{
  "password" : "AUWZ",
  "allowed" : "TUVWXYZ",
  "minutesAlive" : 5,
  "maximumGuesses" : 200,
  "passwordSize" : 12,
  "allowRepeats" : true
}	
```

Where		

- **password** is the proposed password. **NOTE THAT** it can contain letters that are not in the "allowed" property;
- **allowRepeats** differs from the "Game Rules Object" "repeatAllowed";

#### guess

- **Command:** guess/{key}/{guess}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/challenge/guess/81667709EE96491EB04E8A5BD0897690/ABCD`
- **Description:** Similar to the "guess" command from the Single Player Mode






## MULTIPLAYER PLAYER MODE

To play in the Multiplayer Mode the base URL is:

```
http://<server-name>/mastermind/api/multi
```

Allowed commands are listed bellow:

#### create

- **Command:** create/{player}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/multi/create/paullus`
- **Description:** The "create" command will create a new Multiplayer Game, add the informed user and store it in the Games Pool. When successful, it will return a HTTP 200 code and the Multiplayher Game Info object listed bellow:
	
```javascript
{
  "key" : "0843BD687AD04C228CE169BA4323F286",
  "rules" : {
    "key" : "0843BD687AD04C228CE169BA4323F286",
    "allowed" : "ABCDEF",
    "minutesAlive" : 5,
    "maximumGuesses" : 0,
    "passwordSize" : 4,
    "repeatAllowed" : true
  },
  "guesses" : [ ],
  "status" : "WAIT",
  "registeredPlayers" : [ "PAULLUS" ]
}
```
	
Where:

- **key** unique game identifier;
- **rules** game rules object;
- **guesses** array with total tries until that moment;
- **status** status of the game; WAIT means it's waiting for, at least, 2 players to enter;
- **registeredPlayers** array containing the name of the players;

#### enter

- **Command:** enter/{key}/{user}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/multi/enter/35D5B41695A249F9BCB95DB56D412165/axiom`
- **Description:** Inserts the user into the Registered Users pool of the game, allowing him or her to make guesses. If the user is already inserted in the game's pool, then no action is taken, but if the inserted player didn't exist yet, he or she is inserted in the game's pool and the status of the game changes to "OPEN", wich permits the pĺayers to make guesses.

#### status

- **Command:** status/{key}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/multi/status/35D5B41695A249F9BCB95DB56D412165`
- **Description:** Returns the status of the game, if no game is found it returns the message "Multiplayer game not found". 

#### guess

- **Command:** guess/{key}/{user}/{guess}
- **HTTP Method:** GET
- **Example:** `http://<server-name>/mastermind/api/multi/guess/35D5B41695A249F9BCB95DB56D412165/paullus/DBBD`
- **Description:** Similar to the "guess" command from the Single Player Mode, but instead of the last guess made by the user returns all the guesses made;

## Questions

#### 1. What was the hardest part?

The hardest part of all was to decided which kind of implementation should I use. Actually I made 3 projects before deciding for the architecture that I used.

#### 2. If you could go back and give yourself advice at the beginning of the project, what would it be?

_*"Read the entire challenge and test the provided example, before making decisions and do some code."*_

#### 3. If you could change something about this challenge, what would it be?

I did it. In the challenge mode I made the game customizable. You can set available characters if you want to. You can also decide the size of the password, if it will have repeated characters or not. I could have made it available on Single Player and Multiplayer modes also, but I was afraid it could have drained too much of my time. Maybe I'll do it after a few days.
