# CS611-Assignment < 4 >
## < Monsters and Heroes >
---------------------------------------------------------------------------
- Name: Calvin Maddox
- Email: cmaddox1@bu.edu
- Student ID: U97155724

## Files
---------------------------------------------------------------------------
- Armor.java -- represents an armor item the hero can use
- BattleCoordinator -- holds the state of a battle and handles taking actions
- BattleFactory -- Factory object for creating new battles
- Colors -- Class holding information for printing color to console
- DoubleHanded -- Class used for weapons being held in two-hands
- EmptyArmor -- Represents the absence of a piece of armor
- EmptyWeapon -- Represents the absence of a weapon
- Entity -- General class for a 'creature', used as parent for Hero, Monster
- GamePlayer -- Handles main logic of the game
- Hero -- Represents a single hero in the game
- HeroFactory -- Factory object for creating new heroes
- Item -- General class for an item in the game
- ItemFactory -- Factory class for creating new items
- JSONReader -- Class for reading information from a json file
- Main -- main class
- MapTile -- Represents a single tile on the world map
- Merchant -- Represents one of the markets on the map
- MerchantFactory -- Factory class for creating merchants
- Potion -- Represents a potion object
- Spell -- Represents a spell object
- Weapon -- Represents a weapon object
- WorldMap -- Represents the game's map

## Notes
---------------------------------------------------------------------------
Design patterns used:
- Factory Pattern: used in several cases for procedurally generating various parts of the game
- Null Object Pattern: used in weapon and armor cases to avoid null references when testing equippedness

User Friendliness:
- Able to use q at any time to quit
- In most instances b will return to previous screen

Game Features:
- Monsters, Heroes generated automatically
- Monsters, Heroes, Items can all have modifiers that will affect a particular stat
- Clean, clear UI, well designed battle screen (imo).


## How to compile and run
---------------------------------------------------------------------------
In same directory as files run 'java -cp .\json-simple-1.1.jar Main.java'

## Input/Output Examples
---------------------------------------------------------------------------

Starting the Game:
```
    Output : Welcome to Monsters and Heroes!
How many heroes would you like in your party? (enter a number 1-3)

Input : 1

Output : 
  | █ |   |   |   |   |   | M |   |   |
----------------------------------------
  | M | █ |   |   |   | █ | █ | █ |   |
----------------------------------------
█ |   |   |   | M | █ | █ |   |   | M |
----------------------------------------
  |   |   | M |   |   |   |   |   | M |
----------------------------------------
  | █ |   | M | █ |   |   |   | █ | █ |
----------------------------------------
M |   |   | P | █ |   | M | M |   | M |
----------------------------------------
  |   | M |   |   |   |   |   |   |   |
----------------------------------------
  | M |   |   | M |   | M |   |   |   |
----------------------------------------
  |   |   |   |   | M |   |   |   |   |
----------------------------------------
  |   |   |   |   |   |   | M |   |   |
----------------------------------------

=====================================================

=====================================================
Please input a command:
w,a,s,d to move in a direction
i to open inventory
p to see party stats
m if on a market space to enter the market
```
Moving around
```
input : w
Output : 
  | █ |   |   |   |   |   | M |   |   |
----------------------------------------
  | M | █ |   |   |   | █ | █ | █ |   |
----------------------------------------
█ |   |   |   | M | █ | █ |   |   | M |
----------------------------------------
  |   |   | M |   |   |   |   |   | M |
----------------------------------------
  | █ |   | P | █ |   |   |   | █ | █ |
----------------------------------------
M |   |   | M | █ |   | M | M |   | M |
----------------------------------------
  |   | M |   |   |   |   |   |   |   |
----------------------------------------
  | M |   |   | M |   | M |   |   |   |
----------------------------------------
  |   |   |   |   | M |   |   |   |   |
----------------------------------------
  |   |   |   |   |   |   | M |   |   |
----------------------------------------

=====================================================

=====================================================
Please input a command:
w,a,s,d to move in a direction
i to open inventory
p to see party stats
m if on a market space to enter the market

input : d
Output :
  | █ |   |   |   |   |   | M |   |   |
----------------------------------------
  | M | █ |   |   |   | █ | █ | █ |   |
----------------------------------------
█ |   |   |   | M | █ | █ |   |   | M |
----------------------------------------
  |   |   | M |   |   |   |   |   | M |
----------------------------------------
  | █ |   | P | █ |   |   |   | █ | █ |
----------------------------------------
M |   |   | M | █ |   | M | M |   | M |
----------------------------------------
  |   | M |   |   |   |   |   |   |   |
----------------------------------------
  | M |   |   | M |   | M |   |   |   |
----------------------------------------
  |   |   |   |   | M |   |   |   |   |
----------------------------------------
  |   |   |   |   |   |   | M |   |   |
----------------------------------------

=====================================================
Inaccessible tile!
=====================================================
Please input a command:
w,a,s,d to move in a direction
i to open inventory
p to see party stats
m if on a market space to enter the market
```
Going to the Market
```
input : m
output : 
1: warrior Nahiri, LVL:2, XP:0/20, HP:206/206, MP:26/26, STR:44, DEX:24, AGI:28, Gold: 0
2: sorcerer Ral, LVL:1, XP:0/10, HP:102/102, MP:12/12, STR:20, DEX:15, AGI:16, Gold: 7

==========================================================================================================


==========================================================================================================
Which hero should enter?

input: 2
output : 
Welcome to the Merchant!
========================
Ral has 7 gold
========================
(p) to purchase, (s) to sell, (r) to repair, (b) to leave

input : p
output : 
1: LVL 1 Dexterity Potion, Increase dex by 5, Uses: 1/1, value: 2
2: LVL 3 Robust Circlet, adds 17 DEF, worn on head. Durability: 5/5, value: 8
3: LVL 3 Poleaxe, deals 27 DMG, Durability: 5/5, Two-handed. value: 13
4: LVL 3 Mighty Frost Spray, deal 44 ice damage, Costs 9 mp. Uses: 1/1, value: 22
5: LVL 4 Delicate Chaps, adds 10 DEF, worn on legs. Durability: 7/7, value: 5
6: LVL 4 Mana Potion, Increase mp by 13, Uses: 1/1, value: 6
7: LVL 5 Helm, adds 10 DEF, worn on head. Durability: 9/9, value: 5
8: LVL 5 Poleaxe, deals 50 DMG, Durability: 8/8, Two-handed. value: 25
9: LVL 6 Flimsy Shin Guards, adds 15 DEF, worn on legs. Durability: 6/6, value: 7
10: LVL 6 Dagger, deals 36 DMG, Durability: 9/9, One-Handed. value: 18
11: LVL 6 Meager Mana Potion, Increase mp by 24, Uses: 1/1, value: 15
12: LVL 6 Powerful Blizzard, deal 82 ice damage, Costs 30 mp. Uses: 4/4, value: 41
13: LVL 7 Bowstaff, deals 63 DMG, Durability: 13/13, Two-handed. value: 31
14: LVL 7 Lesser Agility Potion, Increase agi by 32, Uses: 1/1, value: 17
15: LVL 7 Lava Spike, deal 91 fire damage, Costs 21 mp. Uses: 4/4, value: 45
16: LVL 8 Thunder, deal 104 lightning damage, Costs 40 mp. Uses: 7/7, value: 52
17: LVL 9 Sturdy Steel Cuirass, adds 58 DEF, worn on chest. Durability: 10/10, value: 29
18: LVL 9 Powerful Longbow, deals 85 DMG, Durability: 11/11, Two-handed. value: 42
19: LVL 9 Fireball, deal 117 fire damage, Costs 27 mp. Uses: 4/4, value: 58
20: LVL 10 Chausses, adds 40 DEF, worn on legs. Durability: 17/17, value: 20
21: LVL 10 Powerful Flail, deals 53 DMG, Durability: 17/17, One-Handed. value: 26

====================================================================================


Ral has 7 gold
====================================================================================
What item to purchase? (enter number or b for back)

input : 1
output : 
1: LVL 3 Robust Circlet, adds 17 DEF, worn on head. Durability: 5/5, value: 8
2: LVL 3 Poleaxe, deals 27 DMG, Durability: 5/5, Two-handed. value: 13
3: LVL 3 Mighty Frost Spray, deal 44 ice damage, Costs 9 mp. Uses: 1/1, value: 22
4: LVL 4 Delicate Chaps, adds 10 DEF, worn on legs. Durability: 7/7, value: 5
5: LVL 4 Mana Potion, Increase mp by 13, Uses: 1/1, value: 6
6: LVL 5 Helm, adds 10 DEF, worn on head. Durability: 9/9, value: 5
7: LVL 5 Poleaxe, deals 50 DMG, Durability: 8/8, Two-handed. value: 25
8: LVL 6 Flimsy Shin Guards, adds 15 DEF, worn on legs. Durability: 6/6, value: 7
9: LVL 6 Dagger, deals 36 DMG, Durability: 9/9, One-Handed. value: 18
10: LVL 6 Meager Mana Potion, Increase mp by 24, Uses: 1/1, value: 15
11: LVL 6 Powerful Blizzard, deal 82 ice damage, Costs 30 mp. Uses: 4/4, value: 41
12: LVL 7 Bowstaff, deals 63 DMG, Durability: 13/13, Two-handed. value: 31
13: LVL 7 Lesser Agility Potion, Increase agi by 32, Uses: 1/1, value: 17
14: LVL 7 Lava Spike, deal 91 fire damage, Costs 21 mp. Uses: 4/4, value: 45
15: LVL 8 Thunder, deal 104 lightning damage, Costs 40 mp. Uses: 7/7, value: 52
16: LVL 9 Sturdy Steel Cuirass, adds 58 DEF, worn on chest. Durability: 10/10, value: 29
17: LVL 9 Powerful Longbow, deals 85 DMG, Durability: 11/11, Two-handed. value: 42
18: LVL 9 Fireball, deal 117 fire damage, Costs 27 mp. Uses: 4/4, value: 58
19: LVL 10 Chausses, adds 40 DEF, worn on legs. Durability: 17/17, value: 20
20: LVL 10 Powerful Flail, deals 53 DMG, Durability: 17/17, One-Handed. value: 26

=====================================================================================

Bought Dexterity Potion for 2 gold!
Ral has 5 gold
=====================================================================================
What item to purchase? (enter number or b for back)
```

Using the inventory
```
input : i
Output : 
1: warrior Nahiri, LVL:2, XP:0/20, HP:206/206, MP:26/26, STR:44, DEX:24, AGI:28, Gold: 0
2: sorcerer Ral, LVL:1, XP:0/10, HP:102/102, MP:12/12, STR:20, DEX:15, AGI:16, Gold: 5

==========================================================================================================


==========================================================================================================
Which hero's inventory to modify?

input : 2
output : 
sorcerer Ral, LVL:1, XP:0/10, HP:102/102, MP:12/12, STR:20, DEX:15, AGI:16, Gold: 5
1: LVL 1 Dexterity Potion, Increase dex by 5, Uses: 1/1, value: 2

========================================================================================


Head : none
Chest : none
Legs : none
Left Hand : none
Right Hand : none
========================================================================================
Choose an item to use/equip/unequip (b to leave)

input : 1
output : 
sorcerer Ral, LVL:1, XP:0/10, HP:102/102, MP:12/12, STR:20, DEX:20, AGI:16, Gold: 5

======================================================================================================

used Dexterity Potion
Head : none
Chest : none
Legs : none
Left Hand : none
Right Hand : none
======================================================================================================
Choose an item to use/equip/unequip (b to leave)
```
Battling:
```
output : 
(1)                   | (2)
Delicate Grist   | Slow Grist
LVL : 2               | LVL : 1
HP:210/210 ██████████ | HP:105/105 ██████████

Nahiri                 | Ral
LVL : 2               | LVL : 1
HP:206/206 ██████████ | HP:102/102 ██████████
MP:26/26 ██████████   | MP:12/12 ██████████
=======================================================================

You were attacked by 2 monsters!
Nahiri's turn!
=======================================================================
1) Attack
2) Use Item
3) See stats

input : 3
output : 
1) warrior Nahiri, LVL:2, XP:0/20, HP:206/206, MP:26/26, STR:44, DEX:24, AGI:28, Gold: 0
2) sorcerer Ral, LVL:1, XP:0/10, HP:102/102, MP:12/12, STR:20, DEX:20, AGI:16, Gold: 5

1) Level 2 Delicate Grist, HP:210/210, DMG:20, DEF:2, Dodge:20
2) Level 1 Slow Grist, HP:105/105, DMG:10, DEF:6, Dodge:1

======================================================================

======================================================================
Enter to return

input : 
output : 
(1)                   | (2)
Delicate Grist   | Slow Grist
LVL : 2               | LVL : 1
HP:210/210 ██████████ | HP:105/105 ██████████

Nahiri                 | Ral
LVL : 2               | LVL : 1
HP:206/206 ██████████ | HP:102/102 ██████████
MP:26/26 ██████████   | MP:12/12 ██████████
=======================================================================

You were attacked by 2 monsters!
Nahiri's turn!
=======================================================================
1) Attack
2) Use Item
3) See stats

input : 1
output : 
(1)                   | (2)
Delicate Grist   | Slow Grist
LVL : 2               | LVL : 1
HP:210/210 ██████████ | HP:105/105 ██████████

Nahiri                 | Ral
LVL : 2               | LVL : 1
HP:206/206 ██████████ | HP:102/102 ██████████
MP:26/26 ██████████   | MP:12/12 ██████████
=======================================================================


=======================================================================
Attack which monster? (enter number or b for back)

input : 1
output :
(1)                   | (2)
Delicate Grist   | Slow Grist
LVL : 2               | LVL : 1
HP:168/210 ██████████ | HP:105/105 ██████████

Nahiri                 | Ral
LVL : 2               | LVL : 1
HP:186/206 ██████████ | HP:102/102 ██████████
MP:26/26 ██████████   | MP:12/12 ██████████
=======================================================================

Nahiri attacked for 44 damage!
Delicate Grist took 42 damage!
Delicate Grist attacked for 20 damage!
Nahiri took 20 damage!

Ral's turn!
=======================================================================
1) Attack
2) Use Item
3) See stats
```
