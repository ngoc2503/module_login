package options;

public class LabelDAO {
	
	// convert level by int
	public static int parIntLabel(String label) {
		int sttLevel = 0;
		switch (label) {
		case "Top Secret":
			sttLevel = 4;
			break;
		case "Secret":
			sttLevel = 3;
			break;
		case "Confidential":
			sttLevel = 2;
			break;
		case "Unclassified":
			sttLevel = 1;
			break;
		}
		return sttLevel;
	}
	// convert level by String
	public static String parStringLabel(int level) {
		String lvString = "";
		switch (level) {
		case 1:
			lvString = "Unclassified";
			break;
		case 2:
			lvString = "Confidential";
			break;
		case 3:
			lvString = "Secret";
			break;
		case 4:
			lvString = "Top Secret";
			break;
		}
		return lvString;
	}
}
