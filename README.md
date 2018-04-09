# MMORPG Medieval Game &nbsp; 
## How to play
	The game server and CI server are no longer running, you can run server locally.
	
### Some description
	MMORPG game where player can connect to server, register, log in, and create character. The information about this is saved in mysql database.   
	Then he can get into the game. User interface for client has most of the common dialogs, which can be seen in real-world mmorpg.  
	There are also four types of enemy which can be defeated to gain experience, each one with different abilities, for eg. healing or casting fireball.   
	As you may expected killing them also allows to collect valuable items like food, potions, gold etc. Potions can be drinked, food can be eaten. Recently, I have implemented basic quest system.
	Server application have appropriate command prompt, where user may configure settings at runtime. 
	Client application with suite authentication can send JavaScript code to server, where it will be executed, making it easy to manage game world.
	This project currently contains more than 23 000 lines of code (that is without comments or whitespace lines) distrubuted across more than 600 java files.
	
## Docker
    The image for headless server can be found on docker hub: https://hub.docker.com/r/pankiev/mmorpgheadlessserver/
    You can use docker-compose file to run it via docker cli:
    docker-compose up -d (-d for detached mode)
## Frameworks and libraries used:
	- Libgdx
	- Hibernate
	- Spring boot and spring data jpa
	- Maven
	- Kryonet
	- Lombok
	- Reflections library

#### Note: 
	I am using Kryonet Library, which license can be found here: https://github.com/EsotericSoftware/kryonet/blob/master/license.txt.
	I do not have any of it's source code nor the binary form here.
