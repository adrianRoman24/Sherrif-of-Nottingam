Sheriff of Nottingham

Important remarks:
    - the game class play method is the one that simulates a game
    - addKingAndQueenBonus verification method for each type of legal good
if there is a king or queen in the order in which the players were given
    
Implementation logic and classes used:

    In the main method we take over the list of players, the cards and the number of rounds,
and with these we create our game (Game). In order to work, the Game needs of:
    - the list of players who can be Basic (Normal Player),
Greedy (Player subclass) or Bribe (also Player subclass) in
depending on each one's strategy. I used these legacies because of the classes
Bribe and Greedy use in certain situations the methods (style) of playing from
the basic player (for example, when Bribe does not have enough money then
creates the same box as the base player. The player class fields are
quite intuitive since I used a list of integers of goods) for each type of
goods we needed (goods in hand, bag and goods brought on the stall. I used some
type fields whole to retain the treasury (money), bribe (bribe), the initial index,
the type of goods declared and a Boolean type that shows me if a player
he is a liar or not when he prepares his sack. There are several methods
essential in the Player class and in its heirs:

    * prepareBox that prepares the bag for each type of player depending
of the conditions specified in the statement (it should be noted that Bribe and Greedy are
use the code already written in Player).

    * playAsSheriff is the function in which the current player inspects another
player when he is sheriff. The function returns a list of integers
(if applicable) representing the ids of the goods confiscated by the sheriff and
makes the "transfer" of bribes or (and) penalties from the sheriff to the merchant
if appropriate.

    * addIllegalBonusToBroughtGoods add all at the end of the last round
legal goods from the illegal goods bonus, and only then is it
calculated the final profit of a player with the calculateProfit function

    - the list of ids of the goods (or of the books in the package). These are
divided 10 at a time only to the traders in each under the round.

    The changes are visible in each player's fields after a round of
game. For the most part, the data is taken over and the function is run
play, and in the play method of the Game class the game itself takes place.
