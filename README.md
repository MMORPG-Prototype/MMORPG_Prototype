#MMORPG Mediaval Game

MMORPG game
	MMORPG game where player can connect to server, register, log in, and create character. The information about this is saved in mysql database. 
	Then he can get into the game. User interface for client has most of the common dialogs, which can be seen in real-world mmorpg.
	There are also four types of enemy which can be defeated to gain experience, each one with different abilities, for eg. healing or casting fireball.
	As you may expected killing them also allows to collect valuable items like food, potions, gold etc. Potions can be drinked, food can be eaten.
	Server application have appropriate command prompt, where user may configure settings at runtime. Client application with suite authentication can send JavaScript code to server, where it will be executed, making it easy to manage game world. This application was tested with profiler towards memory leaks and it is 99% certain that it currently doesn't have any.
	This project currently contains about 16 000 lines of code (that is without comments or whitespace lines) distrubuted across 442 *.java files.

	Frameworks and libraries used:
	-Libgdx
	-Hibernate
	-Kryonet
	-Lombok
	-Maven
	-Google's Reflections 

Note: 
	I am using Kryonet Library, which license can be found here: https://github.com/EsotericSoftware/kryonet/blob/master/license.txt
	I do not have any of it's source code nor the binary form here.