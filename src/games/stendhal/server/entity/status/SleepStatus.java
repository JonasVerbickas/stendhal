package games.stendhal.server.entity.status;

public class SleepStatus extends Status{

	public SleepStatus(String name) {
		super(name);
	}

	@Override
	public StatusType getStatusType() {
		return null;
	}
	
}