# RockSystem

This is a java based system simulating some of the player actions found in the game Red Dead Redemption 2. The goal of the project is to demonstrate some object oriented design and game scripting in a fun way. The player can buy and sell items to merchants, manage their horses, rob merchants and shoot weapon, move locations and track their stats and inventory. To play, simply run:
```
cd src/
```
```
javac Main.java
```
```
java Main
```

The game is text based. To play, type any of the available commands to choose a player action. 

More info:

* The player has 3 stats Health, Stamina and DeadEye
* The player has a satchel of items which they can use, sell or add to by buying more items from merchants
* The player can have one equipped horse at a time, it can be bonded with by patting it
* If a shot is fired near a merchant, or they are robbed, their emotional state will change to scated and they will refuse to serve the player again
* Merchants can only be interacted with when within a 5m range
* Movement is based off simple x,y coordinate input.
* Weapon can be shot in any location, it only scares a merchant when they are within the 5m range.
* When a horse is bought, previous horse is stored in stable. Player can change equipped horse when in range of stable. 
* Only one horse can be equipped at a time, multiple can be owned.
* Buyable class exists as a parent class to Items and Horses, as both are tradeable.


Screenshot:

<img width="530" alt="image" src="https://github.com/KidsSeeGhosts/RockSystem/assets/43515228/eb455494-27c6-4776-a824-a73ab072412e">
