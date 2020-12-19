package helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class VerbindungsIdGenerator {
	private static int _length = 9;
	
	private static boolean _useNumbers = true;
	private static boolean _useLowerCase = true;
	private static boolean _useCapitalLetter = true;
	
	private static int _sNumber = 48;
	private static int _bNumber = 57;
	
	private static int _sLowerCase = 97;
	private static int _bLowerCase = 122;
	
	private static int _sCapitalLetter = 65;
	private static int _bCapitalLetter = 90;
	
	public static String createUserId() {
		return createId();
	}
	
	public static String createModeratorId() {
		return createId();
	}
	
	private static String createId() {
		String result = "";
		List<CharGroups> charGroups = new ArrayList<CharGroups>();
		
		if(_useNumbers) {
			charGroups.add(CharGroups.Numbers);
		}
		
		if(_useLowerCase) {
			charGroups.add(CharGroups.LowerCase);
		}
		
		if(_useCapitalLetter) {
			charGroups.add(CharGroups.CapitalLetter);
		}
		
		for(int i = 0; i < _length; i++) {
			
			int groupId = ThreadLocalRandom.current().nextInt(0, charGroups.size());
			CharGroups group = charGroups.get(groupId);
			
			int charId = ThreadLocalRandom.current().nextInt(getMin(group), getMax(group) + 1);
			
			result += (char)charId;
		}
		
		return result;
	}
	
	private static int getMin(CharGroups charGroup) {
		switch(charGroup) {
			case Numbers:
				return _sNumber;
			case CapitalLetter:
				return _sCapitalLetter;
			case LowerCase:
				return _sLowerCase;
			default:
				return 0;
		}
	}
	
	private static int getMax(CharGroups charGroup) {
		switch(charGroup) {
			case Numbers:
				return _bNumber;
			case CapitalLetter:
				return _bCapitalLetter;
			case LowerCase:
				return _bLowerCase;
			default:
				return 0;
		}
	}
	
	private enum CharGroups
	{
		Numbers,
		LowerCase,
		CapitalLetter
	}
}
