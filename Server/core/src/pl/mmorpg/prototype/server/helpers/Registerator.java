package pl.mmorpg.prototype.server.helpers;

import pl.mmorpg.prototype.SpringContext;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationPacket;
import pl.mmorpg.prototype.clientservercommon.packets.RegisterationReplyPacket;
import pl.mmorpg.prototype.server.database.entities.User;
import pl.mmorpg.prototype.server.database.repositories.UserRepository;

public class Registerator
{
	private final UserRepository userRepo = SpringContext.getBean(UserRepository.class);
	
	public RegisterationReplyPacket tryToRegister(RegisterationPacket packet)
	{	
		RegisterationReplyPacket replyPacket = new RegisterationReplyPacket();
		if (packet.username.length() == 0)
			replyPacket.errorMessage = "Username field cannot be empty";
		else if (packet.password.length() == 0)
			replyPacket.errorMessage = "Password cannot be empty";
		else if (!packet.password.equals(packet.passwordRepeat))
			replyPacket.errorMessage = "Passwords are not the same!";
		else if (userRepo.findByUsername(packet.getUsername()) != null)
			replyPacket.errorMessage = "User '" + packet.getUsername() + "' already exists!";
		else
		{
			replyPacket.isRegistered = true;
			registerUser(packet);
		}
		return replyPacket;
	}

	private void registerUser(RegisterationPacket packet)
	{
		User user = new User();
		user.setUsername(packet.getUsername());
		user.setPassword(packet.getPassword());
		userRepo.save(user);
	}
}
